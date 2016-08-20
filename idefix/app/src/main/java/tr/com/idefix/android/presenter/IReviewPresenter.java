package tr.com.idefix.android.presenter;

import android.content.Intent;

/**
 * Created on 10.18.15.
 */
public interface IReviewPresenter extends Presenter {

  void processIntent(Intent intent);

  boolean isloggedIn();

  void review(String title, String review);
}
