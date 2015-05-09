package pl.pwr.shopassistant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.pwr.shopassistant.dao.ProductDao;
import pl.pwr.shopassistant.dao.ShopDao;
import pl.pwr.shopassistant.dao.UserProductDao;
import pl.pwr.shopassistant.entities.Product;
import pl.pwr.shopassistant.entities.Shop;
import pl.pwr.shopassistant.entities.UserProduct;
import pl.pwr.shopassistant.fridgeapiclient.tesco.TescoApiClient;
import pl.pwr.shopassistant.model.OrderSummaryFinal;
import pl.pwr.shopassistant.model.OrderSummaryItem;
import pl.pwr.shopassistant.model.OrderSummaryShop;
import pl.pwr.shopassistant.model.TimeSlot;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "/orders")
public class OrdersController {

    @Autowired
    private UserProductDao userProductDao;
    @Autowired
    private ShopDao shopDao;

    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
    public String list(Model model) {
        return "orders/list";
    }


    @RequestMapping(value = { "/new1" }, method = RequestMethod.POST, consumes="application/json", produces = "application/json")
         public @ResponseBody
    OrderSummaryItem[] new1(@RequestBody String[] eans, HttpServletRequest request) {
        List<UserProduct> userProducts  = new LinkedList<UserProduct>();
        for(String ean : eans) {
            UserProduct userProduct = userProductDao.getUserProductForEAN(ean);
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

    @RequestMapping(value = { "/new2" }, method = RequestMethod.POST, consumes="application/json", produces = "application/json")
    public @ResponseBody
    OrderSummaryShop[] new2(@RequestBody OrderSummaryItem[] items, HttpServletRequest request) {
        for(OrderSummaryItem item : items) {
            UserProduct userProduct = userProductDao.getUserProductForEAN(item.getEan());
            if(null != userProduct) {
                userProduct.setQuantity(item.getQuantity());
                userProductDao.update(userProduct);
            }
        }

        List<Shop> shops  = shopDao.getList();
        OrderSummaryShop[] shopsArray = new OrderSummaryShop[shops.size()];
        for(int i = 0; i < shops.size(); i++) {
            shopsArray[i] = new OrderSummaryShop(shops.get(i));
        }
        return shopsArray;
    }

    @RequestMapping(value = { "/new3" }, method = RequestMethod.POST, consumes="application/json", produces = "application/json")
    public @ResponseBody
    TimeSlot[] new3(@RequestBody String shopName, HttpServletRequest request) {
        //Shop shop = shopDao.getByName(shopName);
        //TescoApiClient client = new TescoApiClient();
        //List<TimeSlot> timeSlots = client.getDeliveryTimetable();
        
        List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
        timeSlots.add(new TimeSlot("aaa", "bbb", "ccc"));
        TimeSlot[] timeSlotsArray = timeSlots.toArray(new TimeSlot[timeSlots.size()]);
        return timeSlotsArray;
    }

    @RequestMapping(value = { "/new5" }, method = RequestMethod.POST, consumes="application/json", produces = "application/json")
    public @ResponseBody
    String new5(@RequestBody OrderSummaryFinal order, HttpServletRequest request) {
        return "ok";
    }
}
