package pl.pwr.shopassistant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.pwr.shopassistant.dao.UserDao;
import pl.pwr.shopassistant.entities.User;
import pl.pwr.shopassistant.forms.UserRegisterForm;
import pl.pwr.shopassistant.services.hash.HashService;
import pl.pwr.shopassistant.services.notifications.NotificationsService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@Transactional
public class LoginController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private NotificationsService notificationsService;

	@Autowired
	@Qualifier("sha1HashService")
	private HashService hashService;


	@RequestMapping(value="/login", method = RequestMethod.GET)
	public ModelAndView login(@ModelAttribute UserRegisterForm userRegisterForm) {

		Map<String, Object> modelAttributes = new HashMap<String, Object>();
		modelAttributes.put("userRegisterForm", userRegisterForm);

		return new ModelAndView("login", modelAttributes);
	}

	@RequestMapping(value="/register", method = RequestMethod.POST)
	public ModelAndView register(@ModelAttribute UserRegisterForm userRegisterForm, BindingResult bindingResult) {

		if (bindingResult.hasErrors() || !userRegisterForm.isValid(bindingResult)) {
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				notificationsService.addErrorMessage(String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()));
			}
			return login(userRegisterForm);
		}

		String username = userRegisterForm.getUsername();
		String password = userRegisterForm.getPassword();
		String repeatPassword = userRegisterForm.getRepeatPassword();
		String name = userRegisterForm.getName();
		String address = userRegisterForm.getAddress();
		String phone = userRegisterForm.getPhone();

		User user = userDao.findByUsername(username);
		if (user != null) {
			notificationsService.addErrorMessage("Username already exists");
			return login(userRegisterForm);
		}

		if (!password.equals(repeatPassword)) {
			notificationsService.addErrorMessage("Passwords do not match");
			return login(userRegisterForm);
		}

		String passwordHash = hashService.hash(password);

		user = new User();
		user.setUsername(username);
		user.setPassword(passwordHash);
		user.setName(name);
		user.setAddress(address);
		user.setPhone(phone);
		userDao.save(user);

		if (!user.isNew()) {
			notificationsService.addSuccessMessage("Your account has been created successfully and is ready to use");
		}

		return new ModelAndView("redirect:/");
	}
}
