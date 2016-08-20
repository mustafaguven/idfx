package tr.com.idefix.android.presenter;

import javax.inject.Inject;
import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.mapper.LoginDataMapper;
import tr.com.idefix.android.model.LoggedinUserModel;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.android.view.LoginView;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.LoggedInUser;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ISignInInterActor;

/**
 * Created by utkan on 01/09/15.
 */
@PerActivity public class LoginPresenter implements ILoginPresenter {

  private final LoginDataMapper loginDataMapper;
  private final ISignInInterActor signInInterActor;
  LoginView loginView;

  DomainContext domainContext;

  @Inject public LoginPresenter(
      ISignInInterActor signInInterActor, LoginDataMapper loginDataMapper
  ) {
    this.signInInterActor = signInInterActor;
    this.loginDataMapper = loginDataMapper;

    domainContext =
        ApplicationController.getInstance().getDomainApplicationComponent().domainContext();
  }

  @Override public void resume() {

  }

  @Override public void pause() {
    signInInterActor.unSubscribe();
  }

  @Override public void destroy() {
    signInInterActor.unSubscribe();
  }

  void logIn(String email, String password, boolean rememberMe) {

    signInInterActor.login(email, password, rememberMe, new DefaultSubscriber<LoggedInUser>() {
      @Override public void onError(Throwable e) {
        super.onError(e);
        loginView.onError(e != null && e.getMessage() != null ? e.getMessage() : "hata");
        loginView.hideProgress();
      }

      @Override public void onCompleted() {
        super.onCompleted();
        loginView.loggedin();
        loginView.hideProgress();
      }

      @Override public void onNext(LoggedInUser o) {
        super.onNext(o);

        LoggedinUserModel loggedinUserModel = loginDataMapper.transform(o);
      }

      @Override public void onStart() {
        super.onStart();
        loginView.showProgress();
      }
    });
  }

  @Override public void login(String email, String password, boolean rememberMe) {

    logIn(email, password, rememberMe);
  }

  @Override public void logout() {
  }

  @Override public void setView(IView iView) {
    this.loginView = (LoginView) iView;
  }
}
