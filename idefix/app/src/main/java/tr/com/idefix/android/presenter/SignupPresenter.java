package tr.com.idefix.android.presenter;

import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.android.view.SignupView;
import tr.com.idefix.data.entity.SaveNewCustomerRequestEntity;
import tr.com.idefix.data.entity.SaveNewCustomerResponseEntity;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICommonInterActor;

/**
 * Created by mustafaguven on 17.10.2015.
 */
public class SignupPresenter implements ISignupPresenter {

  DomainContext domainContext;
  SignupView view;

  ICommonInterActor commonInterActor;

  public SignupPresenter(ICommonInterActor commonInterActor) {
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
    this.view = (SignupView) iView;
  }

  @Override public void saveNewCustomer(SaveNewCustomerRequestEntity saveNewCustomerRequestEntity) {
    commonInterActor.saveNewCustomer(new DefaultSubscriber<SaveNewCustomerResponseEntity>() {
      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.hideProgress();
      }

      @Override public void onNext(SaveNewCustomerResponseEntity saveNewCustomerResponseEntity) {
        super.onNext(saveNewCustomerResponseEntity);
        view.onNewCustomerSaved(saveNewCustomerResponseEntity);
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
      }
    }, saveNewCustomerRequestEntity);
  }
}
