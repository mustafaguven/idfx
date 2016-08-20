package tr.com.idefix.android.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;
import timber.log.Timber;
import tr.com.idefix.android.mapper.CustomerDataMapper;
import tr.com.idefix.android.model.MainCategoryModel;
import tr.com.idefix.android.view.CategoriesActivityView;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.domain.Alarm;
import tr.com.idefix.domain.Wish;
import tr.com.idefix.domain.events.UserInOutEvent;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICustomerInterActor;

import static tr.com.idefix.android.contants.Keys.ALL;
import static tr.com.idefix.android.contants.Keys.MAIN_CATEGORY_MODEL;

/**
 * Created by utkan on 14/09/15.
 */
public class CategoriesActivityPresenter extends BaseCustomerPresenter
    implements ICategoriesActivityPresenter {

  //<editor-fold desc="Fields">
  CategoriesActivityView view;
  MainCategoryModel mainCategoryModel;
  private boolean isAll;
  //</editor-fold>

  public CategoriesActivityPresenter(
      CustomerDataMapper customerDataMapper, ICustomerInterActor customerInterActor
  ) {
    super(customerDataMapper, customerInterActor);
  }

  @Override public void resume() {
    bus.register(this);
  }

  @Override public void pause() {
    bus.unregister(this);
  }

  @Override public void destroy() {
    customerInterActor.unSubscribe();
  }

  @Override public void setView(IView iView) {
    iview = iView;
    view = (CategoriesActivityView) iView;
  }

  @Override public void processIntent(Intent intent) {
    if (intent != null) {
      Bundle bundle = intent.getExtras();

      isAll = bundle.getBoolean(ALL, false);

      if (bundle.containsKey(MAIN_CATEGORY_MODEL)
          && bundle.getSerializable(MAIN_CATEGORY_MODEL) != null) {
        mainCategoryModel = (MainCategoryModel) bundle.getSerializable(MAIN_CATEGORY_MODEL);

        if (mainCategoryModel.level() == 0) {
          domainContext.setSelectedTopCategory(mainCategoryModel.id(), mainCategoryModel.name());
        }
        if (mainCategoryModel.level() > 0) {
          view.setFilterActionVisible();
        }

        if (mainCategoryModel.level() == 0) {
          view.setColor(mainCategoryModel.id());
        } else if (mainCategoryModel.level() > 0) {
          String[] s = mainCategoryModel.parentPath().split(",");
          int parent = Integer.parseInt(s[1]);
          view.setColor(parent);
        }

        if (!TextUtils.isEmpty(mainCategoryModel.parentName())) {
          view.setToolbarTitle(mainCategoryModel.parentName());
        }
        view.initTab(mainCategoryModel.name());
      }
    }
  }

  @Override public int getParentCategoryId() {
    if (mainCategoryModel != null) {
      return mainCategoryModel.id();
    }
    return -1;
  }

  @Override public String getParentCategoryName() {
    if (mainCategoryModel != null) {
      return mainCategoryModel.name();
    }
    return null;
  }

  @Override public boolean isLoggedIn() {
    return domainContext.getLoggedInUser() != null;
  }

  @Override public void addItemToWishList(String sku) {
    customerInterActor.addItemToWishList(new DefaultSubscriber<Wish>() {
      @Override public void onStart() {
        super.onStart();
      }

      @Override public void onNext(Wish wish) {
        super.onNext(wish);

        Timber.i("wishes: %s", wish != null ? wish.items().size() : "-");
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.itemAddedToWishList();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
      }
    }, sku);
  }

  @Override public void addItemToAlarmList(String days, String price, String sku) {

    customerInterActor.addItemToAlarmList(new DefaultSubscriber<Alarm>() {
      @Override public void onStart() {
        super.onStart();
      }

      @Override public void onNext(Alarm alarm) {
        super.onNext(alarm);

        Timber.i("alarms: %s", alarm != null ? alarm.items().size() : "-");
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.itemAddedToAlarmList();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.itemAddedToAlarmList();
      }
    }, sku, days, price);
  }

  @Override public void removeItemFromAlarmList(String sku) {

    customerInterActor.removeItemFromAlarmList(new DefaultSubscriber<Boolean>() {

      @Override public void onStart() {
        super.onStart();
      }

      @Override public void onNext(Boolean aBoolean) {
        super.onNext(aBoolean);
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.itemRemovedFromAlarmList();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.itemRemovedFromAlarmList();
      }
    }, sku);
  }

  @Override public boolean isloggedIn() {
    return domainContext.getLoggedInUser() != null;
  }

  @Override public boolean isBottom() {
    return mainCategoryModel.bottom();
  }

  @Override public int getLevel() {
    return mainCategoryModel.level();
  }

  @Override public String getCategoryPath() {
    return mainCategoryModel.parentPath();
  }

  @Override public MainCategoryModel getMainCategoryModel() {
    return mainCategoryModel;
  }

  @Override public boolean isALL() {
    return isAll;
  }

  @Produce public UserInOutEvent produceUserLogedinEvent() {
    return new UserInOutEvent(domainContext.getLoggedInUser());
  }

  @Subscribe public void userLogedin(UserInOutEvent userInOutEvent) {

    if (userInOutEvent.getLoggedInUser() != null) {

      getBasketCount();

      getWishList();

      getAlarmList();
    }
    //        view.notifyFragments();
  }

  @Override void onNextAlarm() {
    view.notifyFragments();
  }

  @Override protected void onNextWish() {
    view.notifyFragments();
  }
}
