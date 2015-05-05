package pl.pwr.shopassistant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.pwr.shopassistant.dao.UserProductDao;
import pl.pwr.shopassistant.entities.UserProduct;
import pl.pwr.shopassistant.model.OrderSummaryItem;
import pl.pwr.shopassistant.services.notifications.NotificationsService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "/products")
public class ProductsController {

    @Autowired
    private UserProductDao userProductDao;

    @Autowired
    private NotificationsService notificationsService;

    @RequestMapping(value = { "", "/", "/list" }, method = RequestMethod.GET)
    public String list(Model model) {
        List<UserProduct> userProducts = userProductDao.getList();

        //notificationsService.addInfoMessage("Test info message");

        Collections.sort(userProducts, new Comparator<UserProduct>() {
            public int compare(UserProduct userProduct1, UserProduct userProduct2) {
                return userProduct2.getStatus().getValue().compareTo(userProduct1.getStatus().getValue());
            }
        });
        model.addAttribute("userProducts", userProducts);
        model.addAttribute("orderStage", 0);
        return "products/list";
    }

    @RequestMapping(value = { "/newOrder" }, method = RequestMethod.POST, consumes="application/json", produces = "application/json")
    public @ResponseBody
    OrderSummaryItem[] newOrder(@RequestBody String[] eans, HttpServletRequest request) {
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

}
