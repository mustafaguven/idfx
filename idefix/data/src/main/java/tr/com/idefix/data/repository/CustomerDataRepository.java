package tr.com.idefix.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
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
import tr.com.idefix.data.entity.OrderDetailResponseEntity;
import tr.com.idefix.data.entity.RemoveInterestedListItemResponseEntity;
import tr.com.idefix.data.entity.SendPaymentInfoForOrderRequest;
import tr.com.idefix.data.entity.SendPaymentInfoForOrderResponse;
import tr.com.idefix.data.entity.WishListResponseEntity;
import tr.com.idefix.data.repository.datasource.customer.CustomerDataStore;
import tr.com.idefix.data.repository.datasource.customer.CustomerDataStoreFactory;

/**
 * Created by utkan on 13/09/15.
 */
@Singleton public class CustomerDataRepository implements ICustomerRepository {

  private final CustomerDataStoreFactory apiCustomerDataStoreFactory;

  @Inject public CustomerDataRepository() {
    this.apiCustomerDataStoreFactory = new CustomerDataStoreFactory();
  }

  @Override public Observable<BasketCountEntity> getBasketCount(String sessionID) {
    final CustomerDataStore apiCustomerDataStore =
        apiCustomerDataStoreFactory.createCloudDataStore();
    return apiCustomerDataStore.getBasketCount(sessionID);
  }

  @Override public Observable<CustomerInfoEntity> getInfo(String sessionID) {
    final CustomerDataStore apiCustomerDataStore =
        apiCustomerDataStoreFactory.createCloudDataStore();
    return apiCustomerDataStore.getInfo(sessionID);
  }

  @Override public Observable<WishListResponseEntity> getWishList(String sessionID) {
    final CustomerDataStore apiCustomerDataStore =
        apiCustomerDataStoreFactory.createCloudDataStore();
    return apiCustomerDataStore.getWishList(sessionID);
  }

  @Override public Observable<AddItemToWishListResponseEntity> addItemToWishList(
      String sessionID, String sku
  ) {
    final CustomerDataStore apiCustomerDataStore =
        apiCustomerDataStoreFactory.createCloudDataStore();
    return apiCustomerDataStore.addItemToWishList(sessionID, sku);
  }

  @Override public Observable<RemoveInterestedListItemResponseEntity> removeItemFromWishList(
      String sessionID, String sku
  ) {
    final CustomerDataStore apiCustomerDataStore =
        apiCustomerDataStoreFactory.createCloudDataStore();
    return apiCustomerDataStore.removeItemFromWishList(sessionID, sku);
  }

  @Override public Observable<AlarmListResponseEntity> getAlarmList(String sessionID) {
    final CustomerDataStore apiCustomerDataStore =
        apiCustomerDataStoreFactory.createCloudDataStore();
    return apiCustomerDataStore.getAlarmList(sessionID);
  }

  @Override public Observable<AddItemToAlarmListResponseEntity> addItemToAlarmList(
      String sessionID, AlarmItemAddRequestEntity alarmItemAddRequestEntity
  ) {
    final CustomerDataStore apiCustomerDataStore =
        apiCustomerDataStoreFactory.createCloudDataStore();
    return apiCustomerDataStore.addItemAlarmList(sessionID, alarmItemAddRequestEntity);
  }

  @Override public Observable<RemoveInterestedListItemResponseEntity> removeItemFromAlarmList(
      String sessionID, String sku
  ) {
    final CustomerDataStore apiCustomerDataStore =
        apiCustomerDataStoreFactory.createCloudDataStore();
    return apiCustomerDataStore.removeItemFromAlarmList(sessionID, sku);
  }

  @Override public Observable<ChangePasswordResponseEntity> changePassword(
      ChangePasswordRequestEntity changePasswordRequestEntity, String sessionID
  ) {
    final CustomerDataStore apiCustomerDataStore =
        apiCustomerDataStoreFactory.createCloudDataStore();
    return apiCustomerDataStore.changePassword(changePasswordRequestEntity, sessionID);
  }

  @Override public Observable<BasketItemResponseEntity> moveItemsToCartFromWishlist(
      String code, String sessionID
  ) {
    final CustomerDataStore apiCustomerDataStore =
        apiCustomerDataStoreFactory.createCloudDataStore();
    return apiCustomerDataStore.moveItemsToCartFromWishlist(code, sessionID);
  }

  @Override public Observable<BasketItemResponseEntity> removeInterestedListItem(
      String code, String sessionID
  ) {
    final CustomerDataStore apiCustomerDataStore =
        apiCustomerDataStoreFactory.createCloudDataStore();
    return apiCustomerDataStore.removeInterestedListItem(code, sessionID);
  }

  @Override public Observable<BasketItemResponseEntity> moveItemsToCartFromAlarmList(
      String code, String sessionID
  ) {
    final CustomerDataStore apiCustomerDataStore =
        apiCustomerDataStoreFactory.createCloudDataStore();
    return apiCustomerDataStore.moveItemsToCartFromAlarmList(code, sessionID);
  }

  @Override
  public Observable<BasketItemResponseEntity> removeAlarmListItem(String code, String sessionID) {
    final CustomerDataStore apiCustomerDataStore =
        apiCustomerDataStoreFactory.createCloudDataStore();
    return apiCustomerDataStore.removeAlarmListItem(code, sessionID);
  }

  @Override public Observable<CustomerOrdersBaseEntity> getCustomerOrders(String sessionID) {
    final CustomerDataStore apiCustomerDataStore =
        apiCustomerDataStoreFactory.createCloudDataStore();
    return apiCustomerDataStore.getCustomerOrders(sessionID);
  }

  @Override public Observable<OrderDetailResponseEntity> getOrderDetail(
      String orderId, String sessionID
  ) {
    final CustomerDataStore apiCustomerDataStore =
        apiCustomerDataStoreFactory.createCloudDataStore();
    return apiCustomerDataStore.getCustomOrderDetails(orderId, sessionID);
  }

  @Override public Observable<SendPaymentInfoForOrderResponse> sendPaymentInfoForOrderResponse(
      SendPaymentInfoForOrderRequest request
  ) {
    final CustomerDataStore apiCustomerDataStore =
        apiCustomerDataStoreFactory.createCloudDataStore();
    return apiCustomerDataStore.sendPaymentInfoForOrder(request);
  }

  @Override public Observable<CustomerInfoResponseEntity> changeInfo(
      CustomerInfoRequestEntity customerInfoResponseEntity, String sessionID
  ) {
    final CustomerDataStore apiCustomerDataStore =
        apiCustomerDataStoreFactory.createCloudDataStore();
    return apiCustomerDataStore.changeCustomerInfo(customerInfoResponseEntity, sessionID);
  }
}
