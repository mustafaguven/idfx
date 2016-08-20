package tr.com.idefix.domain.interactor;

import android.text.TextUtils;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;
import tr.com.idefix.data.entity.AlarmItemAddRequestEntity;
import tr.com.idefix.data.entity.BasketItemResponseEntity;
import tr.com.idefix.data.entity.ChangePasswordRequestEntity;
import tr.com.idefix.data.entity.ChangePasswordResponseEntity;
import tr.com.idefix.data.entity.CustomerInfoRequestEntity;
import tr.com.idefix.data.entity.CustomerInfoResponseEntity;
import tr.com.idefix.data.entity.CustomerOrdersBaseEntity;
import tr.com.idefix.data.entity.OrderDetailResponseEntity;
import tr.com.idefix.data.entity.SendPaymentInfoForOrderRequest;
import tr.com.idefix.data.entity.SendPaymentInfoForOrderResponse;
import tr.com.idefix.data.net.RestApiBaseService;
import tr.com.idefix.data.repository.CustomerDataRepository;
import tr.com.idefix.data.repository.ICustomerRepository;
import tr.com.idefix.domain.Alarm;
import tr.com.idefix.domain.BasketCount;
import tr.com.idefix.domain.CustomerInfo;
import tr.com.idefix.domain.DomainApplication;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.Wish;
import tr.com.idefix.domain.mapper.CustomerEntityDataMapper;

/**
 * Created by utkan on 13/09/15.
 */
public class CustomerInterActor implements ICustomerInterActor {

  private final ICustomerRepository repository;
  CustomerEntityDataMapper apiCustomerEntityDataMapper;
  DomainContext domainContext;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject public CustomerInterActor() {
    this.repository = new CustomerDataRepository();
    domainContext = DomainApplication.getInstance().getDomainApplicationComponent().domainContext();

    if (RestApiBaseService.getMemoryCache() == null) {
      RestApiBaseService.setMemoryCache(domainContext.getCache());
    }
  }

  @Override public void unSubscribe() {
    compositeSubscription.unsubscribe();
  }

