package tr.com.idefix.android.presenter;

import timber.log.Timber;
import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.mapper.UserProfileDataMapper;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.android.view.UserProfileActivityView;
import tr.com.idefix.domain.BasketCount;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICommonInterActor;
import tr.com.idefix.domain.interactor.ICustomerInterActor;
import tr.com.idefix.domain.interactor.ISignInInterActor;

/**
 * Created by mustafaguven on 19.10.2015.
 */
public class UserProfilePresenter implements Presenter {

  DomainContext domainContext;
  UserProfileActivityView view;

  UserProfileDataMapper userProfileDataMapper;
  ISignInInterActor signInInterActor;
  ICommonInterActor commonInterActor;
  ICustomerInterActor customerInterActor;

  public UserProfilePresenter(
      ISignInInterActor signInInterActor, ICommonInterActor commonInterActor,
      ICustomerInterActor customerInterActor
  ) {
    this.signInInterActor = signInInterActor;
    this.commonInterActor = commonInterActor;
    this.customerInterActor = customerInterActor;

    domainContext =
        ApplicationController.getInstance().getDomainApplicationComponent().domainContext();
  }

  @Override public void resume() {
  }

  public void getBasketCount() {
    customerInterActor.getBasketCount(new DefaultSubscriber<BasketCount>() {

      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onNext(BasketCount basketCount) {
        super.onNext(basketCount);

        if (basketCount != null) {
          view.setBasketCount(basketCount.cartItemCount());
        }
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
      }

      @Override public void onError(Throwable e) {
        Timber.e(e, "Sepet alınamadı");
        super.onError(e);
        view.hideProgress();
      }
    });
  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  @Override public void setView(IView iView) {
    this.view = (UserProfileActivityView) iView;
  }
}
