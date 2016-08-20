package tr.com.idefix.android.view.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.tagmanager.DataLayer;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import javax.inject.Inject;
import tr.com.idefix.android.R;
import tr.com.idefix.android.events.MainCategorySelectedEvent;
import tr.com.idefix.android.internal.di.HasComponent;
import tr.com.idefix.android.internal.di.components.DaggerMainActivityComponent;
import tr.com.idefix.android.internal.di.components.MainActivityComponent;
import tr.com.idefix.android.model.BannerModel;
import tr.com.idefix.android.model.MainCategoryModel;
import tr.com.idefix.android.presenter.IMainActivityPresenter;
import tr.com.idefix.android.view.MainActivityView;
import tr.com.idefix.android.view.components.BasketViewComponent;
import tr.com.idefix.android.view.fragments.BannerFragment;
import tr.com.idefix.android.view.fragments.MainMenuFragment;
import tr.com.idefix.android.view.listeners.OnBasketClickListener;
import tr.com.idefix.android.view.listeners.OnOpenContractsListener;
import tr.com.idefix.android.view.listeners.OnOpenInstagramListener;
import tr.com.idefix.android.view.listeners.OnOpenStoresListener;
import tr.com.idefix.android.view.listeners.OnSearchClickListener;

public class MainActivity extends BaseActivity
    implements MainActivityView, HasComponent<MainActivityComponent>, OnOpenStoresListener,
    OnOpenContractsListener, OnOpenInstagramListener, OnSearchClickListener, OnBasketClickListener {

  //<editor-fold desc="Fields">

  private static final int PERMISSION_ALL = 100;
  @Bind(R.id.basket_icon) BasketViewComponent basketViewComponent;
  MainActivityComponent mainActivityComponent;
  @Inject IMainActivityPresenter presenter;
  BannerFragment bannerFragment;
  MainMenuFragment mainMenuFragment;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.basket_count) AppCompatTextView basket_count;
  @Bind(R.id.login_text) TextView login_text;
  @Bind(R.id.ic_login) ImageView ic_login;

  @OnClick(R.id.barcode_icon) void onBarcodeClicked() {
    startActivity(BarcodeInfoActivity.createLauncher(this));
  }

  //</editor-fold>

  @Override protected void onCreate(Bundle savedInstanceState) {
    ////////////////////////////////////////////////////////////////////////////////////////////
    getDataLayer().push(DataLayer.mapOf("CD_PageType", "home"));
    ////////////////////////////////////////////////////////////////////////////////////////////
    super.onCreate(savedInstanceState);
    ////////////////////////////////////////////////////////////////////////////////////////////
    setContentView(R.layout.activity_main);
    ////////////////////////////////////////////////////////////////////////////////////////////
    ButterKnife.bind(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    mainActivityComponent = DaggerMainActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.status_green_end));

    ////////////////////////////////////////////////////////////////////////////////////////////
    getComponent().inject(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    presenter.setView(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    setSupportActionBar(toolbar);
    toolbar.setTitle("");
    setTitle("");
    ////////////////////////////////////////////////////////////////////////////////////////////
    //searchViewComponent.setOnSearchClickListener(this);
    basketViewComponent.setOnBasketClickListener(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    bannerFragment =
        (BannerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_banner);

    mainMenuFragment =
        (MainMenuFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_menu);
    ////////////////////////////////////////////////////////////////////////////////////////////
    presenter.processIntent(getIntent());

    checkPermissions();
  }

  protected void checkPermissions() {
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      askPermissions();
    }
  }

  private void askPermissions() {
    boolean askedPermissions = presenter.getDomainContext().isPermissionAsked();
    if (!askedPermissions) {
      presenter.getDomainContext().setPermissionAsked(true);
      ActivityCompat.requestPermissions(this, new String[] {
          Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
          Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
          Manifest.permission.READ_EXTERNAL_STORAGE,
      }, PERMISSION_ALL);
    }
  }

  @Override protected void onResume() {
    super.onResume();
    presenter.resume();
    bus.register(this);
  }

  @Override protected void onPause() {
    super.onPause();
    presenter.pause();
    bus.unregister(this);
  }

  @OnClick(R.id.login_text) void login() {
    if (!presenter.loggedin()) {
      navigator.navigateToLoginActivity(this);
    } else {
      navigator.navigateToUserProfileActivity(this);
    }
  }

  @Override protected void onDestroy() {
    ButterKnife.unbind(this);
    super.onDestroy();
  }

  @Override public MainActivityComponent getComponent() {
    return mainActivityComponent;
  }

  @Override public void setBanners(ArrayList<BannerModel> banners) {
    if (banners != null && banners.size() > 0) {
      bannerFragment.setBanners(banners);
    } else {
      //TODO: hide banners
    }
  }

  @Override public void setCategories(ArrayList<MainCategoryModel> categories) {
    if (categories != null && categories.size() > 0) {
      mainMenuFragment.setCategories(categories);
    } else {
      //TODO: hide banners
    }
  }

  @Override public void setBasketCount(int count) {
    basket_count.setText(String.valueOf(count));
    basket_count.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
  }

  @Override public void onOpenStore() {
    navigator.navigateToStoresActivity(this);
  }

  @Subscribe public void mainCategorySelected(MainCategorySelectedEvent event) {
    navigator.navigateToCategoriesActivity(this, event.mainCategoryModel());
  }

  @Override public void openContracts() {
    Intent browserIntent = new Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://e-sirket.mkk.com.tr/esir/Dashboard.jsp#/sirketbilgileri/10740"));
    startActivity(browserIntent);
  }

  @Override public void openInstagramPage() {

    Uri uri = Uri.parse("http://instagram.com/_u/idefix_com");
    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

    likeIng.setPackage("com.instagram.android");

    try {
      startActivity(likeIng);
    } catch (ActivityNotFoundException e) {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/idefix_com")));
    }
  }

  @Override public void updateView() {
    login_text.setText(presenter.getUserFullName());
    ic_login.setVisibility(View.GONE);
  }

  @Override public void onSearchClick() {
    openSearch();
  }

  @OnClick(R.id.ic_search) void search() {
    onSearchClick();
  }

  @Override public void onBasketClick() {
    if (presenter.loggedin()) {
      navigator.navigateToBasketActivity(this);
    } else {
      navigator.navigateToLoginActivity(this);
    }
  }

  @OnClick(R.id.basket_container) void basket() {
    onBasketClick();
  }
}
