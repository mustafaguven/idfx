package tr.com.idefix.domain.events;

import tr.com.idefix.domain.LoggedInUser;

/**
 * Created on 11.3.15.
 */
public class UserInOutEvent {

  private final LoggedInUser loggedInUser;

  public UserInOutEvent(LoggedInUser loggedInUser) {
    this.loggedInUser = loggedInUser;
  }

  public LoggedInUser getLoggedInUser() {
    return loggedInUser;
  }
}
