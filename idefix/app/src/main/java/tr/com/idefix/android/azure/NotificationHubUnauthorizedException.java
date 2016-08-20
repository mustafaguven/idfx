package tr.com.idefix.android.azure;

/**
 * Created by mustafaguven on 07.01.2016.
 */
public class NotificationHubUnauthorizedException extends NotificationHubException {
  private static final long serialVersionUID = -5926583893712403416L;

  NotificationHubUnauthorizedException() {
    super("Unauthorized", 401);
  }
}
