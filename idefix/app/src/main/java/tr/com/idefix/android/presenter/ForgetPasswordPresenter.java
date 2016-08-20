package tr.com.idefix.android.presenter;

import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.view.ForgetPasswordView;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.data.entity.PasswordRecoveryEntity;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICommonInterActor;

/**
 * Created by mustafaguven on 17.10.2015.
 */
public class ForgetPasswordPresenter implements IForgetPasswordPresenter {

  DomainContext domainContext;
  ForgetPasswordView view;

  ICommonInterActor commonInterActor;

  public ForgetPasswordPresenter(ICommonInterActor commonInterActor) {
    this.commonInterActor = commonInterActor;

    domainContext =
        ApplicationController.getInstance().getDomainApplicationComponent().domainContext();
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  @Override public void setView(IView iView) {
    this.view = (ForgetPasswordView) iView;
  }

  @Override public void passwordRecovery(String email) {
    PasswordRecoveryEntity passwordRecoveryEntity = new PasswordRecoveryEntity();
    passwordRecoveryEntity.setEmail(email);
    commonInterActor.passwordRecovery(new DefaultSubscriber<PasswordRecoveryEntity>() {
      @Override public void onStart() {
        super.onStart();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
      }

      @Override public void onNext(PasswordRecoveryEntity passwordRecoveryEntity) {
        super.onNext(passwordRecoveryEntity);
        view.onMailSent(passwordRecoveryEntity);
      }

      @Override public void onCompleted() {
        super.onCompleted();
      }
    }, passwordRecoveryEntity);
  }
}
