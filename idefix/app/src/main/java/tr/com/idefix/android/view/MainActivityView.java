package tr.com.idefix.android.view;

import java.util.ArrayList;
import tr.com.idefix.android.model.BannerModel;
import tr.com.idefix.android.model.MainCategoryModel;

/**
 * Created by utkan on 08/09/15.
 */
public interface MainActivityView extends IView {
  void setBanners(ArrayList<BannerModel> banners);

  void setCategories(ArrayList<MainCategoryModel> categories);

  void updateView();
}
