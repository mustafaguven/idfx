package tr.com.idefix.android.presenter;

import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.mapper.UserProfileDataMapper;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.android.view.MyOrdersView;
import tr.com.idefix.data.entity.CustomerOrdersBaseEntity;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICommonInterActor;
import tr.com.idefix.domain.interactor.ICustomerInterActor;
import tr.com.idefix.domain.interactor.ISignInInterActor;

/**
 * Created by mustafaguven on 20.10.2015.
 */
public class MyOrdersPresenter implements IMyOrdersPresenter {

  DomainContext domainContext;
  MyOrdersView view;

  UserProfileDataMapper userProfileDataMapper;
  ISignInInterActor signInInterActor;
  ICommonInterActor commonInterActor;
  ICustomerInterActor customerInterActor;

  public MyOrdersPresenter(
      UserProfileDataMapper userProfileDataMapper, ISignInInterActor signInInterActor,
      ICommonInterActor commonInterActor, ICustomerInterActor customerInterActor
  ) {
    this.userProfileDataMapper = userProfileDataMapper;
    this.signInInterActor = signInInterActor;
    this.commonInterActor = commonInterActor;
    this.customerInterActor = customerInterActor;
    domainContext =
        ApplicationController.getInstance().getDomainApplicationComponent().domainContext();
  }

  @Override public void getCustomerOrders() {
    customerInterActor.getCustomerOrders(new DefaultSubscriber<CustomerOrdersBaseEntity>() {
      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.hideProgress();
      }

      @Override public void onNext(CustomerOrdersBaseEntity customerOrdersBaseEntity) {
        super.onNext(customerOrdersBaseEntity);
        view.onFetchOrders(customerOrdersBaseEntity);
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
      }
    });
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  @Override public void setView(IView iView) {
    this.view = (MyOrdersView) iView;
  }
}
