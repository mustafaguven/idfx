package tr.com.idefix.android.azure;

/**
 * Created by mustafaguven on 07.01.2016.
 */
public class NotificationHubException extends Exception {
  private static final long serialVersionUID = -2417498840698257022L;
  private int mStatusCode;

  NotificationHubException(String error, int statusCode) {
    super(error);
    this.mStatusCode = statusCode;
  }

  public int getStatusCode() {
    return this.mStatusCode;
  }
}