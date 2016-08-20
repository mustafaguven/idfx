package tr.com.idefix.android.presenter;

import java.util.List;
import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.mapper.UserProfileDataMapper;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.android.view.MyListView;
import tr.com.idefix.data.entity.BasketItemResponseEntity;
import tr.com.idefix.domain.Alarm;
import tr.com.idefix.domain.DRItem;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.Wish;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICatalogInterActor;
import tr.com.idefix.domain.interactor.ICommonInterActor;
import tr.com.idefix.domain.interactor.ICustomerInterActor;
import tr.com.idefix.domain.interactor.ISignInInterActor;

/**
 * Created by mustafaguven on 17.10.2015.
 */
public class MyListPresenter implements IMyListPresenter {

  DomainContext domainContext;
  MyListView view;

  UserProfileDataMapper userProfileDataMapper;
  ISignInInterActor signInInterActor;
  ICommonInterActor commonInterActor;
  ICustomerInterActor customerInterActor;
  ICatalogInterActor catalogInterActor;

  public MyListPresenter(
      UserProfileDataMapper userProfileDataMapper, ISignInInterActor signInInterActor,
      ICommonInterActor commonInterActor, ICustomerInterActor customerInterActor,
      ICatalogInterActor catalogInterActor
  ) {
    this.userProfileDataMapper = userProfileDataMapper;
    this.signInInterActor = signInInterActor;
    this.commonInterActor = commonInterActor;
    this.customerInterActor = customerInterActor;
    this.catalogInterActor = catalogInterActor;

    domainContext =
        ApplicationController.getInstance().getDomainApplicationComponent().domainContext();
  }

  @Override public void getFavouriteList() {
    customerInterActor.getWishList(new DefaultSubscriber<Wish>() {
      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.hideProgress();
      }

      @Override public void onNext(Wish wishList) {
        super.onNext(wishList);
        view.onFetchFavouriteList(wishList);
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
      }
    });
  }

  @Override public void getAlarmList() {
    customerInterActor.getAlarmList(new DefaultSubscriber<Alarm>() {
      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.hideProgress();
      }

      @Override public void onNext(Alarm alarmList) {
        super.onNext(alarmList);
        view.onFetchAlarmList(alarmList);
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
      }
    });
  }

  @Override public void moveItemsToCartFromWishlist(List<DRItem> items) {
    domainContext.setMovableWishItemCount(items.size());
    for (int i = 0; i < items.size(); i++) {
      customerInterActor.moveItemsToCartFromWishlist(
          new DefaultSubscriber<BasketItemResponseEntity>() {

            @Override public void onStart() {
              super.onStart();
              view.showProgress();
            }

            @Override public void onError(Throwable e) {
              super.onError(e);
              view.onError(e);
              view.hideProgress();
            }

            @Override public void onNext(BasketItemResponseEntity responseEntity) {
              super.onNext(responseEntity);
              domainContext.setMovableWishItemCount(domainContext.getMovableWishItemCount() - 1);
              if (domainContext.getMovableWishItemCount() == 0) {
                view.onMovedItemsFromCardToBasket(responseEntity);
                view.hideProgress();
              }
            }

            @Override public void onCompleted() {
              super.onCompleted();
            }
          }, items.get(i).sku());
    }
  }

  @Override public void removeFromWishList(List<DRItem> items) {
    domainContext.setRemovableWishItemCount(items.size());
    for (int i = 0; i < items.size(); i++) {
      customerInterActor.removeInterestedListItem(
          new DefaultSubscriber<BasketItemResponseEntity>() {

            @Override public void onStart() {
              super.onStart();
              view.showProgress();
            }

            @Override public void onError(Throwable e) {
              super.onError(e);
              view.onError(e);
              view.hideProgress();
            }

            @Override public void onNext(BasketItemResponseEntity responseEntity) {
              super.onNext(responseEntity);
              domainContext.setRemovableWishItemCount(
                  domainContext.getRemovableWishItemCount() - 1);
              //todo: ask to utkan
              if (domainContext.getRemovableWishItemCount() == 0) {
                view.onRemovedFromWishList(responseEntity);
                view.hideProgress();
              }
            }

            @Override public void onCompleted() {
              super.onCompleted();
            }
          }, items.get(i).sku());
    }
  }

  @Override public void moveItemsToCartFromAlarmList(List<DRItem> items) {
    domainContext.setMovableAlarmItemCount(items.size());
    for (int i = 0; i < items.size(); i++) {
      final int finalI = i;
      customerInterActor.moveItemsToCartFromAlarmlist(
          new DefaultSubscriber<BasketItemResponseEntity>() {

            @Override public void onStart() {
              super.onStart();
              view.showProgress();
            }

            @Override public void onError(Throwable e) {
              super.onError(e);
              view.onError(e);
              view.hideProgress();
            }

            @Override public void onNext(BasketItemResponseEntity responseEntity) {
              super.onNext(responseEntity);
              domainContext.setMovableAlarmItemCount(domainContext.getMovableAlarmItemCount() - 1);
              //todo: ask to utkan
              if (domainContext.getMovableAlarmItemCount() == 0) {
                view.onMovedAlarmItemsFromCardToBasket(responseEntity);
                view.hideProgress();
              }
            }

            @Override public void onCompleted() {
              super.onCompleted();
            }
          }, items.get(i).sku());
    }
  }

  @Override public void removeFromAlarmList(List<DRItem> selectedAlarmItems) {
    domainContext.setRemovableAlarmItemCount(selectedAlarmItems.size());
    for (int i = 0; i < selectedAlarmItems.size(); i++) {
      customerInterActor.removeAlarmListItem(new DefaultSubscriber<BasketItemResponseEntity>() {

        @Override public void onStart() {
          super.onStart();
          view.showProgress();
        }

        @Override public void onError(Throwable e) {
          super.onError(e);
          view.onError(e);
          view.hideProgress();
        }

        @Override public void onNext(BasketItemResponseEntity responseEntity) {
          super.onNext(responseEntity);
          domainContext.setRemovableAlarmItemCount(domainContext.getRemovableAlarmItemCount() - 1);
          //todo: ask to utkan
          if (domainContext.getRemovableAlarmItemCount() == 0) {
            view.onRemovedFromAlarmList(responseEntity);
            view.hideProgress();
          }
        }

        @Override public void onCompleted() {
          super.onCompleted();
        }
      }, selectedAlarmItems.get(i).sku());
    }
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  @Override public void setView(IView iView) {
    this.view = (MyListView) iView;
  }
}
