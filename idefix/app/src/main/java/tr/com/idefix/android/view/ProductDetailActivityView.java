package tr.com.idefix.android.view;

import java.util.List;
import tr.com.idefix.domain.GroupedProductAttribute;
import tr.com.idefix.domain.ProductAttribute;
import tr.com.idefix.domain.ProductOther;
import tr.com.idefix.domain.ProductPerson;
import tr.com.idefix.domain.Review;

/**
 * Created by utkan on 14/10/15.
 */
public interface ProductDetailActivityView extends IView {
  void setImage(String imageUrl);

  void setName(String name);

  void bindProductAttributes(List<ProductAttribute> productAttributes);

  void bindOldPrice(String oldPrice);

  void bindPrice(String price);

  void bindProductPersons(List<ProductPerson> productPersons);

  void bindBrand(String brand);

  void bindShortDescription(String s);

  void bindFullDescription(String s);

  void setItemFavorite();

  void setEnabledFavoriteView(boolean b);

  void setEnabledWarnView(boolean b);

  void setItemWarned();

  void setTitle(String title);

  void setCartItemCount(int cartItemCount);

  void bindReviews(List<Review> reviews);

  void noReview();

  void setItemWarned(boolean b);

  void bindOtherProducts(List<ProductOther> productOthers);

  void enableBasket();

  void itemAddedToWishList();

  void itemRemovedFromWishList();

  void itemAddedToAlarmList();

  void itemRemovedFromAlarmList();

  void itemAddedToBasket();

  void bindGroupedProductAttributes(List<GroupedProductAttribute> groupedProductAttributes);

  void showError(String message);

  void setEnableSepeteEkle(boolean b);
}
