package tr.com.idefix.android.azure;

/**
 * Created by mustafaguven on 07.01.2016.
 */
public class RegistrationGoneException extends Exception {
  private static final long serialVersionUID = -156200383034074631L;

  RegistrationGoneException() {
    super("Registration is gone");
  }
}
