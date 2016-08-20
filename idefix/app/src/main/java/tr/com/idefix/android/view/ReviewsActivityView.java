package tr.com.idefix.android.view;

import java.util.List;
import tr.com.idefix.domain.Review;

/**
 * Created on 10.18.15.
 */
public interface ReviewsActivityView extends IView {

  void setTitle(String string);

  void bindReviews(List<Review> reviews);

  void setCartItemCount(int cartItemCount);
}
