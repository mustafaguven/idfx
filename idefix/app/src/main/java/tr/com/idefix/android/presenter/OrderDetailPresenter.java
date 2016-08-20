package tr.com.idefix.android.presenter;

import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.view.IOrderDetailView;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.data.entity.OrderDetailResponseEntity;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICustomerInterActor;

public class OrderDetailPresenter implements IOrderDetailPresenter {

  DomainContext domainContext;
  IOrderDetailView view;
  ICustomerInterActor customerInterActor;

  public OrderDetailPresenter(
      ICustomerInterActor iCustomerInterActor
  ) {
    this.customerInterActor = iCustomerInterActor;
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
    this.view = (IOrderDetailView) iView;
  }

  @Override public void getOrderDetail(String orderId) {
    customerInterActor.getOrderDetails(new DefaultSubscriber<OrderDetailResponseEntity>() {
      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.hideProgress();
      }

      @Override public void onNext(OrderDetailResponseEntity response) {
        view.fetchOrderDetail(response.orderDetail());
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
      }
    }, orderId);
  }
}
