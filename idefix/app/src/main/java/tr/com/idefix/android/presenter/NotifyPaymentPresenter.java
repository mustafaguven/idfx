package tr.com.idefix.android.presenter;

import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.view.INotifyPaymentView;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.data.entity.AvailableBankResponse;
import tr.com.idefix.data.entity.SendPaymentInfoForOrderRequest;
import tr.com.idefix.data.entity.SendPaymentInfoForOrderResponse;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICommonInterActor;
import tr.com.idefix.domain.interactor.ICustomerInterActor;

public class NotifyPaymentPresenter implements INotifyPaymentPresenter {

  DomainContext domainContext;
  INotifyPaymentView view;
  ICustomerInterActor customerInterActor;
  ICommonInterActor commonInterActor;

  public NotifyPaymentPresenter(
      ICustomerInterActor iCustomerInterActor, ICommonInterActor commonInterActor
  ) {
    this.customerInterActor = iCustomerInterActor;
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
    this.view = (INotifyPaymentView) iView;
  }

  @Override public void getAvailableBankAccounts() {
    commonInterActor.getAvailableBankAccounts(new DefaultSubscriber<AvailableBankResponse>() {
      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.hideProgress();
      }

      @Override public void onNext(AvailableBankResponse response) {
        view.fetchBankAccounts(response.bankEntityList());
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
      }
    });
  }

  @Override public void sendNotifyPayment(
      String orderCode, String bankInformation, String date, String description
  ) {
    SendPaymentInfoForOrderRequest request = new SendPaymentInfoForOrderRequest();
    request.setCustomerName(domainContext.getCustomerInfo().username());
    request.setOrderCode(orderCode);
    request.setBankInformation(bankInformation);
    request.setDate(date);
    request.setDescription(description);
    customerInterActor.sendPaymentInfoForOrder(
        new DefaultSubscriber<SendPaymentInfoForOrderResponse>() {
          @Override public void onStart() {
            super.onStart();
            view.showProgress();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            view.hideProgress();
          }

          @Override public void onNext(SendPaymentInfoForOrderResponse response) {
            view.sentPaymentInfoForOrderSuccesfully(response);
          }

          @Override public void onCompleted() {
            super.onCompleted();
            view.hideProgress();
          }
        }, request);
  }
}
