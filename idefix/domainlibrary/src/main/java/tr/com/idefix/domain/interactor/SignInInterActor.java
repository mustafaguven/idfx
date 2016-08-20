package tr.com.idefix.domain.interactor;

import javax.inject.Inject;
import rx.Subscriber;
import tr.com.idefix.data.net.RestApiBaseService;
import tr.com.idefix.data.repository.ILoginRepository;
import tr.com.idefix.data.repository.LoginDataRepository;
import tr.com.idefix.domain.BusProvider;
import tr.com.idefix.domain.DomainApplication;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.LogOut;
import tr.com.idefix.domain.LoggedInUser;
import tr.com.idefix.domain.events.UserInOutEvent;
import tr.com.idefix.domain.internal.di.components.ApplicationComponent;
import tr.com.idefix.domain.mapper.LoginEntityDataMapper;

/**
 * Created by utkan on 01/09/15.
 */
public class SignInInterActor implements ISignInInterActor {

  //<editor-fold desc="Fields">
  private final ILoginRepository loginRepository;
  private LoginEntityDataMapper loginEntityDataMapper;
  private DomainContext domainContext;
  private BusProvider busProvider;
  private Subscriber<LoggedInUser> loggedInSubscriber;
  private DefaultSubscriber<LogOut> logoutSubscriber;
  //</editor-fold>

  @Inject public SignInInterActor() {
    this.loginRepository = new LoginDataRepository();

    ApplicationComponent component =
        DomainApplication.getInstance().getDomainApplicationComponent();

    domainContext = component.domainContext();

    busProvider = component.busProvider();

    if (RestApiBaseService.getMemoryCache() == null) {
      RestApiBaseService.setMemoryCache(domainContext.getCache());
    }
  }

  public void login(
      String email, String password, boolean rememberMe, DefaultSubscriber<LoggedInUser> subscriber
  ) {

    this.loggedInSubscriber = subscriber;
    if (loginEntityDataMapper == null) {
      loginEntityDataMapper = new LoginEntityDataMapper();
    }

    domainContext.setLoggedInUser(null);
    domainContext.setCustomerInfo(null);
    domainContext.getCache().removeCache(RestApiBaseService.LOGIN_COOKIES);

    loginRepository.login(email, password, rememberMe)
        .map(loginEntityDataMapper::transform)
        .doOnNext(loggedInUser -> {
          domainContext.setLoggedInUser(loggedInUser);

          if (loggedInUser != null) {
            busProvider.getBus().post(new UserInOutEvent(loggedInUser));
          }
        })
        .doOnError(throwable -> {
          domainContext.getCache().removeCache(RestApiBaseService.LOGIN_COOKIES);
          domainContext.setLoggedInUser(null);
          domainContext.setCustomerInfo(null);
        })
        .subscribe(subscriber);
  }

  @Override public void logout(String sessionID, DefaultSubscriber<LogOut> subscriber) {

    this.logoutSubscriber = subscriber;
    if (loginEntityDataMapper == null) {
      loginEntityDataMapper = new LoginEntityDataMapper();
    }

    loginRepository.logout(sessionID).map(loginEntityDataMapper::transform).doOnNext(logOut -> {
      domainContext.getCache().removeCache(RestApiBaseService.LOGIN_COOKIES);
      domainContext.setLoggedInUser(null);
      domainContext.setCustomerInfo(null);

      busProvider.getBus().post(new UserInOutEvent(null));
    }).subscribe(subscriber);
  }

  @Override public void unSubscribe() {
    if (loggedInSubscriber != null && !loggedInSubscriber.isUnsubscribed()) {
      loggedInSubscriber.unsubscribe();
    }
    if (logoutSubscriber != null && !logoutSubscriber.isUnsubscribed()) {
      logoutSubscriber.unsubscribe();
    }
  }
}

