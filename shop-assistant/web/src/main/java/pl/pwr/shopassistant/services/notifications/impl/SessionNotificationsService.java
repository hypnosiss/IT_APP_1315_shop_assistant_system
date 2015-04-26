package pl.pwr.shopassistant.services.notifications.impl;

import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.pwr.shopassistant.services.notifications.Notification;
import pl.pwr.shopassistant.services.notifications.NotificationType;
import pl.pwr.shopassistant.services.notifications.NotificationsService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class SessionNotificationsService implements NotificationsService {

	private final String _attributeName = "notifications";

	private HttpSession getSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(true);
	}

    private List<Notification> getSessionNotifications() {
        List<Notification> notifications;
        HttpSession session = this.getSession();

        Object attributeValue = session.getAttribute(_attributeName);
        if (attributeValue == null || !(attributeValue instanceof List)) {
            attributeValue = new ArrayList<Notification>();
        }

        notifications = (List<Notification>) attributeValue;
        return notifications;
    }

    private void setSessionNotifications(List<Notification> notifications) {
        HttpSession session = this.getSession();
        session.setAttribute(_attributeName, notifications);
    }

	private void addMessage(String content, NotificationType type) {
        List<Notification> notifications = this.getSessionNotifications();

        Notification notification = new Notification(content, type);
        notifications.add(notification);

        this.setSessionNotifications(notifications);
	}

    public List<Notification> getNotifications() {
        return this.getSessionNotifications();
    }

	public void clearNotifications() {
		this.setSessionNotifications(null);
	}

    public NotificationsService addSuccessMessage(String content) {
        this.addMessage(content, NotificationType.SUCCESS);

        return this;
    }

    public NotificationsService addInfoMessage(String content) {
        this.addMessage(content, NotificationType.INFO);

        return this;
    }

    public NotificationsService addWarningMessage(String content) {
        this.addMessage(content, NotificationType.WARNING);

        return this;
    }

    public NotificationsService addErrorMessage(String content) {
        this.addMessage(content, NotificationType.ERROR);

        return this;
    }
}
