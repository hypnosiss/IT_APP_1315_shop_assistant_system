package pl.pwr.shopassistant.services.notifications;

public class Notification {

    private String content;
    private NotificationType type;

	public Notification(String content, NotificationType type) {
		this.content = content;
        this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }
}
