package tr.com.idefix.android.azure;

/**
 * Created by mustafaguven on 07.01.2016.
 */

public class NotificationHubResourceNotFoundException extends NotificationHubException {
  private static final long serialVersionUID = -1205615098165583127L;

  NotificationHubResourceNotFoundException() {
    super("Resource not found", 404);
  }
}
