package tr.com.idefix.data.repository.datasource.customer;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tr.com.idefix.data.entity.AddItemToAlarmListResponseEntity;
import tr.com.idefix.data.entity.AddItemToWishListResponseEntity;
import tr.com.idefix.data.entity.AlarmItemAddRequestEntity;
import tr.com.idefix.data.entity.AlarmListResponseEntity;
import tr.com.idefix.data.entity.BasketCountEntity;
import tr.com.idefix.data.entity.BasketItemResponseEntity;
import tr.com.idefix.data.entity.ChangePasswordRequestEntity;
import tr.com.idefix.data.entity.ChangePasswordResponseEntity;
import tr.com.idefix.data.entity.CustomerInfoEntity;
import tr.com.idefix.data.entity.CustomerInfoRequestEntity;
import tr.com.idefix.data.entity.CustomerInfoResponseEntity;
import tr.com.idefix.data.entity.CustomerOrdersBaseEntity;
import tr.com.idefix.data.entity.EmptyRequestBody;
import tr.com.idefix.data.entity.OrderDetailResponseEntity;
import tr.com.idefix.data.entity.RemoveInterestedListItemResponseEntity;
import tr.com.idefix.data.entity.SendPaymentInfoForOrderRequest;
import tr.com.idefix.data.entity.SendPaymentInfoForOrderResponse;
import tr.com.idefix.data.entity.WishListResponseEntity;
import tr.com.idefix.data.net.RestApiCustomer;

public class CloudCustomerDataStore implements CustomerDataStore {

  private final RestApiCustomer restApi;

  public CloudCustomerDataStore(RestApiCustomer restApi) {
    this.restApi = restApi;
  }

  @Override public Observable<BasketCountEntity> getBasketCount(String sessionID) {
    return restApi.getBasketCount(new EmptyRequestBody(), sessionID)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<CustomerInfoEntity> getInfo(String sessionID) {
    return restApi.getInfo(new EmptyRequestBody(), sessionID).map(customerInfoResponseEntity -> {
      if (customerInfoResponseEntity != null && customerInfoResponseEntity.getSuccess() == null) {
        return customerInfoResponseEntity.getCustomerInfo();
      }
      return null;
    }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<WishListResponseEntity> getWishList(String sessionID) {
    return restApi.getWishList(new EmptyRequestBody(), sessionID)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<AddItemToWishListResponseEntity> addItemToWishList(
      String sessionID, String sku
  ) {
    return restApi.addItemToWishlist(new EmptyRequestBody(), sessionID, sku)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<RemoveInterestedListItemResponseEntity> removeItemFromWishList(
      String sessionID, String sku
  ) {
    return restApi.removeItemFromWishlist(new EmptyRequestBody(), sessionID, sku)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<AlarmListResponseEntity> getAlarmList(String sessionID) {
    return restApi.getAlarmList(new EmptyRequestBody(), sessionID)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<AddItemToAlarmListResponseEntity> addItemAlarmList(
      String sessionID, AlarmItemAddRequestEntity alarmItemAddRequestEntity
  ) {
    return restApi.addItemToAlarmlist(alarmItemAddRequestEntity, sessionID)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<RemoveInterestedListItemResponseEntity> removeItemFromAlarmList(
      String sessionID, String sku
  ) {
    return restApi.removeAlarmListItem(new EmptyRequestBody(), sessionID, sku)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<CustomerInfoResponseEntity> changeCustomerInfo(
      CustomerInfoRequestEntity customerInfoRequestEntity, String sessionID
  ) {
    return restApi.changeInfo(customerInfoRequestEntity, sessionID)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<ChangePasswordResponseEntity> changePassword(
      ChangePasswordRequestEntity changePasswordRequestEntity, String sessionID
  ) {
    return restApi.changePassword(changePasswordRequestEntity, sessionID)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<BasketItemResponseEntity> moveItemsToCartFromWishlist(
      String code, String sessionID
  ) {
    return restApi.moveItemsToCartFromWishlist(new EmptyRequestBody(), code, sessionID)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<BasketItemResponseEntity> removeInterestedListItem(
      String code, String sessionID
  ) {
    return restApi.removeInterestedListItem(new EmptyRequestBody(), code, sessionID)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<BasketItemResponseEntity> moveItemsToCartFromAlarmList(
      String code, String sessionID
  ) {
    return restApi.moveItemsToCartFromAlarmlist(new EmptyRequestBody(), code, sessionID)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override
  public Observable<BasketItemResponseEntity> removeAlarmListItem(String code, String sessionID) {
    return restApi.removeAlarmListItem_2(new EmptyRequestBody(), code, sessionID)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<CustomerOrdersBaseEntity> getCustomerOrders(String sessionID) {
    return restApi.getCustomerOrders(new EmptyRequestBody(), sessionID)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<OrderDetailResponseEntity> getCustomOrderDetails(
      String orderId, String sessionID
  ) {
    return restApi.getCustomerOrderDetails(new EmptyRequestBody(), orderId, sessionID)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<SendPaymentInfoForOrderResponse> sendPaymentInfoForOrder(
      SendPaymentInfoForOrderRequest request
  ) {
    return restApi.sendPaymentInfoForOrder(request)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
