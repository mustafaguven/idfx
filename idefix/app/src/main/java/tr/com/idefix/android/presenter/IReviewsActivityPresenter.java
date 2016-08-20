package tr.com.idefix.android.presenter;

import android.content.Intent;

/**
 * Created on 10.18.15.
 */
public interface IReviewsActivityPresenter extends Presenter {
  void processIntent(Intent intent);

  boolean isloggedIn();
}
