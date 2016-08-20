package tr.com.idefix.android.mapper;

import javax.inject.Inject;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.model.LoggedinUserModel;
import tr.com.idefix.domain.LoggedInUser;

/**
 * Created by utkan on 01/09/15.
 */
@PerActivity public class LoginDataMapper {

  @Inject public LoginDataMapper() {
  }

  public LoggedinUserModel transform(LoggedInUser o) {

    if (o != null) {
      LoggedinUserModel loggedinUserModel = new LoggedinUserModel();
      return loggedinUserModel.email(o.email())
          .password(o.password())
          .rememberMe(o.rememberMe())
          .username(o.username());
    }
    return null;
  }
}
