package tr.com.idefix.android.view.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.tagmanager.DataLayer;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import tr.com.idefix.android.R;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.contants.RequestCodes;
import tr.com.idefix.android.internal.di.HasComponent;
import tr.com.idefix.android.internal.di.components.CategoriesActivityComponent;
import tr.com.idefix.android.internal.di.components.DaggerCategoriesActivityComponent;
import tr.com.idefix.android.model.CategoryModel;
import tr.com.idefix.android.model.MainCategoryModel;
import tr.com.idefix.android.presenter.ICategoriesActivityPresenter;
import tr.com.idefix.android.view.CategoriesActivityView;
import tr.com.idefix.android.view.components.BasketViewComponent;
import tr.com.idefix.android.view.components.SearchViewComponent;
import tr.com.idefix.android.view.fragments.ParentCategoryFragment;
import tr.com.idefix.android.view.fragments.TopCategoryInfoFragment;
import tr.com.idefix.android.view.listeners.OnBasketClickListener;
import tr.com.idefix.android.view.listeners.OnCategorySelectedListener;
import tr.com.idefix.android.view.listeners.OnOpenLoginListener;
import tr.com.idefix.android.view.listeners.OnOpenProductDetailListener;
import tr.com.idefix.android.view.listeners.OnSearchClickListener;
import tr.com.idefix.android.view.listeners.OnSortAllSelectedListener;
import tr.com.idefix.domain.FilterItem;
import tr.com.idefix.domain.events.AlarmWishEvent;

