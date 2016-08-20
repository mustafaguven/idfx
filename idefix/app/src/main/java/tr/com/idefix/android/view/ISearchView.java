package tr.com.idefix.android.view;

import tr.com.idefix.data.entity.SearchResponseEntity;

/**
 * Created by mustafaguven on 24.10.2015.
 */
public interface ISearchView extends IView {

  void getBasketCount();

  void onSearchResult(SearchResponseEntity searchResponseEntity);

  void itemAddedToWishList(String code);

  void itemAddedToAlarmList(String code);

  void onRemovedFromAlarmList(String code);

  void removeItemFromWishList(String sku);

  void onCatalogResult(SearchResponseEntity searchResponseEntity);
}
