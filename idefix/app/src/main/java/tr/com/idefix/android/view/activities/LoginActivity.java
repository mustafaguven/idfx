package tr.com.idefix.android.view.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tr.com.idefix.android.R;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.internal.di.HasComponent;
import tr.com.idefix.android.internal.di.components.DaggerLoginComponent;
import tr.com.idefix.android.internal.di.components.LoginComponent;
import tr.com.idefix.android.view.components.BasketViewComponent;
import tr.com.idefix.android.view.components.SearchViewComponent;
import tr.com.idefix.android.view.fragments.LoginFragment;
import tr.com.idefix.android.view.listeners.OnBasketClickListener;
import tr.com.idefix.android.view.listeners.OnForgetPasswordListener;
import tr.com.idefix.android.view.listeners.OnSearchClickListener;

public class LoginActivity extends BaseActivity
    implements HasComponent<LoginComponent>, OnForgetPasswordListener, OnSearchClickListener,
    OnBasketClickListener {

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.search_icon) SearchViewComponent searchViewComponent;
  @Bind(R.id.basket_icon) BasketViewComponent basketViewComponent;
  @Bind(R.id.barcode_icon) ImageView barcodeIcon;
  private LoginComponent loginComponent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ////////////////////////////////////////////////////////////////////////////////////////////
    ButterKnife.bind(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    toolbar.setTitle(R.string.menu);
    ////////////////////////////////////////////////////////////////////////////////////////////
    setSupportActionBar(toolbar);
    ////////////////////////////////////////////////////////////////////////////////////////////
    toolbar.setNavigationOnClickListener(v -> finish());
    toolbar.setNavigationIcon(R.drawable.ic_menu);
    ////////////////////////////////////////////////////////////////////////////////////////////
    searchViewComponent.setOnSearchClickListener(this);
    basketViewComponent.setOnBasketClickListener(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    initializeInjector();
  }

  @OnClick(R.id.barcode_icon) void onBarcodeClicked() {
    startActivity(BarcodeInfoActivity.createLauncher(this));
  }

  @Override protected void onStart() {
    super.onStart();
    if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Keys.USERNAME)) {
      LoginFragment loginFragment =
          (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
      loginFragment.login(getIntent().getStringExtra(Keys.USERNAME),
          getIntent().getStringExtra(Keys.PASSWORD), false);
      getIntent().getExtras().remove(Keys.USERNAME);
      getIntent().getExtras().remove(Keys.PASSWORD);
    }
  }

  private void initializeInjector() {
    this.loginComponent = DaggerLoginComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
  }

  @Override public LoginComponent getComponent() {
    return loginComponent;
  }

  @Override public void onOpenForgetPassword() {
    navigator.navigateToForgetPasswordActivity(this);
  }

  @Override public void onSearchClick() {
    openSearch();
  }

  @Override public void onBasketClick() {

  }
}
