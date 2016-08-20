package tr.com.idefix.domain.interactor;

import tr.com.idefix.domain.LogOut;
import tr.com.idefix.domain.LoggedInUser;

/**
 * Created by utkan on 01/09/15.
 */
public interface ISignInInterActor extends IInterActor {

  void login(
      String email, String password, boolean rememberMe,
      DefaultSubscriber<LoggedInUser> defaultSubscriber
  );

  void logout(String sessionID, DefaultSubscriber<LogOut> defaultSubscriber);
}
