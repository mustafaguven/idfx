package tr.com.idefix.android.presenter;

import tr.com.idefix.data.entity.SearchRequestEntity;

/**
 * Created by mustafaguven on 24.10.2015.
 */
public interface ISearchPresenter extends Presenter {

  void getBasketCount();

  void search(SearchRequestEntity searchRequestEntity);

  boolean isLoggedIn();

  void favorite(boolean checked, String sku);

  void alarm(boolean checked, String sku);

  void addItemToAlarmList(String day, String price, String sku);

  void removeAlarmListItem(String code);

  void catalog(SearchRequestEntity requestEntity);
}
