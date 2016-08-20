package tr.com.idefix.data.net;

import retrofit.http.Body;
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
import tr.com.idefix.data.exception.ResponseNullException;

/**
 * RestApi for retrieving data from the network.
 */
public class RestApiCustomerService extends RestApiBaseService<RestApiCustomer>
    implements RestApiCustomer {

  static final String API_CATALOG_URL = API_BASE_URL + "ApiCustomer/";

  public RestApiCustomerService() {

    super(API_CATALOG_URL, RestApiCustomer.class);
  }

  public RestApiCustomer getApi() {

    return restApi;
  }

  @Override public Observable<BasketCountEntity> getBasketCount(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "signedRequest", encodeValue = true) String sessionID
  ) {
    return getApi().getBasketCount(emptyRequestBody, sessionID).flatMap(entity -> {
      if (entity == null || (entity != null && entity.success() != null && !entity.success())) {
        return Observable.error(new ResponseNullException(entity != null ? entity.message() : ""));
      } else {
        return Observable.just(entity);
      }
    });
  }

  @Override public Observable<CustomerInfoResponseEntity> getInfo(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  ) {
    return getApi().getInfo(emptyRequestBody, sessionID).flatMap(entity -> {
      if (entity == null || (entity != null
          && entity.getSuccess() != null
          && !entity.getSuccess())) {
        return Observable.error(
            new ResponseNullException(entity != null ? entity.getMessage() : ""));
      } else {
        return Observable.just(entity);
      }
    });
  }

  @Override public Observable<WishListResponseEntity> getWishList(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  ) {
    return getApi().getWishList(emptyRequestBody, sessionID).flatMap(entity -> {
      //if (entity != null && entity.itemList() != null && entity.itemList().size() > 0) {
      return Observable.just(entity);
      //}
      //return null;
    });
  }

  @Override public Observable<AddItemToWishListResponseEntity> addItemToWishlist(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "signedRequest", encodeValue = false) String sessionID,
      @Query(value = "code", encodeValue = false) String sku
  ) {
    return getApi().addItemToWishlist(emptyRequestBody, sessionID, sku).flatMap(entity -> {
      if (entity != null &&
          entity.wishList() != null &&
          entity.wishList().items() != null &&
          entity.wishList().items().size() > 0) {
        return Observable.just(entity);
      }
      return null;
    });
  }

  @Override public Observable<RemoveInterestedListItemResponseEntity> removeItemFromWishlist(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "signedRequest", encodeValue = false) String sessionID,
      @Query(value = "code", encodeValue = false) String sku
  ) {
    return getApi().removeItemFromWishlist(emptyRequestBody, sessionID, sku);
  }

  @Override public Observable<AlarmListResponseEntity> getAlarmList(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  ) {
    return getApi().getAlarmList(emptyRequestBody, sessionID).flatMap(entity -> {
      //if (entity != null && entity.itemList() != null && entity.itemList().size() > 0) {
      return Observable.just(entity);
      //}
      //return null;
    });
  }

  @Override public Observable<AddItemToAlarmListResponseEntity> addItemToAlarmlist(
      @Body AlarmItemAddRequestEntity alarmItemAddRequestEntity,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  ) {
    return getApi().addItemToAlarmlist(alarmItemAddRequestEntity, sessionID).flatMap(entity -> {
      if (entity != null &&
          entity.alarmList() != null &&
          entity.alarmList().items() != null &&
          entity.alarmList().items().size() > 0) {
        return Observable.just(entity);
      }
      return null;
    });
  }

  @Override public Observable<RemoveInterestedListItemResponseEntity> removeAlarmListItem(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "signedRequest", encodeValue = false) String sessionID,
      @Query(value = "code", encodeValue = false) String sku
  ) {
    return getApi().removeAlarmListItem(emptyRequestBody, sessionID, sku);
  }

  //    @Override
  //    public Observable productReviewsAdd(
  //            @Body ReviewRequestEntity reviewRequestEntity) {
  //        return getApi().productReviewsAdd(reviewRequestEntity);
  //    }

  @Override public Observable<CustomerInfoResponseEntity> changeInfo(
      @Body CustomerInfoRequestEntity customerInfoRequestEntity,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  ) {
    return getApi().changeInfo(customerInfoRequestEntity, sessionID).flatMap(entity -> {
      if (entity == null || (entity != null
          && entity.getSuccess() != null
          && !entity.getSuccess())) {
        return Observable.error(
            new ResponseNullException(entity != null ? entity.getMessage() : ""));
      } else {
        return Observable.just(entity);
      }
    });
  }

  @Override public Observable<ChangePasswordResponseEntity> changePassword(
      @Body ChangePasswordRequestEntity changePasswordRequestEntity,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  ) {
    return getApi().changePassword(changePasswordRequestEntity, sessionID).flatMap(entity -> {
      if (entity == null) {
        return Observable.error(new ResponseNullException("Password could not change"));
      } else {
        return Observable.just(entity);
      }
    });
  }

  @Override public Observable<BasketItemResponseEntity> moveItemsToCartFromWishlist(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "code", encodeValue = false) String code,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  ) {
    return getApi().moveItemsToCartFromWishlist(emptyRequestBody, code, sessionID)
        .flatMap(entity -> {
          if (entity == null) {
            return Observable.error(new ResponseNullException(""));
          } else {
            return Observable.just(entity);
          }
        });
  }

  @Override public Observable<BasketItemResponseEntity> removeInterestedListItem(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "code", encodeValue = false) String code,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  ) {
    return getApi().removeInterestedListItem(emptyRequestBody, code, sessionID).flatMap(entity -> {
      if (entity == null) {
        return Observable.error(new ResponseNullException(""));
      } else {
        return Observable.just(entity);
      }
    });
  }

  @Override public Observable<BasketItemResponseEntity> moveItemsToCartFromAlarmlist(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "code", encodeValue = false) String code,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  ) {
    return getApi().moveItemsToCartFromAlarmlist(emptyRequestBody, code, sessionID)
        .flatMap(entity -> {
          if (entity == null) {
            return Observable.error(new ResponseNullException(""));
          } else {
            return Observable.just(entity);
          }
        });
  }

  @Override public Observable<BasketItemResponseEntity> removeAlarmListItem_2(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "signedRequest", encodeValue = false) String sessionID,
      @Query(value = "code", encodeValue = false) String sku
  ) {
    return getApi().removeAlarmListItem_2(emptyRequestBody, sessionID, sku).flatMap(entity -> {
      if (entity == null) {
        return Observable.error(new ResponseNullException("removeAlarmListItem_2 error"));
      } else {
        return Observable.just(entity);
      }
    });
  }

  @Override public Observable<CustomerOrdersBaseEntity> getCustomerOrders(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  ) {
    return getApi().getCustomerOrders(emptyRequestBody, sessionID).flatMap(entity -> {
      if (entity == null) {
        return Observable.error(new ResponseNullException(""));
      } else {
        return Observable.just(entity);
      }
    });
  }

  @Override public Observable<OrderDetailResponseEntity> getCustomerOrderDetails(
      @Body EmptyRequestBody emptyRequestBody,
      @Query(value = "OrderId", encodeValue = false) String orderId,
      @Query(value = "signedRequest", encodeValue = false) String sessionID
  ) {
    return getApi().getCustomerOrderDetails(emptyRequestBody, orderId, sessionID)
        .flatMap(entity -> {
          if (entity == null) {
            return Observable.error(
                new ResponseNullException("getCustomerOrderDetails error orderId:" + orderId));
          } else {
            return Observable.just(entity);
          }
        });
  }

  @Override public Observable<SendPaymentInfoForOrderResponse> sendPaymentInfoForOrder(
      @Body SendPaymentInfoForOrderRequest sendPaymentInfoForOrderRequest
  ) {
    return getApi().sendPaymentInfoForOrder(sendPaymentInfoForOrderRequest).flatMap(entity -> {
      if (entity == null) {
        return Observable.error(new ResponseNullException("sendPaymentInfoForOrder error"));
      } else {
        return Observable.just(entity);
      }
    });
  }
}
