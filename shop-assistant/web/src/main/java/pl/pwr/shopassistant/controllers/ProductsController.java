package pl.pwr.shopassistant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.pwr.shopassistant.services.auth.AuthService;
import pl.pwr.shopassistant.dao.UserProductDao;
import pl.pwr.shopassistant.entities.User;
import pl.pwr.shopassistant.entities.UserProduct;
import pl.pwr.shopassistant.entities.comparators.UserProductsByStatusComparator;
import pl.pwr.shopassistant.model.OrderSummaryItem;
import pl.pwr.shopassistant.services.notifications.NotificationsService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@Transactional
@RequestMapping(value = "/products")
public class ProductsController {

    @Autowired
    private UserProductDao userProductDao;

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private AuthService authService;


    @RequestMapping(value = { "", "/", "/list" }, method = RequestMethod.GET)
    public String list(Model model) {

        User currentUser = authService.getCurrentUser();
        List<UserProduct> userProducts = userProductDao.getUserProductsByUser(currentUser);

        Collections.sort(userProducts, new UserProductsByStatusComparator());

        model.addAttribute("userProducts", userProducts);
        return "products/list";
    }

}
