package tr.com.idefix.domain.mapper;

import tr.com.idefix.data.entity.LogOutEntity;
import tr.com.idefix.data.entity.LoginEntity;
import tr.com.idefix.domain.LogOut;
import tr.com.idefix.domain.LoggedInUser;

/**
 * Mapper class used to transform {@link LoginEntity} (in the data layer)
 * to {@link LoggedInUser} in the
 * domain layer.
 */
public class LoginEntityDataMapper {
  /**
   * Transform a {@link LoginEntity} into an {@link LoggedInUser}.
   *
   * @param loginEntity Object to be transformed.
   * @return {@link LoggedInUser} if valid {@link LoginEntity} otherwise null.
   */
  public LoggedInUser transform(LoginEntity loginEntity) {
    LoggedInUser logedinUser = null;
    if (loginEntity != null && loginEntity.loginSubEntity() != null) {
      logedinUser = new LoggedInUser();

      logedinUser.sessionObject(loginEntity.sessionObject())
          .email(loginEntity.loginSubEntity().email())
          .password(loginEntity.loginSubEntity().password())
          .rememberMe(loginEntity.loginSubEntity().rememberMe())
          .username(loginEntity.loginSubEntity().username());
    }

    return logedinUser;
  }

  public LogOut transform(LogOutEntity logOutEntity) {
    LogOut logOut = null;
    if (logOutEntity != null) {
      logOut = new LogOut();

      logOut.success(logOutEntity.success()).errorMessage(logOutEntity.errorMessage());
    }

    return logOut;
  }
}