  @Override public void getBasketCount(DefaultSubscriber<BasketCount> subscriber) {

    String sessionID = getSessionID();

    if (TextUtils.isEmpty(sessionID)) {
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

    if (apiCustomerEntityDataMapper == null) {
      apiCustomerEntityDataMapper = new CustomerEntityDataMapper();
    }

    compositeSubscription.add(repository.getBasketCount(sessionID)
        .map(apiCustomerEntityDataMapper::transform)
        .subscribe(subscriber));
  }

  @Override public void getInfo(DefaultSubscriber<CustomerInfo> subscriber) {

    String sessionID = getSessionID();

    if (TextUtils.isEmpty(sessionID)) {
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

    if (apiCustomerEntityDataMapper == null) {
      apiCustomerEntityDataMapper = new CustomerEntityDataMapper();
    }

    compositeSubscription.add(repository.getInfo(sessionID)
        .map(apiCustomerEntityDataMapper::transform)
        .doOnNext(domainContext::setCustomerInfo)
        .doOnError(throwable -> {
          Timber.e(throwable, "Can't get info");
          domainContext.setCustomerInfo(null);
          domainContext.setLoggedInUser(null);
        })
        .subscribe(subscriber));
  }

  @Override public void getWishList(DefaultSubscriber<Wish> subscriber) {

    String sessionID = getSessionID();

    if (TextUtils.isEmpty(sessionID)) {
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

/*        if (domainContext.getCachedWish() != null) {
            subscriber.onNext(domainContext.getCachedWish());
            subscriber.onCompleted();
            return;
        }*/

    if (apiCustomerEntityDataMapper == null) {
      apiCustomerEntityDataMapper = new CustomerEntityDataMapper();
    }

    compositeSubscription.add(repository.getWishList(sessionID)
        .map(apiCustomerEntityDataMapper::transform)
        .doOnNext(wish -> domainContext.setWishes(wish))
        .doOnError(throwable -> {
          Timber.e(throwable, "Can't get wishlist");
          domainContext.setWishes(null);
        })
        .subscribe(subscriber));
  }

  @Override public void addItemToWishList(DefaultSubscriber<Wish> subscriber, String sku) {

    String sessionID = getSessionID();

    if (TextUtils.isEmpty(sessionID)) {
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

    if (apiCustomerEntityDataMapper == null) {
      apiCustomerEntityDataMapper = new CustomerEntityDataMapper();
    }

    compositeSubscription.add(repository.addItemToWishList(sessionID, sku)
        .map(apiCustomerEntityDataMapper::transform)
        .doOnNext(domainContext::setWishes)
        .doOnError(throwable -> domainContext.setWishes(null))
        .subscribe(subscriber));
  }

  @Override public void removeItemFromWishList(DefaultSubscriber<Boolean> subscriber, String sku) {

    String sessionID = getSessionID();

    if (TextUtils.isEmpty(sessionID)) {
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

    if (apiCustomerEntityDataMapper == null) {
      apiCustomerEntityDataMapper = new CustomerEntityDataMapper();
    }

    compositeSubscription.add(repository.removeItemFromWishList(sessionID, sku)
        .flatMap(removeInterestedListItemResponseEntity -> {
          if (removeInterestedListItemResponseEntity != null
              && removeInterestedListItemResponseEntity.status() != null) {
            return Observable.just(removeInterestedListItemResponseEntity.status());
          }
          return null;
        })
        .doOnNext(aBoolean -> {
          if (aBoolean != null && aBoolean) {
            domainContext.removeSkuFromWishCache(sku);
          }
        })
        .subscribe(subscriber));
  }

  @Override public void getAlarmList(DefaultSubscriber<Alarm> subscriber) {

    String sessionID = getSessionID();

    if (TextUtils.isEmpty(sessionID)) {
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

    if (apiCustomerEntityDataMapper == null) {
      apiCustomerEntityDataMapper = new CustomerEntityDataMapper();
    }

    compositeSubscription.add(repository.getAlarmList(sessionID)
        .map(apiCustomerEntityDataMapper::transform)
        .doOnNext(domainContext::setAlarms)
        .doOnError(throwable -> {
          domainContext.setAlarms(null);
        })
        .subscribe(subscriber));
  }

  @Override public void addItemToAlarmList(
      DefaultSubscriber<Alarm> subscriber, String sku, String days, String price
  ) {

    String sessionID = getSessionID();

    if (TextUtils.isEmpty(sessionID)) {
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

    if (apiCustomerEntityDataMapper == null) {
      apiCustomerEntityDataMapper = new CustomerEntityDataMapper();
    }

    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());

    if (!TextUtils.isEmpty(days)) {
      try {
        cal.add(Calendar.DATE, Integer.parseInt(days));
      } catch (Exception e) {
        //XXX: IGNORE
        e.printStackTrace();
      }
    }
    //2015-09-25
    String jDate = String.format("%s-%s-%s", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
        cal.get(Calendar.DAY_OF_MONTH));

    compositeSubscription.add(repository.addItemToAlarmList(sessionID,
        new AlarmItemAddRequestEntity().code(sku).date(jDate).price(price))
        .map(apiCustomerEntityDataMapper::transform)
        .doOnNext(domainContext::setAlarms)
        .doOnError(throwable -> domainContext.setAlarms(null))
        .subscribe(subscriber));
  }

  @Override public void removeItemFromAlarmList(DefaultSubscriber<Boolean> subscriber, String sku) {

    String sessionID = getSessionID();

    if (TextUtils.isEmpty(sessionID)) {
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

    if (apiCustomerEntityDataMapper == null) {
      apiCustomerEntityDataMapper = new CustomerEntityDataMapper();
    }

    compositeSubscription.add(repository.removeItemFromAlarmList(sessionID, sku)
        .flatMap(removeInterestedListItemResponseEntity -> {
          if (removeInterestedListItemResponseEntity != null
              && removeInterestedListItemResponseEntity.status() != null) {
            return Observable.just(removeInterestedListItemResponseEntity.status());
          }
          return null;
        })
        .doOnNext(aBoolean -> {
          if (aBoolean != null && aBoolean) {
            domainContext.removeSkuFromAlarmCache(sku);
          }
        })
        .subscribe(subscriber));
  }

  String getSessionID() {

    return domainContext != null ? domainContext.getLoggedInUser() != null
        ? domainContext.getLoggedInUser().sessionObject() : null : null;
  }

  @Override public void changeInfo(
      DefaultSubscriber<CustomerInfoResponseEntity> subscriber,
      CustomerInfoRequestEntity customerInfoRequestEntity
  ) {
    String sessionID = getSessionID();

    if (TextUtils.isEmpty(sessionID)) {
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

    if (apiCustomerEntityDataMapper == null) {
      apiCustomerEntityDataMapper = new CustomerEntityDataMapper();
    }

    compositeSubscription.add(repository.changeInfo(customerInfoRequestEntity, sessionID)
        .doOnNext(new Action1<CustomerInfoResponseEntity>() {
          @Override public void call(CustomerInfoResponseEntity customerInfoResponseEntity) {
            int a = 0;
          }
        })
        .doOnError(throwable -> {
          Timber.e(throwable, "Can't change info");
        })
        .subscribe(subscriber));
  }

  @Override public void changePassword(
      DefaultSubscriber<ChangePasswordResponseEntity> subscriber,
      ChangePasswordRequestEntity changePasswordRequestEntity
  ) {
    String sessionID = getSessionID();

    if (TextUtils.isEmpty(sessionID)) {
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

    if (apiCustomerEntityDataMapper == null) {
      apiCustomerEntityDataMapper = new CustomerEntityDataMapper();
    }

    compositeSubscription.add(repository.changePassword(changePasswordRequestEntity, sessionID)
        //.map(apiCustomerEntityDataMapper::transform)
        .doOnNext(
            (ChangePasswordResponseEntity changePasswordResponseEntity) -> domainContext
                .setLoggedInUser(
                domainContext.getLoggedInUser()
                    .password(changePasswordResponseEntity.newpassword()))).doOnError(throwable -> {
          Timber.e(throwable, "Can't change password");
        }).subscribe(subscriber));
  }

  @Override public void moveItemsToCartFromWishlist(
      DefaultSubscriber<BasketItemResponseEntity> subscriber, String code
  ) {
    String sessionID = getSessionID();

    if (TextUtils.isEmpty(sessionID)) {
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

    if (apiCustomerEntityDataMapper == null) {
      apiCustomerEntityDataMapper = new CustomerEntityDataMapper();
    }

    compositeSubscription.add(repository.moveItemsToCartFromWishlist(code, sessionID)
        //.map(apiCustomerEntityDataMapper::transform)
        .doOnNext(basketItemResponseEntity -> {
        }).doOnError(throwable -> {
          Timber.e(throwable, "Can't moveitemstocartfromwishlist");
        }).subscribe(subscriber));
  }

  @Override public void removeInterestedListItem(
      DefaultSubscriber<BasketItemResponseEntity> subscriber, String code
  ) {
    String sessionID = getSessionID();

    if (TextUtils.isEmpty(sessionID)) {
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

    if (apiCustomerEntityDataMapper == null) {
      apiCustomerEntityDataMapper = new CustomerEntityDataMapper();
    }

    compositeSubscription.add(repository.removeInterestedListItem(code, sessionID)
        //.map(apiCustomerEntityDataMapper::transform)
        .doOnNext(basketItemResponseEntity -> domainContext.removeSkuFromWishCache(code))
        .doOnError(throwable -> {
          Timber.e(throwable, "Can't removeInterestedListItem");
        })
        .subscribe(subscriber));
  }

  @Override public void moveItemsToCartFromAlarmlist(
      DefaultSubscriber<BasketItemResponseEntity> subscriber, String code
  ) {
    String sessionID = getSessionID();

    if (TextUtils.isEmpty(sessionID)) {
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

    if (apiCustomerEntityDataMapper == null) {
      apiCustomerEntityDataMapper = new CustomerEntityDataMapper();
    }

    compositeSubscription.add(repository.moveItemsToCartFromAlarmList(code, sessionID)
        //.map(apiCustomerEntityDataMapper::transform)
        .doOnNext(basketItemResponseEntity -> {
        }).doOnError(throwable -> {
          Timber.e(throwable, "Can't moveItemsToCartFromAlarmlist");
        }).subscribe(subscriber));
  }

  @Override public void removeAlarmListItem(
      DefaultSubscriber<BasketItemResponseEntity> subscriber, String code
  ) {
    String sessionID = getSessionID();

    if (TextUtils.isEmpty(sessionID)) {
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

    if (apiCustomerEntityDataMapper == null) {
      apiCustomerEntityDataMapper = new CustomerEntityDataMapper();
    }

    compositeSubscription.add(repository.removeAlarmListItem(code, sessionID)
        //.map(apiCustomerEntityDataMapper::transform)
        .doOnNext(basketItemResponseEntity -> domainContext.removeSkuFromAlarmCache(code))
        .doOnError(throwable -> {
          Timber.e(throwable, "Can't removeAlarmListItem");
        })
        .subscribe(subscriber));
  }

  @Override public void getCustomerOrders(DefaultSubscriber<CustomerOrdersBaseEntity> subscriber) {
    String sessionID = getSessionID();

    if (TextUtils.isEmpty(sessionID)) {
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

    if (apiCustomerEntityDataMapper == null) {
      apiCustomerEntityDataMapper = new CustomerEntityDataMapper();
    }

    compositeSubscription.add(repository.getCustomerOrders(sessionID)
        //.map(apiCustomerEntityDataMapper::transform)
        .doOnNext(customerOrdersBaseEntity -> {
        }).doOnError(throwable -> {
          Timber.e(throwable, "Can't getCustomerOrders");
        }).subscribe(subscriber));
  }

  @Override public void getOrderDetails(
      DefaultSubscriber<OrderDetailResponseEntity> subscriber, String orderId
  ) {
    if (apiCustomerEntityDataMapper == null) {
      apiCustomerEntityDataMapper = new CustomerEntityDataMapper();
    }

    compositeSubscription.add(
        repository.getOrderDetail(orderId, getSessionID()).subscribe(subscriber));
  }

  @Override public void sendPaymentInfoForOrder(
      DefaultSubscriber<SendPaymentInfoForOrderResponse> subscriber,
      SendPaymentInfoForOrderRequest request
  ) {
    request.setSignedRequest(getSessionID());
    if (apiCustomerEntityDataMapper == null) {
      apiCustomerEntityDataMapper = new CustomerEntityDataMapper();
    }

    compositeSubscription.add(
        repository.sendPaymentInfoForOrderResponse(request).subscribe(subscriber));
  }
}
