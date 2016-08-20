package tr.com.idefix.android.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.android.view.ReviewActivityView;
import tr.com.idefix.domain.BasketCount;
import tr.com.idefix.domain.ReviewAddResult;
import tr.com.idefix.domain.interactor.CatalogInterActor;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICustomerInterActor;

/**
 * Created on 10.18.15.
 */
public class ReviewPresenter extends BasePresenter implements IReviewPresenter {

  private final CatalogInterActor catalogInterActor;
  private final ICustomerInterActor customerInterActor;
  private ReviewActivityView view;
  private int product_id;

  public ReviewPresenter(
      ICustomerInterActor customerInterActor, CatalogInterActor catalogInterActor
  ) {

    super();

    this.customerInterActor = customerInterActor;
    this.catalogInterActor = catalogInterActor;
  }

  @Override public void resume() {

    if (domainContext.getLoggedInUser() != null) {
      customerInterActor.getBasketCount(new DefaultSubscriber<BasketCount>() {

        @Override public void onStart() {
          super.onStart();
          view.showProgress();
        }

        @Override public void onNext(BasketCount basketCount) {
          super.onNext(basketCount);

          if (basketCount != null) {
            view.setCartItemCount(basketCount.cartItemCount());
          }
        }

        @Override public void onCompleted() {
          super.onCompleted();
          view.hideProgress();
        }

        @Override public void onError(Throwable e) {
          super.onError(e);
          view.hideProgress();
        }
      });
    }
  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  @Override public void setView(IView iView) {
    view = (ReviewActivityView) iView;
  }

  @Override public void processIntent(Intent intent) {
    if (intent != null) {
      Bundle extras = intent.getExtras();

      if (extras != null && extras.containsKey(Keys.PRODUCT_NAME)) {
        view.setTitle(extras.getString(Keys.PRODUCT_NAME));
      }

      if (extras != null && extras.containsKey(Keys.PRODUCT_ID)) {
        product_id = extras.getInt(Keys.PRODUCT_ID);
      }
    }
  }

  @Override public boolean isloggedIn() {
    return domainContext.getLoggedInUser() != null;
  }

  @Override public void review(String title, String review) {

    catalogInterActor.productReviewsAdd(new DefaultSubscriber<ReviewAddResult>() {

      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onNext(ReviewAddResult reviewAddResult) {

        super.onNext(reviewAddResult);

        view.hideProgress();

        if (reviewAddResult != null
            && reviewAddResult.success() != null
            && reviewAddResult.success()) {
          view.onReviewed();
        } else if (reviewAddResult != null
            && reviewAddResult.success() != null
            && !reviewAddResult.success()) {
          if (!TextUtils.isEmpty(reviewAddResult.message())) {
            view.showException(reviewAddResult.message());
          }
        } else {
          view.showException("Hata oluştu");
        }
      }

      @Override public void onCompleted() {
        super.onCompleted();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);

        view.hideProgress();

        if (e != null && !TextUtils.isEmpty(e.getMessage())) {
          view.showException(e.getMessage());
        } else {
          view.showException("Hata oluştu");
        }
      }
    }, product_id, title, review);
  }
}
