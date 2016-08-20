package tr.com.idefix.android.view;

/**
 * Created on 10.18.15.
 */
public interface ReviewActivityView extends IView {

  void setTitle(String title);

  void setCartItemCount(int cartItemCount);

  void onReviewed();

  void showException(String message);
}
