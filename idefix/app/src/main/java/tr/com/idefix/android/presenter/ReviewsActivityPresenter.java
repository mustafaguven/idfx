package tr.com.idefix.android.presenter;

import android.content.Intent;
import android.os.Bundle;
import java.util.List;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.android.view.ReviewsActivityView;
import tr.com.idefix.domain.BasketCount;
import tr.com.idefix.domain.Review;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICatalogInterActor;
import tr.com.idefix.domain.interactor.ICustomerInterActor;

/**
 * Created on 10.18.15.
 */
public class ReviewsActivityPresenter extends BasePresenter implements IReviewsActivityPresenter {

  private final ICatalogInterActor catalogInterActor;
  private final ICustomerInterActor customerInterActor;
  ReviewsActivityView view;

  public ReviewsActivityPresenter(
      ICatalogInterActor catalogInterActor, ICustomerInterActor customerInterActor
  ) {

    super();

    this.catalogInterActor = catalogInterActor;
    this.customerInterActor = customerInterActor;
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
        }
      });
    }
  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  @Override public void setView(IView iView) {
    view = (ReviewsActivityView) iView;
  }

  @Override public void processIntent(Intent intent) {

    if (intent != null) {
      Bundle extras = intent.getExtras();

      if (extras != null && extras.containsKey(Keys.TITLE)) {
        view.setTitle(extras.getString(Keys.TITLE));
      }

      if (extras != null && extras.containsKey(Keys.PRODUCT_ID)) {
        int productID = extras.getInt(Keys.PRODUCT_ID);

        catalogInterActor.getProductReviews(new DefaultSubscriber<List<Review>>() {

          List<Review> r;

          @Override public void onStart() {
            super.onStart();
          }

          @Override public void onNext(List<Review> reviews) {
            super.onNext(reviews);

            r = reviews;

            if (reviews != null && reviews.size() > 0) {
              view.bindReviews(reviews);
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();

            if (r == null || r.size() == 0) {
              //                                    view.noReview();
            }
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
          }
        }, productID, 10);
      }
    }
  }

  @Override public boolean isloggedIn() {
    return domainContext.getLoggedInUser() != null;
  }
}
