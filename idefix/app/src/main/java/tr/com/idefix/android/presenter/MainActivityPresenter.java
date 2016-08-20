package tr.com.idefix.android.presenter;

import android.content.Intent;
import android.os.Bundle;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.mapper.CustomerDataMapper;
import tr.com.idefix.android.mapper.SplashDataMapper;
import tr.com.idefix.android.model.BannerModel;
import tr.com.idefix.android.model.CustomerInfoModel;
import tr.com.idefix.android.model.MainCategoryModel;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.android.view.MainActivityView;
import tr.com.idefix.domain.Banner;
import tr.com.idefix.domain.CustomerInfo;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.MainCategory;
import tr.com.idefix.domain.events.UserInOutEvent;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICustomerInterActor;
import tr.com.idefix.domain.interactor.ISplashInterActor;

import static tr.com.idefix.android.contants.Keys.BANNER_LIST;
import static tr.com.idefix.android.contants.Keys.MAIN_CATEGORY_LIST;

/**
 * Created by utkan on 08/09/15.
 */
public class MainActivityPresenter extends BaseCustomerPresenter implements IMainActivityPresenter {

  //<editor-fold desc="Fields">
  private final ISplashInterActor splashInterActor;
  private final SplashDataMapper splashDataMapper;
  private MainActivityView mainActivityView;
  private ArrayList<MainCategoryModel> categories;
  private ArrayList<BannerModel> banners;
  //</editor-fold>

  public MainActivityPresenter(
      CustomerDataMapper customerDataMapper, SplashDataMapper splashDataMapper,
      ICustomerInterActor customerInterActor, ISplashInterActor splashInterActor
  ) {

    super(customerDataMapper, customerInterActor);

    this.splashDataMapper = splashDataMapper;
    this.splashInterActor = splashInterActor;
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
    this.mainActivityView = (MainActivityView) iView;
  }

  @Override public void processIntent(Intent intent) {

    if (intent != null) {
      Bundle bundle = intent.getExtras();

      if (bundle != null) {
        if (bundle.containsKey(MAIN_CATEGORY_LIST)) {
          categories = (ArrayList<MainCategoryModel>)

              bundle.getSerializable(Keys.MAIN_CATEGORY_LIST);
        }
        if (bundle.containsKey(BANNER_LIST)) {
          banners = (ArrayList<BannerModel>)

              bundle.getSerializable(Keys.BANNER_LIST);
        }
      }
    }

    if (categories == null) {

      splashInterActor.getMainCategories(new DefaultSubscriber<List<MainCategory>>() {
        @Override public void onStart() {
          super.onStart();
          mainActivityView.showProgress();
        }

        @Override public void onNext(List<MainCategory> mainCategories) {
          super.onNext(mainCategories);
          categories =
              (ArrayList<MainCategoryModel>) splashDataMapper.transformMainCategory(mainCategories);
        }

        @Override public void onCompleted() {
          super.onCompleted();
          mainActivityView.setCategories(categories);
          mainActivityView.hideProgress();
        }

        @Override public void onError(Throwable e) {
          super.onError(e);
          mainActivityView.hideProgress();
        }
      });
    } else {
      mainActivityView.setCategories(categories);
    }
    if (banners == null) {

      splashInterActor.getBanners(new DefaultSubscriber<List<Banner>>() {
        @Override public void onStart() {
          super.onStart();
          mainActivityView.showProgress();
        }

        @Override public void onNext(List<Banner> bannerList) {
          super.onNext(bannerList);
          banners = (ArrayList<BannerModel>) splashDataMapper.transformBanner(bannerList);
        }

        @Override public void onCompleted() {
          super.onCompleted();
          mainActivityView.setBanners(banners);
          mainActivityView.hideProgress();
        }

        @Override public void onError(Throwable e) {
          super.onError(e);
          mainActivityView.hideProgress();
        }
      });
    } else {
      mainActivityView.setBanners(banners);
    }
  }

  @Override public boolean loggedin() {
    return domainContext.getLoggedInUser() != null;
  }

  @Override public String getUserFullName() {
    if (domainContext.getCustomerInfo() != null) {
      CustomerInfoModel customerInfoModel =
          customerDataMapper.transform(domainContext.getCustomerInfo());
      return customerInfoModel.fullName();
    }
    return "";
  }

  @Produce public UserInOutEvent produceUserLogedinEvent() {
    return new UserInOutEvent(domainContext.getLoggedInUser());
  }

  @Subscribe public void userLogedin(UserInOutEvent userInOutEvent) {

    if (userInOutEvent.getLoggedInUser() != null) {

      getBasketCount();

      customerInterActor.getInfo(new DefaultSubscriber<CustomerInfo>() {
        @Override public void onStart() {
          super.onStart();
          mainActivityView.showProgress();
        }

        @Override public void onNext(CustomerInfo customerInfo) {
          super.onNext(customerInfo);

          mainActivityView.updateView();
        }

        @Override public void onCompleted() {
          super.onCompleted();
          mainActivityView.hideProgress();
        }

        @Override public void onError(Throwable e) {
          super.onError(e);
          mainActivityView.hideProgress();
        }
      });

      getWishList();

      getAlarmList();
    }
  }

  @Override void onNextAlarm() {

  }

  @Override protected void onNextWish() {

  }

  @Override public DomainContext getDomainContext() {
    return domainContext;
  }
}