public class CategoriesActivity extends BaseActivity
    implements HasComponent<CategoriesActivityComponent>, CategoriesActivityView,
    OnSearchClickListener, OnBasketClickListener, OnOpenLoginListener, OnOpenProductDetailListener,
    OnCategorySelectedListener, OnSortAllSelectedListener {

  //<editor-fold desc="Fields">

  @Inject ICategoriesActivityPresenter presenter;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.view_pager) ViewPager viewPager;
  @Bind(R.id.tab_layout) TabLayout tabLayout;
  @Bind(R.id.category_title) AppCompatTextView category_title;
  @Bind(R.id.search_icon) SearchViewComponent searchViewComponent;
  @Bind(R.id.basket_icon) BasketViewComponent basketViewComponent;
  @Bind(R.id.basket_count) AppCompatTextView basket_count;
  CategoryPagerAdapter pagerAdapter;
  @Bind(R.id.filter_action) FloatingActionButton filter_action;
  TopCategoryInfoFragment topCategoryInfoFragment;
  ParentCategoryFragment parentCategoryFragment;
  private CategoriesActivityComponent cateoriesActivityComponent;
  private String tabName;
  private boolean isFilterActionVisible;
  private Bus bus;

  //</editor-fold>

  @OnClick(R.id.barcode_icon) void onBarcodeClicked() {
    startActivity(BarcodeInfoActivity.createLauncher(this));
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == RESULT_OK) {
      if (requestCode == RequestCodes.FILTER &&
          data.getExtras() != null &&
          data.getExtras().containsKey(Keys.FILTER)) {

        List<FilterItem> filterItems =
            (List<FilterItem>) data.getExtras().getSerializable(Keys.FILTER);

        topCategoryInfoFragment.updateFilterItems(filterItems);
      }
    }
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_categories);
    ////////////////////////////////////////////////////////////////////////////////////////////
    ButterKnife.bind(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    cateoriesActivityComponent = DaggerCategoriesActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    cateoriesActivityComponent.inject(this);
    bus = presenter.getBus();
    ////////////////////////////////////////////////////////////////////////////////////////////
    // Set a toolbar which will replace the action bar.
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_menu);
    toolbar.setNavigationOnClickListener(v -> finish());
    toolbar.setTitle("");
    setTitle("");
    ////////////////////////////////////////////////////////////////////////////////////////////
    searchViewComponent.setOnSearchClickListener(this);
    basketViewComponent.setOnBasketClickListener(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    // Setup the Tabs
    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#f5744d"));
    tabLayout.setSelectedTabIndicatorHeight((int) (2 * getResources().getDisplayMetrics().density));
    ////////////////////////////////////////////////////////////////////////////////////////////
    presenter.setView(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    presenter.processIntent(getIntent());

    filter_action.setBackgroundTintList(
        getResources().getColorStateList(R.color.filter_action_colorstatelist));
  }

  @Override public void setToolbarTitle(String title) {
    category_title.setText(title);
  }

  @Override public void onResume() {
    super.onResume();
    bus.register(this);
    presenter.resume();
  }

  @Override protected void onPause() {
    presenter.pause();
    bus.unregister(this);
    super.onPause();
  }

  @Override protected void onDestroy() {
    presenter.destroy();
    super.onDestroy();
  }

  @Override public CategoriesActivityComponent getComponent() {
    return cateoriesActivityComponent;
  }

  @Override public void setBasketCount(int count) {
    basket_count.setText(String.valueOf(count));
    basket_count.setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
  }

  @Override public void initTab(String name) {

    tabName = name;
    ////////////////////////////////////////////////////////////////////////////////////////////

    pagerAdapter = new CategoryPagerAdapter(getSupportFragmentManager());
    viewPager.setAdapter(pagerAdapter);

    tabLayout.setTabsFromPagerAdapter(pagerAdapter);
    // This method ensures that tab selection events update the ViewPager and page changes update
    // the selected tab.
    tabLayout.setupWithViewPager(viewPager);
    //        viewPager.setCurrentItem(1, true);

    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

      int selectedPage;

      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      }

      @Override public void onPageSelected(int position) {

        selectedPage = position;

        if (selectedPage == 0 && isFilterActionVisible) {
          filter_action.show();
        } else if (selectedPage == 1 && isFilterActionVisible) {
          filter_action.hide();
        }
      }

      /**
       * Called when the scroll state changes. Useful for discovering when the user
       * begins dragging, when the pager is automatically settling to the current page,
       * or when it is fully stopped/idle.
       *
       * @param state The new scroll state.
       * @see ViewPager#SCROLL_STATE_IDLE
       * @see ViewPager#SCROLL_STATE_DRAGGING
       * @see ViewPager#SCROLL_STATE_SETTLING
       */
      @Override public void onPageScrollStateChanged(int state) {

        //                if (selectedPage == 1 && isFilterActionVisible) {
        //                    switch (state) {
        //                        case ViewPager.SCROLL_STATE_IDLE:
        //                        case ViewPager.SCROLL_STATE_DRAGGING:
        //                        case ViewPager.SCROLL_STATE_SETTLING:
        //                            filter_action.setVisibility(View.GONE);
        //                            break;
        //                    }
        //                }
      }
    });
  }

  @Override public void onSearchClick() {
    openSearch();
  }

  @Override public void onBasketClick() {
    if (!presenter.isLoggedIn()) {
      navigator.navigateToLoginActivity(this);
    }
  }

  @Override public void onOpenLogin() {
    navigator.navigateToLoginActivity(this);
  }

  @Override public void itemAddedToWishList() {
    ((TopCategoryInfoFragment) pagerAdapter.getItem(0)).notifyItemAddedToWishList();

    final Dialog dialog = new Dialog(this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.dialog_ok);
    dialog.setTitle("");

    View container = dialog.findViewById(R.id.container);
    container.getLayoutParams().width = (int) (deviceUtils.getDeviceWidth(this) * 0.90);
    dialog.findViewById(R.id.ok).setOnClickListener(v -> dialog.dismiss());

    TextView content = (TextView) dialog.findViewById(R.id.content);
    content.setText("Ürün favori listenize eklendi.");

    dialog.show();
  }

  @Override public void itemAddedToAlarmList() {
    ((TopCategoryInfoFragment) pagerAdapter.getItem(0)).notifyItemAddedToAlarmList();

    final Dialog dialog = new Dialog(this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.dialog_ok);
    dialog.setTitle("");

    View container = dialog.findViewById(R.id.container);
    container.getLayoutParams().width = (int) (deviceUtils.getDeviceWidth(this) * 0.90);
    dialog.findViewById(R.id.ok).setOnClickListener(v -> dialog.dismiss());

    TextView content = (TextView) dialog.findViewById(R.id.content);
    content.setText("Ürün alarm listenize eklendi");

    dialog.show();
  }

  @Override public void itemRemovedFromAlarmList() {

    final Dialog dialog = new Dialog(this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.dialog_ok);
    dialog.setTitle("");

    View container = dialog.findViewById(R.id.container);
    container.getLayoutParams().width = (int) (deviceUtils.getDeviceWidth(this) * 0.90);
    dialog.findViewById(R.id.ok).setOnClickListener(v -> dialog.dismiss());

    TextView content = (TextView) dialog.findViewById(R.id.content);
    content.setText("Ürün alarm listenizden çıkarıldı.");

    dialog.show();
  }

  @Override public void setColor(int id) {
    switch (id) {
      case 4645: //kitap
        deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.status_stores_start));
        toolbar.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_stores));
        tabLayout.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_stores));
        break;
      case 4647: //film
        deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.bg_red_toolbar_start));
        toolbar.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_red));
        tabLayout.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_red));
        break;
      case 4646: //muzik
        deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.status_stores_start));
        toolbar.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_stores));
        tabLayout.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_stores));
        break;
      case 4648: //elektronik
        deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.status_green_end));
        toolbar.setBackground(getResources().getDrawable(R.drawable.bg_main_header));
        tabLayout.setBackground(getResources().getDrawable(R.drawable.bg_main_header));
        break;
      case 4649: //oyun konsol
        deviceUtils.setStatusBarColor(
            this, getResources().getColor(R.color.status_brassy_green_start));
        toolbar.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_oyun_konsol));
        tabLayout.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_oyun_konsol));
        break;
      case 4652: //kirtasiye
        deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.status_grey));
        toolbar.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_kirtasiye));
        tabLayout.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_kirtasiye));
        break;
      case 4653: //kisisel urun
        deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.bg_red_toolbar_end));
        toolbar.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_kisisel_urun));
        tabLayout.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_kisisel_urun));
        break;
      case 4650: //oyuncak
        deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.status_brown));
        toolbar.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_oyuncak));
        tabLayout.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_oyuncak));
        break;
      case 6448: //Hobi & Outdoor
        deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.status_green_end));
        toolbar.setBackground(getResources().getDrawable(R.drawable.bg_main_header));
        tabLayout.setBackground(getResources().getDrawable(R.drawable.bg_main_header));
        break;
      default:
        deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.status_stores_start));
        toolbar.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_stores));
        tabLayout.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_stores));
        break;
    }
  }

  @Subscribe public void bell(AlarmWishEvent event) {

    if (!presenter.isLoggedIn()) {
      navigator.navigateToLoginActivity(this);
      return;
    }

    if (event.bell()) {

      if (event.isChecked()) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_bell);
        dialog.setTitle("");

        dialog.findViewById(R.id.cancel).setOnClickListener(v -> {
          dialog.dismiss();
          ((TopCategoryInfoFragment) pagerAdapter.getItem(0)).notifyDataSetChanged();
        });

        EditText indays = (EditText) dialog.findViewById(R.id.indays);
        EditText below_price = (EditText) dialog.findViewById(R.id.below_price);

        dialog.findViewById(R.id.ok).setOnClickListener(v -> {
          if (below_price.getText().toString().length() == 0) {
            below_price.setError("Fiyat girmek zorunludur");
            return;
          }
          presenter.addItemToAlarmList(indays.getText().toString(),
              below_price.getText().toString(), event.sku());
          dialog.dismiss();
        });

        dialog.show();
      } else {
        presenter.removeItemFromAlarmList(event.sku());
      }
    }

    if (event.favorite()) {
      presenter.addItemToWishList(event.sku());
    }
  }

  @Override public void onOpenProductDetail(int id, String sku) {
    navigator.navigateToProductDetailActivity(this, id, sku, tabName);

    getDataLayer().pushEvent("productClick", DataLayer.mapOf(
        "ecommerce", DataLayer.mapOf("click",
            DataLayer.mapOf("actionField", DataLayer.mapOf("list", "SubCategory"),
                // Ürünün Clicklendiği Sayfa Tipi (Search Results, Main Category,
                // SubCategory, Homepage)
                "products", DataLayer.listOf(
                    DataLayer.mapOf("name", "undefined", "id", id, "price", "undefined", "brand",
                        "undefined", "category", tabName))))));
  }

  @Override public void onCategorySelected(CategoryModel categoryModel) {
    if (!categoryModel.bottom()) {

    }
    navigator.navigateToCategoriesActivity(
        this, new MainCategoryModel().id(categoryModel.id())
            .name(categoryModel.name())
            .bottom(categoryModel.bottom())
            .level(categoryModel.level())
            .parentPath(categoryModel.parentPath())
            .parentName(presenter.getParentCategoryName())

    );
  }

  @OnClick({ R.id.basket_count, R.id.basket_icon }) void viewBasket() {
    if (presenter.isloggedIn()) {
      navigator.navigateToBasketActivity(this);
    }
  }

  @Override public void onSortAllSelected() {

    MainCategoryModel categoryModel = presenter.getMainCategoryModel();

    navigator.navigateToCategoriesActivity(
        this, new MainCategoryModel().id(categoryModel.id())
            .name(categoryModel.name())
            .bottom(categoryModel.bottom())
            .level(categoryModel.level())
            .parentPath(categoryModel.parentPath())
            .parentName(presenter.getParentCategoryName()), true

    );
  }

  @Override public void setFilterActionVisible() {

    isFilterActionVisible = true;
    filter_action.setVisibility(View.VISIBLE);

    filter_action.setOnClickListener(v -> {
      if (topCategoryInfoFragment.getFilter() != null) {
        navigator.navigateToFilterActivity(this,
            (HashMap<Integer, List<FilterItem>>) topCategoryInfoFragment.getFilter());
      }
    });
  }

  @Override public void notifyFragments() {
    if (topCategoryInfoFragment != null) {
      topCategoryInfoFragment.notifyDataSetChanged();
    }
  }

  private class CategoryPagerAdapter extends FragmentStatePagerAdapter {

    public CategoryPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override public Fragment getItem(int pos) {
      switch (pos) {

        case 0:

          if (topCategoryInfoFragment == null) {
            topCategoryInfoFragment =
                TopCategoryInfoFragment.newInstance(presenter.getParentCategoryId(),
                    presenter.getLevel(), presenter.getCategoryPath(), presenter.isALL());
            topCategoryInfoFragment.setOnSortAllSelectedListener(CategoriesActivity.this);
          }
          return topCategoryInfoFragment;
        case 1:

          if (parentCategoryFragment == null) {

            parentCategoryFragment =
                ParentCategoryFragment.newInstance(presenter.getParentCategoryId());
            parentCategoryFragment.setOnCategorySelectedListener(CategoriesActivity.this);
          }
          return parentCategoryFragment;
      }
      return TopCategoryInfoFragment.newInstance(presenter.getParentCategoryId(),
          presenter.getLevel(), presenter.getCategoryPath(), presenter.isALL());
    }

    @Override public int getCount() {
      if (presenter.isBottom()) {
        return 1;
      }
      return 2;
    }

    @Override public CharSequence getPageTitle(int position) {
      switch (position) {
        case 0:
          return tabName;
        case 1:
          return getResources().getString(R.string.tab_category_title);
      }
      return "";
    }
  }
}
