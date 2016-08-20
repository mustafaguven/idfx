package tr.com.idefix.android.view.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import javax.inject.Inject;
import tr.com.idefix.android.R;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.internal.di.HasComponent;
import tr.com.idefix.android.internal.di.components.DaggerUserProfileActivityComponent;
import tr.com.idefix.android.internal.di.components.UserProfileActivityComponent;
import tr.com.idefix.android.presenter.UserProfilePresenter;
import tr.com.idefix.android.view.UserProfileActivityView;
import tr.com.idefix.android.view.components.BasketViewComponent;
import tr.com.idefix.android.view.components.SearchViewComponent;
import tr.com.idefix.android.view.fragments.MyInformationFragment;
import tr.com.idefix.android.view.fragments.MyListsFragment;
import tr.com.idefix.android.view.fragments.MyOrdersFragment;
import tr.com.idefix.android.view.listeners.OnBasketClickListener;
import tr.com.idefix.android.view.listeners.OnLogoutListener;
import tr.com.idefix.android.view.listeners.OnSearchClickListener;

public class UserProfileActivity extends BaseActivity
    implements UserProfileActivityView, HasComponent<UserProfileActivityComponent>,
    OnLogoutListener, OnBasketClickListener, OnSearchClickListener {

  UserProfileActivityComponent userProfileActivityComponent;
  UserProfilePagerAdapter pagerAdapter;

  @Inject UserProfilePresenter presenter;

  @Bind(R.id.view_pager) ViewPager viewPager;
  @Bind(R.id.tab_layout) TabLayout tabLayout;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.basket_count) AppCompatTextView basket_count;
  @Bind(R.id.basket_icon) BasketViewComponent basketViewComponent;
  @Bind(R.id.search_icon) SearchViewComponent searchViewComponent;

  @OnClick(R.id.barcode_icon) void onBarcodeClicked() {
    startActivity(BarcodeInfoActivity.createLauncher(this));
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_profile);
    ButterKnife.bind(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    userProfileActivityComponent = DaggerUserProfileActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();

    getComponent().inject(this);
    // Set a toolbar which will replace the action bar.
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_menu);
    toolbar.setNavigationOnClickListener(v -> finish());
    toolbar.setTitle("");
    setTitle("");
    basketViewComponent.setOnBasketClickListener(this);
    searchViewComponent.setOnSearchClickListener(this);
    presenter.setView(this);

    initTab();
    if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras()
        .containsKey(Keys.OPEN_FOR_MY_ORDERS)) {
      viewPager.setCurrentItem(1);
    }
  }

  @Override protected void onResume() {
    super.onResume();
    getBasketCount();
  }

  public void initTab() {

    pagerAdapter = new UserProfilePagerAdapter(getSupportFragmentManager());
    viewPager.setAdapter(pagerAdapter);

    tabLayout.setTabsFromPagerAdapter(pagerAdapter);
    // This method ensures that tab selection events update the ViewPager and page changes update
    // the selected tab.
    tabLayout.setupWithViewPager(viewPager);
    //        viewPager.setCurrentItem(1, true);

  }

  @Override public UserProfileActivityComponent getComponent() {
    return userProfileActivityComponent;
  }

  @Override public void setBasketCount(int count) {
    basket_count.setText(String.valueOf(count));
    basket_count.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
  }

  @Override public void getBasketCount() {
    presenter.getBasketCount();
  }

  @Override public void onLogout() {
    navigator.navigateToMainActivityWithNewTask(this);
    finish();
  }

  @Override public void onBasketClick() {
    navigator.navigateToBasketActivity(this);
  }

  @Override public void onSearchClick() {
    openSearch();
  }

  private class UserProfilePagerAdapter extends FragmentStatePagerAdapter {

    public UserProfilePagerAdapter(FragmentManager fm) {
      super(fm);
    }

    //TopCategoryInfoFragment topCategoryInfoFragment;

    @Override public Fragment getItem(int pos) {
      switch (pos) {

        case 0:
          return new MyInformationFragment();
        case 1:
          return new MyOrdersFragment();
        case 2:
          return new MyListsFragment();
      }
      return new MyInformationFragment();
      //return TopCategoryInfoFragment.newInstance(presenter.getParentCategoryId());
    }

    @Override public int getCount() {
      return 3;
    }

    @Override public CharSequence getPageTitle(int position) {
      switch (position) {
        case 0:
          return getString(R.string.my_informations);
        case 1:
          return getString(R.string.my_orders);
        case 2:
          return getString(R.string.my_lists);
      }
      return "";
    }
  }
}
