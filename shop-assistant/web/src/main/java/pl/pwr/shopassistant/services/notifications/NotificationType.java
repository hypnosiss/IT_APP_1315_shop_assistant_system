package pl.pwr.shopassistant.services.notifications;

public enum NotificationType {
    SUCCESS ("success"),
    INFO    ("info"),
    WARNING ("warning"),
    ERROR   ("error");

	private String type;

	private NotificationType(String type) {
		this.type = type;
	}

	private String getType() {
		return type;
	}
}