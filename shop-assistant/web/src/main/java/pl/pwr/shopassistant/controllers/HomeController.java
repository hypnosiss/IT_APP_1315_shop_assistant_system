package pl.pwr.shopassistant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pwr.shopassistant.dao.ProductDao;
import pl.pwr.shopassistant.dao.impl.HibernateProductDao;
import pl.pwr.shopassistant.entities.ProductEntity;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductDao productDao;

    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
    public String home(Model model) {
        List<ProductEntity> products = productDao.getList();

        model.addAttribute("products", products);

        return "home/index";
    }
}
