package tr.com.idefix.android.presenter;

import android.content.Intent;
import com.squareup.otto.Bus;
import tr.com.idefix.android.model.MainCategoryModel;

/**
 * Created by utkan on 14/09/15.
 */
public interface ICategoriesActivityPresenter extends Presenter {
  void processIntent(Intent intent);

  int getParentCategoryId();

  boolean isLoggedIn();

  void addItemToWishList(String sku);

  void addItemToAlarmList(String day, String price, String sku);

  boolean isloggedIn();

  String getParentCategoryName();

  boolean isBottom();

  int getLevel();

  String getCategoryPath();

  MainCategoryModel getMainCategoryModel();

  boolean isALL();

  void removeItemFromAlarmList(String sku);

  Bus getBus();
}
