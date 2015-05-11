package pl.pwr.shopassistant.controllers;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.pwr.shopassistant.dao.*;
import pl.pwr.shopassistant.entities.*;
import pl.pwr.shopassistant.shopapiclient.ShopApiClient;
import pl.pwr.shopassistant.shopapiclient.ShopProduct;
import pl.pwr.shopassistant.shopapiclient.TimePeriod;
import pl.pwr.shopassistant.model.*;
import pl.pwr.shopassistant.operationresult.OperationResult;
import pl.pwr.shopassistant.services.auth.AuthService;
import pl.pwr.shopassistant.shopapiclient.mock.MockApiClient;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
@Transactional
@RequestMapping(value = "/orders")
public class OrdersController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserProductDao userProductDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderProductDao orderProductDao;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
    public String list(Model model) {
        User currentUser = authService.getCurrentUser();
        List<Order> orders = orderDao.findByUser(currentUser);

        model.addAttribute("orders", orders);
        return "orders/list";
    }

    @Transactional
    @RequestMapping(value = { "/new1" }, method = RequestMethod.POST, consumes="application/json", produces = "application/json")
         public @ResponseBody
    OrderSummaryItem[] new1(@RequestBody String[] eans, HttpServletRequest request) {

        List<UserProduct> userProducts  = new LinkedList<UserProduct>();
        for(String ean : eans) {
            Product product = productDao.findProductByEan(ean);
            User user = authService.getCurrentUser();
            UserProduct userProduct = userProductDao.findByUserAndProduct(user, product);
            if(null != userProduct) {
                userProducts.add(userProduct);
            }
        }

        Collections.sort(userProducts, new Comparator<UserProduct>() {
            public int compare(UserProduct userProduct1, UserProduct userProduct2) {
                return userProduct2.getStatus().getValue().compareTo(userProduct1.getStatus().getValue());
            }
        });

        OrderSummaryItem[] items = new OrderSummaryItem[userProducts.size()];
        for(int i = 0; i < userProducts.size(); i++) {
            items[i] = new OrderSummaryItem(userProducts.get(i));
        }
        return items;
    }

    @Transactional
    @RequestMapping(value = { "/new2" }, method = RequestMethod.POST, consumes="application/json", produces = "application/json")
    public @ResponseBody
    OrderSummaryShop[] new2(@RequestBody OrderSummaryItem[] items, HttpServletRequest request) {
        for(OrderSummaryItem item : items) {
            Product product = productDao.findProductByEan(item.getEan());
            User user = authService.getCurrentUser();
            UserProduct userProduct = userProductDao.findByUserAndProduct(user, product);
            if (null != userProduct) {
                userProduct.setQuantity(item.getQuantity());
                userProductDao.update(userProduct);
            }
        }
        //TODO: check if quantity > 0
        MockApiClient mockApiClient = new MockApiClient();

        Set<String> eans = new HashSet<String>();
        for(OrderSummaryItem item : items) {
            eans.add(item.getEan());
        }

        List<Shop> shops  = shopDao.getList();
        OrderSummaryShop[] shopsArray = new OrderSummaryShop[shops.size()];
        for(int i = 0; i < shops.size(); i++) {
            OperationResult operationResult = mockApiClient.findProductsByEANs(eans);
            if (operationResult.getResultCode() == 0) {
                //@TODO: multiply by quantity;
                shopsArray[i] = new OrderSummaryShop(shops.get(i),
                        (List<ShopProduct>) operationResult.getValue(ShopApiClient.FIND_PRODUCTS_BY_EANS__PRODUCTS));
            }
        }
        return shopsArray;
    }

    @RequestMapping(value = { "/new3" }, method = RequestMethod.POST, consumes="application/json", produces = "application/json")
    public @ResponseBody
    TimeSlot[] new3(@RequestBody String shopName, HttpServletRequest request) throws Exception {
        MockApiClient mockApiClient = new MockApiClient();
        OperationResult operationResult = mockApiClient.getDeliveryTimetable();
        if (operationResult.getResultCode() != 0) {
            return new TimeSlot[0];
        }
        Map<LocalDate, List<TimePeriod>> timetable =
                (Map<LocalDate, List<TimePeriod>>) operationResult.getValue(ShopApiClient.GET_DELIVERY_TIMETABLE__TIMETABLE);

        List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
        for(Map.Entry<LocalDate, List<TimePeriod>> entry : timetable.entrySet()) {
            for(TimePeriod timePeriod : entry.getValue()) {
                TimeSlot slot = new TimeSlot(entry.getKey(), timePeriod);
                timeSlots.add(slot);
            }
        }
        Collections.sort(timeSlots, new TimeSlotComparator());
        TimeSlot[] timeSlotsArray = timeSlots.toArray(new TimeSlot[timeSlots.size()]);
        return timeSlotsArray;
    }

    @Transactional
    @RequestMapping(value = { "new4" }, method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    JsonString new4(HttpServletRequest request) throws IOException {
        User user = authService.getCurrentUser();
        return new JsonString(user.getAddress());
    }

    @Transactional
    @RequestMapping(value = { "new/finalize" }, method = RequestMethod.POST, consumes="application/json", produces = "application/json")
    public @ResponseBody
    JsonString finalize(@RequestBody OrderSummaryFinal orderSummary, HttpServletRequest request) {
        Order order = new Order();
        Date now = new Date();
        User user = authService.getCurrentUser();
        order.setUser(user);
        order.setDeliveryAddress(user.getAddress());

        Shop shop = shopDao.findByName(orderSummary.getShopName());
        order.setShop(shop);

        order.setDeliveryDate(TimeSlot.dateFormatter.parseLocalDate(orderSummary.getTimeSlot().getDate()).toDate());
        order.setDeliveryHourFrom(TimeSlot.timeFormatter.parseLocalTime(orderSummary.getTimeSlot().getFrom()).toDateTimeToday().toDate());
        order.setDeliveryHourTo(TimeSlot.timeFormatter.parseLocalTime(orderSummary.getTimeSlot().getTo()).toDateTimeToday().toDate());
        order.setOrderTimestamp(now);

        Set<String> eans = new HashSet<String>();
        for(String ean : orderSummary.getEans()) {
            eans.add(ean);
        }

        MockApiClient mockApiClient = new MockApiClient();
        OperationResult operationResult = mockApiClient.findProductsByEANs(eans);
        if (operationResult.getResultCode() != 0) {
            return new JsonString("Could not connect to shop API");
        }

        BigDecimal totalPrice = new BigDecimal(0.0);
        Set<OrderProduct> orderProducts = new HashSet<OrderProduct>();
        List<UserProduct> userProducts = new ArrayList<UserProduct>();
        List<ShopProduct> shopProducts = (List<ShopProduct>) operationResult.getValue(ShopApiClient.FIND_PRODUCTS_BY_EANS__PRODUCTS);
        for(ShopProduct shopProduct : shopProducts) {
            OrderProduct orderProduct = new OrderProduct();

            orderProduct.setOrder(order);

            orderProduct.setPrice(shopProduct.getPrice());

            Product product = productDao.findProductByEan(shopProduct.getEAN());
            orderProduct.setProduct(product);

            UserProduct userProduct = userProductDao.findByUserAndProduct(user, product);
            orderProduct.setQuantity(userProduct.getQuantity());
            userProducts.add(userProduct);

            totalPrice = totalPrice.add(orderProduct.getPrice());
        }
        order.setTotalPrice(totalPrice);
        order.setOrdersProducts(orderProducts);

        //@TODO: placing order in the shop
        mockApiClient.placeOrder();
        if (operationResult.getResultCode() != 0) {
            return new JsonString("Could not connect to shop API");
        }

        for(UserProduct userProduct : userProducts) {
            userProduct.setLastOrderTimestamp(now);
            userProductDao.update(userProduct);
        }

        orderDao.save(order);
        for(OrderProduct orderProduct : orderProducts) {
            orderProductDao.save(orderProduct);
        }

        return new JsonString("ok");
    }

    private class TimeSlotComparator implements Comparator<TimeSlot> {

        public int compare(TimeSlot timeSlot1, TimeSlot timeSlot2) {
            LocalDate date1 = TimeSlot.timeFormatter.parseLocalDate(timeSlot1.getDate());
            LocalDate date2 = TimeSlot.timeFormatter.parseLocalDate(timeSlot1.getDate());

            if(date1.isBefore(date2)) {
                return -1;
            } else if (date2.isBefore(date1)) {
                return 1;
            } else {
                LocalTime time1 = TimeSlot.timeFormatter.parseLocalTime(timeSlot1.getFrom());
                LocalTime time2 = TimeSlot.timeFormatter.parseLocalTime(timeSlot2.getFrom());
                if(time1.isBefore(time2)) {
                    return -1;
                } else if (time2.isBefore(time2)) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }
}