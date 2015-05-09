package pl.pwr.shopassistant.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/settings")
public class SettingsController {

    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
    public String home(Model model) {
        return "settings/list";
    }
}
