package tr.com.idefix.data.repository;

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

/**
 * Created by utkan on 13/09/15.
 */
public interface ICustomerRepository {

  Observable<BasketCountEntity> getBasketCount(String sessionID);

  Observable<CustomerInfoEntity> getInfo(String sessionID);

  Observable<WishListResponseEntity> getWishList(String sessionID);

  Observable<AddItemToWishListResponseEntity> addItemToWishList(String sessionID, String sku);

  Observable<RemoveInterestedListItemResponseEntity> removeItemFromWishList(
      String sessionID, String sku
  );

  Observable<AlarmListResponseEntity> getAlarmList(String sessionID);

  Observable<AddItemToAlarmListResponseEntity> addItemToAlarmList(
      String sessionID, AlarmItemAddRequestEntity alarmItemAddRequestEntity
  );

  Observable<RemoveInterestedListItemResponseEntity> removeItemFromAlarmList(
      String sessionID, String sku
  );

  Observable<CustomerInfoResponseEntity> changeInfo(
      CustomerInfoRequestEntity customerInfoResponseEntity, String sessionID
  );

  Observable<ChangePasswordResponseEntity> changePassword(
      ChangePasswordRequestEntity changePasswordRequestEntity, String sessionID
  );

  Observable<BasketItemResponseEntity> moveItemsToCartFromWishlist(String code, String sessionID);

  Observable<BasketItemResponseEntity> removeInterestedListItem(String code, String sessionID);

  Observable<BasketItemResponseEntity> moveItemsToCartFromAlarmList(String code, String sessionID);

  Observable<BasketItemResponseEntity> removeAlarmListItem(String code, String sessionID);

  Observable<CustomerOrdersBaseEntity> getCustomerOrders(String sessionID);

  Observable<OrderDetailResponseEntity> getOrderDetail(String orderId, String sessionID);

  Observable<SendPaymentInfoForOrderResponse> sendPaymentInfoForOrderResponse(
      SendPaymentInfoForOrderRequest request
  );
}
