package tr.com.idefix.android.presenter;

import android.content.Intent;
import com.google.android.gms.tagmanager.DataLayer;
import tr.com.idefix.domain.GroupedProductAttribute;

/**
 * Created by utkan on 14/10/15.
 */
public interface IProductDetailPresenter extends Presenter {

  void processIntent(Intent intent);

  void favorite(boolean checked);

  boolean isloggedIn();

  void warn(boolean checked, String days, String price);

  void warn(boolean checked);

  String getShareText();

  int getProductId();

  String getProductName();

  void addItemToBasket(int quantity);

  String getSku();

  String getTitle();

  boolean isInfavList(String sku);

  boolean isInAlarmList(String sku);

  void favorite(boolean isChecked, String sku);

  void addItemToAlarmList(String day, String price, String sku);

  void removeItemFromAlarmList(String sku);

  void getBasketCount();

  GroupedProductAttribute getGroupedProductAttribute(int i);

  void setSelectedGroupedProductAttribute(GroupedProductAttribute groupedProductAttribute);

  boolean isEBook();

  boolean selectEBook();

  int getEBookIndex();

  void setDataLayer(DataLayer dataLayer);
}
