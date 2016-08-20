package tr.com.idefix.data.net;

import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;
import tr.com.idefix.data.entity.AddItemToAlarmListResponseEntity;
import tr.com.idefix.data.entity.AddItemToWishListResponseEntity;
import tr.com.idefix.data.entity.AlarmItemAddRequestEntity;
import tr.com.idefix.data.entity.AlarmListResponseEntity;
import tr.com.idefix.data.entity.BasketCountEntity;
import tr.com.idefix.data.entity.BasketItemResponseEntity;
import tr.com.idefix.data.entity.ChangePasswordRequestEntity;
import tr.com.idefix.data.entity.ChangePasswordResponseEntity;
import tr.com.idefix.data.entity.CustomerInfoRequestEntity;
import tr.com.idefix.data.entity.CustomerInfoResponseEntity;
import tr.com.idefix.data.entity.CustomerOrdersBaseEntity;
import tr.com.idefix.data.entity.EmptyRequestBody;
import tr.com.idefix.data.entity.OrderDetailResponseEntity;
import tr.com.idefix.data.entity.RemoveInterestedListItemResponseEntity;
import tr.com.idefix.data.entity.SendPaymentInfoForOrderRequest;
import tr.com.idefix.data.entity.SendPaymentInfoForOrderResponse;
import tr.com.idefix.data.entity.WishListResponseEntity;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApiCustomer {

  @POST("/GetShoppingCartItemCount/") Observable<BasketCountEntity> getBasketCount(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  );

  @POST("/Info/") Observable<CustomerInfoResponseEntity> getInfo(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  );

  @POST("/WishList") Observable<WishListResponseEntity> getWishList(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  );

  @POST("/AddItemsToWishlist") Observable<AddItemToWishListResponseEntity> addItemToWishlist(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "signedRequest", encodeValue = false) String sessionID,
      @Query(value = "code", encodeValue = false) String sku
  );

  @POST("/RemoveInterestedListItem")
  Observable<RemoveInterestedListItemResponseEntity> removeItemFromWishlist(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "signedRequest", encodeValue = false) String sessionID,
      @Query(value = "code", encodeValue = false) String sku
  );

  @POST("/AlarmList") Observable<AlarmListResponseEntity> getAlarmList(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  );

  @POST("/AddItemsToAlarmlist") Observable<AddItemToAlarmListResponseEntity> addItemToAlarmlist(
      @Body AlarmItemAddRequestEntity alarmItemAddRequestEntity,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  );

  @POST("/RemoveAlarmListItem")
  Observable<RemoveInterestedListItemResponseEntity> removeAlarmListItem(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "signedRequest", encodeValue = false) String sessionID,
      @Query(value = "code", encodeValue = false) String sku
  );

  //    @POST("/ProductReviewsAdd")
  //    Observable<EmptyResponseBody> productReviewsAdd(
  //            @Body ReviewRequestEntity reviewRequestEntity);

  @POST("/ChangeCustomerInfo/") Observable<CustomerInfoResponseEntity> changeInfo(
      @Body CustomerInfoRequestEntity customerInfoRequestEntity,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  );

  @POST("/ChangePassword/") Observable<ChangePasswordResponseEntity> changePassword(
      @Body ChangePasswordRequestEntity changePasswordRequestEntity,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  );

  @POST("/MoveItemsToCartFromWishlist/")
  Observable<BasketItemResponseEntity> moveItemsToCartFromWishlist(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "code", encodeValue = false) String code,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  );

  @POST("/RemoveInterestedListItem/") Observable<BasketItemResponseEntity> removeInterestedListItem(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "code", encodeValue = false) String code,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  );

  @POST("/MoveItemsToCartFromAlarmlist/")
  Observable<BasketItemResponseEntity> moveItemsToCartFromAlarmlist(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "code", encodeValue = false) String code,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  );

  @POST("/RemoveAlarmListItem/") Observable<BasketItemResponseEntity> removeAlarmListItem_2(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "code", encodeValue = false) String code,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  );

  @POST("/CustomerOrders/") Observable<CustomerOrdersBaseEntity> getCustomerOrders(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  );

  @POST("/OrderDetails/") Observable<OrderDetailResponseEntity> getCustomerOrderDetails(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "OrderId", encodeValue = false) String orderId,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  );

  @POST("/SendPaymentInfoForOrder/")
  Observable<SendPaymentInfoForOrderResponse> sendPaymentInfoForOrder(
      @Body SendPaymentInfoForOrderRequest sendPaymentInfoForOrderRequest
  );
}
