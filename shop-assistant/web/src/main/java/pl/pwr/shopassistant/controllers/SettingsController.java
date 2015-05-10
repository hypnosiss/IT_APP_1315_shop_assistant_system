package pl.pwr.shopassistant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.pwr.shopassistant.dao.UserDao;
import pl.pwr.shopassistant.entities.User;
import pl.pwr.shopassistant.forms.ChangeSettingsForm;
import pl.pwr.shopassistant.services.auth.AuthService;
import pl.pwr.shopassistant.services.notifications.NotificationsService;

import java.util.HashMap;
import java.util.Map;

@Controller
@Transactional
@RequestMapping(value = "/settings")
public class SettingsController {

    @Autowired
    private AuthService authService;

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private UserDao userDao;


    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
    public ModelAndView changeSettings() {

        User currentUser = authService.getCurrentUser();

        ChangeSettingsForm changeSettingsForm = new ChangeSettingsForm();
        changeSettingsForm.setName(currentUser.getName());
        changeSettingsForm.setAddress(currentUser.getAddress());
        changeSettingsForm.setPhone(currentUser.getPhone());

        Map<String, Object> modelAttributes = new HashMap<String, Object>();
        modelAttributes.put("changeSettingsForm", changeSettingsForm);

        return new ModelAndView("settings/change", modelAttributes);
    }

    @RequestMapping(value = { "/change" }, method = RequestMethod.POST)
    public ModelAndView changeSettingsPost(@ModelAttribute ChangeSettingsForm changeSettingsForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors() || !changeSettingsForm.isValid(bindingResult)) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                notificationsService.addErrorMessage(String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()));
            }
            return changeSettings();
        }

        String name = changeSettingsForm.getName();
        String address = changeSettingsForm.getAddress();
        String phone = changeSettingsForm.getPhone();

        User currentUser = authService.getCurrentUser();
        currentUser.setName(name);
        currentUser.setAddress(address);
        currentUser.setPhone(phone);
        userDao.save(currentUser);

        notificationsService.addSuccessMessage("Settings have been changed");

        return new ModelAndView("redirect:/settings");
    }
}
