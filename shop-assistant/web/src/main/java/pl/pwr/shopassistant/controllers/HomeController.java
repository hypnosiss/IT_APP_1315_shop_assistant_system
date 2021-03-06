package pl.pwr.shopassistant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

@Controller
public class HomeController {

    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
    public String home() {
        return "redirect:/products";
    }
}
