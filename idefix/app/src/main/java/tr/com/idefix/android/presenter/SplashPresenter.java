package tr.com.idefix.android.presenter;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.mapper.SplashDataMapper;
import tr.com.idefix.android.model.BannerModel;
import tr.com.idefix.android.model.MainCategoryModel;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.android.view.SplashView;
import tr.com.idefix.data.net.RestApiBaseService;
import tr.com.idefix.domain.Banner;
import tr.com.idefix.domain.MainCategory;
import tr.com.idefix.domain.SettingsItem;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ISplashInterActor;

import static tr.com.idefix.android.contants.Keys.BANNER_LIST;
import static tr.com.idefix.android.contants.Keys.MAIN_CATEGORY_LIST;

/**
 * Created by utkan on 01/09/15.
 */
@PerActivity public class SplashPresenter implements ISplashPresenter {

  private final SplashDataMapper splashDataMapper;
  private final ISplashInterActor splashInterActor;
  SplashView splashView;

  int dataCount = 0;
  private List<MainCategoryModel> mainCategoryModels;
  private List<BannerModel> bannerModels;

  @Inject public SplashPresenter(
      ISplashInterActor splashInterActor, SplashDataMapper splashDataMapper
  ) {
    this.splashInterActor = splashInterActor;
    this.splashDataMapper = splashDataMapper;
  }

  @Override public void resume() {

  }

  @Override public void pause() {
    splashInterActor.unSubscribe();
  }

  @Override public void destroy() {
    splashInterActor.unSubscribe();
  }

  @Override public void setView(IView iView) {
    this.splashView = (SplashView) iView;
  }

  @Override public void getData() {
    dataCount = 0;
    splashInterActor.getMainCategories(new DefaultSubscriber<List<MainCategory>>() {
      @Override public void onStart() {
        super.onStart();
        splashView.showProgress();
      }

      @Override public void onNext(List<MainCategory> mainCategories) {
        super.onNext(mainCategories);
        mainCategoryModels = splashDataMapper.transformMainCategory(mainCategories);
      }

      @Override public void onCompleted() {
        super.onCompleted();
        dataCount++;
        checkIfDataFinished();
        splashView.hideProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        dataCount++;
        checkIfDataFinished();
        splashView.hideProgress();
      }
    });

    splashInterActor.getBanners(new DefaultSubscriber<List<Banner>>() {
      @Override public void onStart() {
        super.onStart();
        splashView.showProgress();
      }

      @Override public void onNext(List<Banner> bannerList) {
        super.onNext(bannerList);
        bannerModels = splashDataMapper.transformBanner(bannerList);
      }

      @Override public void onCompleted() {
        super.onCompleted();
        dataCount++;
        checkIfDataFinished();
        splashView.hideProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        dataCount++;
        checkIfDataFinished();
        splashView.hideProgress();
      }
    });

    splashInterActor.getSettings(new DefaultSubscriber<SettingsItem>() {
      @Override public void onStart() {
        super.onStart();
        splashView.showProgress();
      }

      @Override public void onNext(SettingsItem settingsItem) {
        super.onNext(settingsItem);
        RestApiBaseService.API_BASE_IMAGE_URL = settingsItem.imageServer();
      }

      @Override public void onCompleted() {
        super.onCompleted();

        dataCount++;
        checkIfDataFinished();
        splashView.hideProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        dataCount++;
        checkIfDataFinished();
        splashView.hideProgress();
      }
    });
  }

  @Override public Bundle getBundle() {

    Bundle bundle = null;
    if (mainCategoryModels != null || bannerModels != null) {
      bundle = new Bundle();

      if (mainCategoryModels != null && mainCategoryModels.size() > 0) {
        bundle.putSerializable(MAIN_CATEGORY_LIST, (ArrayList) mainCategoryModels);
      }

      if (bannerModels != null && bannerModels.size() > 0) {
        bundle.putSerializable(BANNER_LIST, (ArrayList) bannerModels);
      }
    }
    return bundle;
  }

  void checkIfDataFinished() {
    if (dataCount == 3) {
      splashView.setNextVisible();
    }
  }
}
