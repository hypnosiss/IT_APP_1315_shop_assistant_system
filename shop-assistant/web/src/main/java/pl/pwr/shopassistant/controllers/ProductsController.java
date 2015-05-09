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
        return "products/list";
    }

}
