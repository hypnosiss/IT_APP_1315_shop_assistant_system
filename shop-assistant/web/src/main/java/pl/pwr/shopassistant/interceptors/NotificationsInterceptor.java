package pl.pwr.shopassistant.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;
import pl.pwr.shopassistant.services.notifications.Notification;
import pl.pwr.shopassistant.services.notifications.NotificationsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class NotificationsInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	NotificationsService notificationService;

	private final String attributeName = "notifications";
	
	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response,
                           final Object handler, final ModelAndView modelAndView) throws Exception {

		if (modelAndView != null && !(modelAndView.getView() instanceof RedirectView)
                && !modelAndView.getViewName().startsWith("redirect:") && modelAndView.getModelMap() != null) {

            List<Notification> notifications = notificationService.getNotifications();
            notificationService.clearNotifications();

            modelAndView.getModelMap().addAttribute(this.attributeName, notifications);
		}
		
	}
}
