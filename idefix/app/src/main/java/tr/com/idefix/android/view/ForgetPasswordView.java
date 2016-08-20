package tr.com.idefix.android.view;

import tr.com.idefix.data.entity.PasswordRecoveryEntity;

/**
 * Created by mustafaguven on 17.10.2015.
 */
public interface ForgetPasswordView extends IView {

  void onMailSent(PasswordRecoveryEntity passwordRecoveryEntity);
}
