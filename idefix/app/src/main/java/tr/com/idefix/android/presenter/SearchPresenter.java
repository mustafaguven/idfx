package tr.com.idefix.android.presenter;

import timber.log.Timber;
import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.view.ISearchView;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.data.entity.SearchRequestEntity;
import tr.com.idefix.data.entity.SearchResponseEntity;
import tr.com.idefix.domain.Alarm;
import tr.com.idefix.domain.BasketCount;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.Wish;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICustomerInterActor;
import tr.com.idefix.domain.interactor.ISearchInteractor;

/**
 * Created by mustafaguven on 24.10.2015.
 */
public class SearchPresenter implements ISearchPresenter {

  DomainContext domainContext;
  ISearchView view;
  ISearchInteractor searchInteractor;
  ICustomerInterActor customerInterActor;

  public SearchPresenter(
      ISearchInteractor iSearchInteractor, ICustomerInterActor iCustomerInterActor
  ) {
    this.searchInteractor = iSearchInteractor;
    this.customerInterActor = iCustomerInterActor;
    domainContext =
        ApplicationController.getInstance().getDomainApplicationComponent().domainContext();
  }

  public void getBasketCount() {
    customerInterActor.getBasketCount(new DefaultSubscriber<BasketCount>() {

      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onNext(BasketCount basketCount) {
        super.onNext(basketCount);

        if (basketCount != null) {
          view.setBasketCount(basketCount.cartItemCount());
        }
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
      }

      @Override public void onError(Throwable e) {
        Timber.e(e, "Sepet alınamadı");
        super.onError(e);
        view.hideProgress();
      }
    });
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  @Override public void setView(IView iView) {
    this.view = (ISearchView) iView;
  }

  @Override public void search(SearchRequestEntity searchRequestEntity) {
    searchInteractor.search(new DefaultSubscriber<SearchResponseEntity>() {
      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.hideProgress();
      }

      @Override public void onNext(SearchResponseEntity searchResponseEntity) {
        super.onNext(searchResponseEntity);
        view.onSearchResult(searchResponseEntity);
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
      }
    }, searchRequestEntity);
  }

  @Override public boolean isLoggedIn() {
    return domainContext.getLoggedInUser() != null;
  }

  @Override public void favorite(boolean checked, String sku) {
    if (checked) {
      customerInterActor.addItemToWishList(new DefaultSubscriber<Wish>() {
        @Override public void onStart() {
          super.onStart();
          view.showProgress();
        }

        @Override public void onNext(Wish wish) {
          super.onNext(wish);

          Timber.i("wishes: %s", wish != null ? wish.items().size() : "-");
        }

        @Override public void onCompleted() {
          super.onCompleted();
          view.itemAddedToWishList(sku);
          view.hideProgress();
        }

        @Override public void onError(Throwable e) {
          super.onError(e);
          view.hideProgress();
        }
      }, sku);
    } else {
      customerInterActor.removeItemFromWishList(new DefaultSubscriber<Boolean>() {

        @Override public void onStart() {
          super.onStart();
          view.showProgress();
        }

        @Override public void onNext(Boolean b) {
          super.onNext(b);
        }

        @Override public void onCompleted() {
          super.onCompleted();
          view.removeItemFromWishList(sku);
          view.hideProgress();
        }

        @Override public void onError(Throwable e) {
          super.onError(e);
          view.hideProgress();
        }
      }, sku);
    }
  }

  @Override public void alarm(boolean checked, String sku) {

  }

  @Override public void addItemToAlarmList(String day, String price, String sku) {
    customerInterActor.addItemToAlarmList(new DefaultSubscriber<Alarm>() {
      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onNext(Alarm alarm) {
        super.onNext(alarm);

        Timber.i("alarms: %s", alarm != null ? alarm.items().size() : "-");
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.itemAddedToAlarmList(sku);
        view.hideProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.hideProgress();
      }
    }, sku, day, price);
  }

  @Override public void removeAlarmListItem(String code) {
    customerInterActor.removeItemFromAlarmList(new DefaultSubscriber<Boolean>() {

      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.hideProgress();
      }

      @Override public void onNext(Boolean responseEntity) {
        super.onNext(responseEntity);
        view.onRemovedFromAlarmList(code);
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
      }
    }, code);
  }

  @Override public void catalog(SearchRequestEntity searchRequestEntity) {
    searchInteractor.catalog(new DefaultSubscriber<SearchResponseEntity>() {
      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.hideProgress();
      }

      @Override public void onNext(SearchResponseEntity searchResponseEntity) {
        super.onNext(searchResponseEntity);
        view.onCatalogResult(searchResponseEntity);
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
      }
    }, searchRequestEntity);
  }
}
