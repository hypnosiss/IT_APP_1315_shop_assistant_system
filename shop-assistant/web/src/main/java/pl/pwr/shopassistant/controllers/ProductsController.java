package pl.pwr.shopassistant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pwr.shopassistant.dao.ProductDao;
import pl.pwr.shopassistant.dao.UserProductDao;
import pl.pwr.shopassistant.entities.Product;
import pl.pwr.shopassistant.entities.UserProduct;
import pl.pwr.shopassistant.services.notifications.NotificationsService;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

        notificationsService.addInfoMessage("Test info message");

        Collections.sort(userProducts, new Comparator<UserProduct>() {
            public int compare(UserProduct userProduct1, UserProduct userProduct2) {
                return userProduct2.getStatus().getValue().compareTo(userProduct1.getStatus().getValue());
            }
        });

        model.addAttribute("userProducts", userProducts);
        return "products/list";
    }
}
