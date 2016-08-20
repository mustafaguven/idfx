package tr.com.idefix.android.presenter;

import tr.com.idefix.android.mapper.CustomerDataMapper;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.domain.Alarm;
import tr.com.idefix.domain.BasketCount;
import tr.com.idefix.domain.Wish;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICustomerInterActor;

/**
 * Created on 11.3.15.
 */
public abstract class BaseCustomerPresenter extends BasePresenter {

  protected final CustomerDataMapper customerDataMapper;
  protected final ICustomerInterActor customerInterActor;

  protected IView iview;

  public BaseCustomerPresenter(
      CustomerDataMapper apiCustomerDataMapper, ICustomerInterActor customerInterActor
  ) {
    super();
    this.customerDataMapper = apiCustomerDataMapper;
    this.customerInterActor = customerInterActor;
  }

  void getAlarmList() {
    customerInterActor.getAlarmList(new DefaultSubscriber<Alarm>() {

      @Override public void onStart() {
        super.onStart();
      }

      @Override public void onNext(Alarm alarm) {
        super.onNext(alarm);
        onNextAlarm();
      }

      @Override public void onCompleted() {
        super.onCompleted();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
      }
    });
  }

  abstract void onNextAlarm();

  void getWishList() {
    customerInterActor.getWishList(new DefaultSubscriber<Wish>() {

      @Override public void onStart() {
        super.onStart();
      }

      @Override public void onNext(Wish wish) {
        super.onNext(wish);
      }

      @Override public void onCompleted() {
        super.onCompleted();
        onNextWish();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
      }
    });
  }

  protected abstract void onNextWish();

  void getBasketCount() {

    customerInterActor.getBasketCount(new DefaultSubscriber<BasketCount>() {

      @Override public void onStart() {
        super.onStart();
      }

      @Override public void onNext(BasketCount basketCount) {
        super.onNext(basketCount);

        if (basketCount != null) {
          iview.setBasketCount(basketCount.cartItemCount());
        }
      }

      @Override public void onCompleted() {
        super.onCompleted();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
      }
    });
  }
}
