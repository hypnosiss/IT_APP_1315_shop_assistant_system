package pl.pwr.shopassistant.services.notifications;

import org.springframework.validation.FieldError;

import java.util.List;

public interface NotificationsService {
    public NotificationsService addSuccessMessage(String content);
    public NotificationsService addInfoMessage(String content);
    public NotificationsService addWarningMessage(String content);
    public NotificationsService addErrorMessage(String content);

    public List<Notification> getNotifications();
    public void clearNotifications();
}
