package tr.com.idefix.android.view;

/**
 * Created by utkan on 08/09/15.
 */
public interface CategoriesActivityView extends IView {

  void initTab(String name);

  void itemAddedToWishList();

  void itemAddedToAlarmList();

  void setToolbarTitle(String title);

  void setFilterActionVisible();

  void notifyFragments();

  void itemRemovedFromAlarmList();

  void setColor(int id);
}
