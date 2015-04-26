package pl.pwr.shopassistant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pwr.shopassistant.dao.ProductDao;
import pl.pwr.shopassistant.entities.Product;

import java.util.List;

@Controller
@RequestMapping(value = "/orders")
public class OrdersController {

    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
    public String list(Model model) {
        return "orders/list";
    }
}
