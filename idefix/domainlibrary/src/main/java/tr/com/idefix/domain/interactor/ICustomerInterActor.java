package tr.com.idefix.domain.interactor;

import tr.com.idefix.data.entity.BasketItemResponseEntity;
import tr.com.idefix.data.entity.ChangePasswordRequestEntity;
import tr.com.idefix.data.entity.ChangePasswordResponseEntity;
import tr.com.idefix.data.entity.CustomerInfoRequestEntity;
import tr.com.idefix.data.entity.CustomerInfoResponseEntity;
import tr.com.idefix.data.entity.CustomerOrdersBaseEntity;
import tr.com.idefix.data.entity.OrderDetailResponseEntity;
import tr.com.idefix.data.entity.SendPaymentInfoForOrderRequest;
import tr.com.idefix.data.entity.SendPaymentInfoForOrderResponse;
import tr.com.idefix.domain.Alarm;
import tr.com.idefix.domain.BasketCount;
import tr.com.idefix.domain.CustomerInfo;
import tr.com.idefix.domain.Wish;

/**
 * Created by utkan on 13/09/15.
 */
public interface ICustomerInterActor extends IInterActor {

  void getBasketCount(DefaultSubscriber<BasketCount> subscriber);

  void getInfo(DefaultSubscriber<CustomerInfo> subscriber);

  void getWishList(DefaultSubscriber<Wish> subscriber);

  void addItemToWishList(DefaultSubscriber<Wish> subscriber, String sku);

  void removeItemFromWishList(DefaultSubscriber<Boolean> subscriber, String sku);

  void getAlarmList(DefaultSubscriber<Alarm> subscriber);

  void addItemToAlarmList(
      DefaultSubscriber<Alarm> subscriber, String sku, String days, String price
  );

  void removeItemFromAlarmList(DefaultSubscriber<Boolean> subscriber, String sku);

  void changeInfo(
      DefaultSubscriber<CustomerInfoResponseEntity> subscriber,
      CustomerInfoRequestEntity customerInfoRequestEntity
  );

  void changePassword(
      DefaultSubscriber<ChangePasswordResponseEntity> subscriber,
      ChangePasswordRequestEntity changePasswordRequestEntity
  );

  void moveItemsToCartFromWishlist(
      DefaultSubscriber<BasketItemResponseEntity> subscriber, String code
  );

  void removeInterestedListItem(
      DefaultSubscriber<BasketItemResponseEntity> subscriber, String code
  );

  void moveItemsToCartFromAlarmlist(
      DefaultSubscriber<BasketItemResponseEntity> subscriber, String code
  );

  void removeAlarmListItem(DefaultSubscriber<BasketItemResponseEntity> subscriber, String code);

  void getCustomerOrders(DefaultSubscriber<CustomerOrdersBaseEntity> subscriber);

  void getOrderDetails(DefaultSubscriber<OrderDetailResponseEntity> subscriber, String orderId);

  void sendPaymentInfoForOrder(
      DefaultSubscriber<SendPaymentInfoForOrderResponse> subscriber,
      SendPaymentInfoForOrderRequest request
  );
}
