package tr.com.idefix.android.view;

import tr.com.idefix.data.entity.BasketItemResponseEntity;
import tr.com.idefix.domain.Alarm;
import tr.com.idefix.domain.Wish;

/**
 * Created by mustafaguven on 17.10.2015.
 */
public interface MyListView extends IView {

  void onFetchFavouriteList(Wish wishList);

  void onFetchAlarmList(Alarm alarmList);

  void onMovedItemsFromCardToBasket(BasketItemResponseEntity basketItemResponseEntity);

  void onRemovedFromWishList(BasketItemResponseEntity clickedItems);

  void onError(Throwable throwable);

  void onRemovedFromAlarmList(BasketItemResponseEntity responseEntity);

  void onMovedAlarmItemsFromCardToBasket(BasketItemResponseEntity responseEntity);
}
