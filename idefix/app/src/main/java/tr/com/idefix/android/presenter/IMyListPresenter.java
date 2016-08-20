package tr.com.idefix.android.presenter;

import java.util.List;
import tr.com.idefix.domain.DRItem;

/**
 * Created by mustafaguven on 17.10.2015.
 */
public interface IMyListPresenter extends Presenter {

  void getFavouriteList();

  void getAlarmList();

  void moveItemsToCartFromWishlist(List<DRItem> items);

  void removeFromWishList(List<DRItem> selectedFavouriteItems);

  void moveItemsToCartFromAlarmList(List<DRItem> items);

  void removeFromAlarmList(List<DRItem> selectedAlarmItems);
}
