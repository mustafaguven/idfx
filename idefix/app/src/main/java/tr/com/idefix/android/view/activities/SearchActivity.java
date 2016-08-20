package tr.com.idefix.android.view.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.tagmanager.DataLayer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import javax.inject.Inject;
import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.R;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.internal.di.HasComponent;
import tr.com.idefix.android.internal.di.components.DaggerSearchActivityComponent;
import tr.com.idefix.android.internal.di.components.SearchActivityComponent;
import tr.com.idefix.android.presenter.ISearchPresenter;
import tr.com.idefix.android.view.ISearchView;
import tr.com.idefix.android.view.adapters.SearchAdapter;
import tr.com.idefix.android.view.adapters.SearchOrderAdapter;
import tr.com.idefix.android.view.components.BasketViewComponent;
import tr.com.idefix.android.view.components.SearchViewComponent;
import tr.com.idefix.android.view.fragments.SearchFilterDialog;
import tr.com.idefix.android.view.listeners.OnBasketClickListener;
import tr.com.idefix.android.view.listeners.OnSearchClickListener;
import tr.com.idefix.data.entity.ContentEntity;
import tr.com.idefix.data.entity.ProductOtherEntity;
import tr.com.idefix.data.entity.SearchRequestEntity;
import tr.com.idefix.data.entity.SearchResponseEntity;
import tr.com.idefix.data.entity.SearchSubResponseEntity;
import tr.com.idefix.domain.SearchFilterItem;
import tr.com.idefix.domain.SearchOrderItem;

public class SearchActivity extends BaseActivity
    implements HasComponent<SearchActivityComponent>, ISearchView, OnBasketClickListener,
    OnSearchClickListener {

  //<editor-fold desc="Fields">
  @Inject ISearchPresenter presenter;
  int pastVisiblesItems, visibleItemCount, totalItemCount;
  List<ProductOtherEntity> searchResult = null;
  LinkedHashMap<String, SearchFilterItem> mFilterList = new LinkedHashMap<>();
  SearchSubResponseEntity searchSubResponseEntity = null;

  @Bind(R.id.lnFilters) LinearLayout lnFilters;
  @Bind(R.id.rvSearch) RecyclerView rvSearch;
  @Bind(R.id.txtSearch) AppCompatEditText txtSearch;
  @Bind(R.id.coordinatorLayout) RelativeLayout coordinatorLayout;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.lblCount) AppCompatTextView lblCount;
  @Bind(R.id.ddlOrder) AppCompatSpinner ddlOrder;
  @Bind(R.id.btnCollapse) AppCompatTextView btnCollapse;
  @Bind(R.id.category_title) AppCompatTextView category_title;
  @Bind(R.id.basket_icon) BasketViewComponent basketViewComponent;
  @Bind(R.id.search_icon) SearchViewComponent searchViewComponent;
  @Bind(R.id.basket_count) AppCompatTextView basket_count;
  @Bind(R.id.rlSearchComponent) RelativeLayout rlSearchComponent;
  @Bind(R.id.rlResultInfo) RelativeLayout rlResultInfo;
  @Bind(R.id.rlBarcode) RelativeLayout rlBarcode;
  private SearchActivityComponent searchActivityComponent;
  private boolean loading = true;
  private int start = 0, size = 6;
  private int page = 1;
  private OpeningType openingType;
  private String catalogId = "";
  private String catalogTopic = "";
  //</editor-fold>

  @Override public void onBasketClick() {
    navigator.navigateToBasketActivity(this);
  }

  @Override public void onSearchClick() {
    openSearch();
  }

  //@OnClick(R.id.btnSearch)
  void btnSearchClicked() {
    page = 1;
    searchResult = null;
    search();
  }

  void getCatalogResult(String catalogId) {
    SearchOrderItem searchOrderItem = (SearchOrderItem) ddlOrder.getSelectedItem();
    SearchRequestEntity requestEntity = new SearchRequestEntity();
    ContentEntity contentEntity = new ContentEntity();
    contentEntity.page(page);
    contentEntity.sortfield(searchOrderItem.getSortField());
    contentEntity.sortorder(searchOrderItem.getSortOrder());
    contentEntity.size(size);
    contentEntity.catalogId(catalogId);
    requestEntity.reponseType(0);
    double minPrice = -1;
    double maxPrice = -1;

    contentEntity.minPrice(minPrice);
    contentEntity.maxPrice(maxPrice);
    requestEntity.content(contentEntity);
    presenter.catalog(requestEntity);
  }

  @OnClick(R.id.imgClear) void clearText() {
    txtSearch.setText("");
    lnFilters.removeAllViews();
    rvSearch.setAdapter(null);
    mFilterList = new LinkedHashMap<>();
    btnCollapse.setVisibility(View.GONE);
    rlResultInfo.setVisibility(View.GONE);
    rlBarcode.setVisibility(View.VISIBLE);
  }

  void search() {
    if (txtSearch.getText().toString().trim().length() >= 2) {
      SearchOrderItem searchOrderItem = (SearchOrderItem) ddlOrder.getSelectedItem();
      SearchRequestEntity requestEntity = new SearchRequestEntity();
      ContentEntity contentEntity = new ContentEntity();
      contentEntity.page(page);
      contentEntity.sortfield(searchOrderItem.getSortField());
      contentEntity.sortorder(searchOrderItem.getSortOrder());
      contentEntity.size(size);
      contentEntity.term(txtSearch.getText().toString());
      requestEntity.reponseType(0);
      double minPrice = -1;
      double maxPrice = -1;

      if (mFilterList.size() > 0) {
        requestEntity.reponseType(2);
        String categories = "";
        String brands = "";
        String mediatypes = "";
        String parentIds = "";

        for (LinkedHashMap.Entry<String, SearchFilterItem> entry : mFilterList.entrySet()) {
          if (entry.getValue().getParent().contentEquals(getString(R.string.category))) {
            categories = entry.getValue().getId().split("-")[0] + ",";
            parentIds = entry.getValue().getId().split("-")[1] + ",";
          }

          if (entry.getValue().getParent().contentEquals(getString(R.string.brands))) {
            brands += entry.getValue().getId() + ",";
          }
          if (entry.getValue().getParent().contentEquals(getString(R.string.media_types))) {
            mediatypes += entry.getValue().getName() + ",";
          }
          if (entry.getValue().getParent().contentEquals(getString(R.string.prices))) {
            String values = entry.getValue().getName();
            values = values.replace("TL", "").replace(",", ".");
            String[] minmax = values.split("-");
            if (TextUtils.isEmpty(minmax[0].trim())) minmax[0] = "0";
            if (TextUtils.isEmpty(minmax[1].trim())) minmax[1] = "-1";
            minPrice = Double.parseDouble(minmax[0]);
            maxPrice = Double.parseDouble(minmax[1]);
          }
        }
        if (!TextUtils.isEmpty(categories)) {
          categories = categories.substring(0, categories.length() - 1);
          parentIds = parentIds.substring(0, parentIds.length() - 1);
          contentEntity.categoryid(categories);
          contentEntity.parentid(parentIds);
        }
        if (!TextUtils.isEmpty(brands)) {
          brands = brands.substring(0, brands.length() - 1);
          contentEntity.brandIds(brands);
        }
        if (!TextUtils.isEmpty(mediatypes)) {
          mediatypes = mediatypes.substring(0, mediatypes.length() - 1);
          contentEntity.mediatypes(mediatypes);
        }
      }
      showFilterItems();
      contentEntity.minPrice(minPrice);
      contentEntity.maxPrice(maxPrice);
      requestEntity.content(contentEntity);
      presenter.search(requestEntity);

           /* "categoryid":"0,4648,4655", // 0’dan başlayarak ağacın tepesinden itibaren kategori

                    "parentId":4655, // seçili kategori

                    "brandIds":”4957,4958”, // markalar

            "mediatypes":"PC DVD-Rom,CD",   // medya tipleri*/
    } else {
      Snackbar.make(coordinatorLayout, R.string.less_word_length, Snackbar.LENGTH_SHORT).show();
    }
  }

  private void showFilterItems() {
    lnFilters.removeAllViews();
    lnFilters.setVisibility(mFilterList.size() > 0 ? View.VISIBLE : View.GONE);
    if (mFilterList.size() == 0) return;

    Iterator it = mFilterList.entrySet().iterator();
    int i = 0;
    LinearLayout row = null;
    LinearLayout cell = null;
    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    while (it.hasNext()) {
      LinkedHashMap.Entry<String, SearchFilterItem> entry =
          (LinkedHashMap.Entry<String, SearchFilterItem>) it.next();
      SearchFilterItem filterItem = entry.getValue();

      if (i % 2 == 0) {
        row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        row.setWeightSum(2);
        row.setLayoutParams(params);
      }
      cell = (LinearLayout) inflater.inflate(R.layout.search_filtered_item, null);
      LinearLayout.LayoutParams cellParams =
          new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
      cellParams.weight = 1;
      cell.setLayoutParams(cellParams);
      cell.setTag(entry.getKey());
      TextView t = (TextView) cell.findViewById(R.id.filterText);
      t.setText(filterItem.getName());
      cell.setOnClickListener(v -> {
        if (!entry.getValue().getParent().contentEquals(getString(R.string.category))) {
          mFilterList.remove(entry.getKey());
          lnFilters.removeView(v);
        } else {
          removeCategoryItems(entry);
        }

        btnSearchClicked();
      });

      row.addView(cell);
      if (i % 2 == 0) lnFilters.addView(row);
      i++;
    }
  }

  private void removeCategoryItems(LinkedHashMap.Entry<String, SearchFilterItem> itemEntry) {
    Iterator it = mFilterList.entrySet().iterator();
    int i = 0, itemPositionInList = Integer.MAX_VALUE;
    while (it.hasNext()) {
      LinkedHashMap.Entry<String, SearchFilterItem> entry =
          (LinkedHashMap.Entry<String, SearchFilterItem>) it.next();
      if (entry.getKey().contentEquals(itemEntry.getKey())) {
        itemPositionInList = i;
      }
      if (i >= itemPositionInList && entry.getValue()
          .getParent()
          .contentEquals(getString(R.string.category))) {
        it.remove();
        for (int j = 0; j < lnFilters.getChildCount(); j++) {
          View v = lnFilters.getChildAt(j);
          if (v.getTag() != null && v.getTag().toString().contentEquals(entry.getKey())) {
            lnFilters.removeView(v);
            break;
          }
        }
      }
      i++;
    }
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    ButterKnife.bind(this);
    searchActivityComponent = DaggerSearchActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    searchActivityComponent.inject(this);

    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_menu);
    toolbar.setNavigationOnClickListener(v -> finish());
    toolbar.setTitle("");
    setTitle("");
    presenter.setView(this);

    openingType = OpeningType.SEARCH;
    if (getIntent().getStringExtra(Keys.OPEN_FOR_CATALOG) != null) {
      openingType = OpeningType.CATALOG;
      catalogId = getIntent().getStringExtra(Keys.OPEN_FOR_CATALOG);
      catalogTopic = getIntent().getStringExtra(Keys.CATALOG_TOPIC);
    }

    prepareSearchOrderSpinner();

    if (openingType == OpeningType.SEARCH) {
      deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.bg_red_toolbar_start));
      txtSearch.setOnEditorActionListener((v, actionId, event) -> {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
          searchSubResponseEntity = null;
          mFilterList = new LinkedHashMap<>();
          btnSearchClicked();
          return true;
        }
        return false;
      });
    } else {
      //KATALOG
      deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.status_green_end));
      toolbar.setBackground(getResources().getDrawable(R.drawable.bg_main_header));
      rlSearchComponent.setVisibility(View.GONE);
      getCatalogResult(catalogId);
      category_title.setText(catalogTopic);
      basketViewComponent.setOnBasketClickListener(this);
      searchViewComponent.setOnSearchClickListener(this);
      basketViewComponent.setVisibility(View.VISIBLE);
      searchViewComponent.setVisibility(View.VISIBLE);
    }

    rvSearch.setHasFixedSize(true);
    GridLayoutManager glm =
        new GridLayoutManager(this, ((ApplicationController) getApplication()).getColumnCount());
    rvSearch.setLayoutManager(glm);
    rvSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        visibleItemCount = glm.getChildCount();
        totalItemCount = glm.getItemCount();
        pastVisiblesItems = glm.findFirstVisibleItemPosition();

        if (loading) {
          if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
            page++;
            loading = false;
            start += size;
            if (openingType == OpeningType.SEARCH) {
              search();
            } else {
              getCatalogResult(catalogId);
            }
          }
        }
      }
    });
  }

  private void prepareSearchOrderSpinner() {
    List<SearchOrderItem> searchOrderItems = new ArrayList<>();
    if (openingType == OpeningType.SEARCH) {
      searchOrderItems.add(new SearchOrderItem(getString(R.string.ordering), "relevance", "desc"));
    }
    searchOrderItems.add(
        new SearchOrderItem(getString(R.string.price_highest_first), "price", "desc"));
    searchOrderItems.add(
        new SearchOrderItem(getString(R.string.price_lowest_first), "price", "asc"));
    searchOrderItems.add(
        new SearchOrderItem(getString(R.string.comment_highest_first), "commentcount", "desc"));
    searchOrderItems.add(
        new SearchOrderItem(getString(R.string.comment_lowest_first), "commentcount", "asc"));
    searchOrderItems.add(
        new SearchOrderItem(getString(R.string.soldcount_highest_first), "soldcount", "desc"));
    searchOrderItems.add(
        new SearchOrderItem(getString(R.string.soldcount_lowest_first), "soldcount", "asc"));
    searchOrderItems.add(
        new SearchOrderItem(getString(R.string.createdate_newest_first), "createdate", "desc"));
    searchOrderItems.add(
        new SearchOrderItem(getString(R.string.createdate_lowest_first), "createdate", "asc"));
    searchOrderItems.add(new SearchOrderItem(getString(R.string.a_to_z), "name.sortable", "asc"));
    searchOrderItems.add(new SearchOrderItem(getString(R.string.z_to_a), "name.sortable", "desc"));
    searchOrderItems.add(
        new SearchOrderItem(getString(R.string.discount_highest_first), "discount", "desc"));
    searchOrderItems.add(
        new SearchOrderItem(getString(R.string.discount_lowest_first), "discount", "asc"));
    SearchOrderAdapter adapter = new SearchOrderAdapter(this, searchOrderItems);
    ddlOrder.setAdapter(adapter);
    //ddlOrder.setSelection(4);
    ddlOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //SearchOrderItem searchOrderItem = (SearchOrderItem) ddlOrder.getAdapter().getItem
        // (position);
        if (openingType == OpeningType.SEARCH) {
          btnSearchClicked();
        } else {
          page = 1;
          searchResult = null;
          getCatalogResult(catalogId);
        }
      }

      @Override public void onNothingSelected(AdapterView<?> parent) {

      }
    });
  }

  @Override protected void onResume() {
    super.onResume();
    getBasketCount();
    if (rvSearch.getAdapter() != null) rvSearch.getAdapter().notifyDataSetChanged();
  }

  @Override public void getBasketCount() {
    presenter.getBasketCount();
  }

  @Override public void onSearchResult(SearchResponseEntity searchResponseEntity) {

    searchSubResponseEntity = searchResponseEntity.searchResponse();

    willShowBtnCollapse();

    btnCollapse.setOnClickListener(v -> {
      android.app.FragmentManager fm = getFragmentManager();
      SearchFilterDialog filterDialog =
          SearchFilterDialog.getInstance(searchSubResponseEntity, mFilterList);
      filterDialog.setOnFilterClicked(list -> {
        mFilterList = list;
        SearchActivity.this.btnSearchClicked();
      });
      filterDialog.show(fm, "Filtre");
    });

    rlResultInfo.setVisibility(View.VISIBLE);
    rlBarcode.setVisibility(View.GONE);
    loading = this.size == searchResponseEntity.searchResponse().products().size();

    lblCount.setText(String.format("%s %s", searchResponseEntity.searchResponse().hitCount(),
        getString(R.string.result_count)));

    if (searchResult == null) {
      searchResult = new ArrayList<>(searchResponseEntity.searchResponse().products());
      SearchAdapter adapter = new SearchAdapter(this, searchResult, presenter);
      adapter.setOnOpenLoginListener(() -> navigator.navigateToLoginActivity(SearchActivity.this));
      adapter.setOnOpenProductDetailListener((id, sku) -> {
        navigator.navigateToProductDetailActivity(
            SearchActivity.this, id, sku, getString(R.string.search_result));

        getDataLayer().pushEvent("productClick", DataLayer.mapOf(
            "ecommerce", DataLayer.mapOf("click",
                DataLayer.mapOf("actionField", DataLayer.mapOf("list", "Search Results"),
                    // Ürünün Clicklendiği Sayfa Tipi (Search Results, Main Category,
                    // SubCategory, Homepage)
                    "products", DataLayer.listOf(
                        DataLayer.mapOf("name", "undefined", "id", id, "price", "undefined",
                            "brand", "undefined", "category", "undefined"))))));
      });

      adapter.setOnBellListener((isChecked, sku) -> {
        if (isChecked) {
          final Dialog dialog = new Dialog(this);
          dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
          dialog.setCancelable(false);
          dialog.setContentView(R.layout.dialog_bell);
          dialog.setTitle("");

          dialog.findViewById(R.id.cancel).setOnClickListener(v -> dialog.dismiss());

          EditText indays = (EditText) dialog.findViewById(R.id.indays);
          EditText below_price = (EditText) dialog.findViewById(R.id.below_price);

          dialog.findViewById(R.id.ok).setOnClickListener(v -> {
            if (below_price.getText().toString().length() == 0) {
              below_price.setError("Fiyat girmek zorunludur");
              return;
            }
            presenter.addItemToAlarmList(indays.getText().toString(),
                below_price.getText().toString(), sku);
            dialog.dismiss();
          });

          dialog.show();
        } else {
          presenter.removeAlarmListItem(sku);
        }
      });

      rvSearch.setAdapter(adapter);
    } else {
      searchResult.addAll(searchResponseEntity.searchResponse().products());
      ((SearchAdapter) rvSearch.getAdapter()).updateList(searchResult);
      rvSearch.getAdapter().notifyDataSetChanged();
    }
  }

  @Override public void itemAddedToWishList(String code) {
    Snackbar.make(
        coordinatorLayout, "Seçtiğiniz ürün favori listesine eklendi", Snackbar.LENGTH_SHORT)
        .show();
  }

  @Override public void itemAddedToAlarmList(String code) {
    Snackbar.make(
        coordinatorLayout, "Seçtiğiniz ürün alarm listesine eklendi", Snackbar.LENGTH_SHORT).show();
  }

  @Override public void onRemovedFromAlarmList(String code) {
    Snackbar.make(
        coordinatorLayout, "Seçtiğiniz ürün alarm listesinden çıkarıldı", Snackbar.LENGTH_SHORT)
        .show();
  }

  @Override public void removeItemFromWishList(String code) {
    Snackbar.make(
        coordinatorLayout, "Seçtiğiniz ürün favori listesinden çıkarıldı", Snackbar.LENGTH_SHORT)
        .show();
  }

  @Override public void onCatalogResult(SearchResponseEntity searchResponseEntity) {

    btnCollapse.setVisibility(View.GONE);
    rlResultInfo.setVisibility(View.VISIBLE);
    rlBarcode.setVisibility(View.GONE);

    loading = this.size == searchResponseEntity.searchResponse().products().size();

    lblCount.setText(String.format("%s %s", searchResponseEntity.searchResponse().hitCount(),
        getString(R.string.result_count)));

    if (searchResult == null) {
      searchResult = new ArrayList<>(searchResponseEntity.searchResponse().products());
      SearchAdapter adapter = new SearchAdapter(this, searchResult, presenter);
      adapter.setOnOpenLoginListener(() -> navigator.navigateToLoginActivity(SearchActivity.this));
      adapter.setOnOpenProductDetailListener((id, sku) -> {
        navigator.navigateToProductDetailActivity(
            SearchActivity.this, id, sku, getString(R.string.search_result));

        getDataLayer().pushEvent("productClick", DataLayer.mapOf(
            "ecommerce", DataLayer.mapOf("click",
                DataLayer.mapOf("actionField", DataLayer.mapOf("list", "Search Results"),
                    // Ürünün Clicklendiği Sayfa Tipi (Search Results, Main Category,
                    // SubCategory, Homepage)
                    "products", DataLayer.listOf(
                        DataLayer.mapOf("name", "undefined", "id", id, "price", "undefined",
                            "brand", "undefined", "category", "undefined"))))));
      });

      adapter.setOnBellListener((isChecked, sku) -> {
        if (isChecked) {
          final Dialog dialog = new Dialog(this);
          dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
          dialog.setCancelable(false);
          dialog.setContentView(R.layout.dialog_bell);
          dialog.setTitle("");

          dialog.findViewById(R.id.cancel).setOnClickListener(v -> dialog.dismiss());

          EditText indays = (EditText) dialog.findViewById(R.id.indays);
          EditText below_price = (EditText) dialog.findViewById(R.id.below_price);

          dialog.findViewById(R.id.ok).setOnClickListener(v -> {
            if (below_price.getText().toString().length() == 0) {
              below_price.setError("Fiyat girmek zorunludur");
              return;
            }
            presenter.addItemToAlarmList(indays.getText().toString(),
                below_price.getText().toString(), sku);
            dialog.dismiss();
          });

          dialog.show();
        } else {
          presenter.removeAlarmListItem(sku);
        }
      });

      rvSearch.setAdapter(adapter);
    } else {
      searchResult.addAll(searchResponseEntity.searchResponse().products());
      ((SearchAdapter) rvSearch.getAdapter()).updateList(searchResult);
      rvSearch.getAdapter().notifyDataSetChanged();
    }
  }

  @Override public SearchActivityComponent getComponent() {
    return searchActivityComponent;
  }

  @Override public void setBasketCount(int count) {
    if (openingType == OpeningType.CATALOG) {
      basket_count.setText(String.valueOf(count));
      basket_count.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
    }
  }

  private void willShowBtnCollapse() {
    SearchSubResponseEntity data = searchSubResponseEntity;
    int a = 0;
    if (data.brands() != null && data.brands().size() > 1) {
      for (int i = 0; i < data.brands().size(); i++) {
        a++;
      }
    }

    if (data.mediaTypes() != null && data.mediaTypes().size() > 1) {
      for (int i = 0; i < data.mediaTypes().size(); i++) {
        a++;
      }
    }
    if (data.categories() != null && data.categories().size() > 1) {
      for (int i = 0; i < data.categories().size(); i++) {
        a++;
      }
    }

    if (data.prices() != null && data.prices().size() > 1) {
      for (int i = 0; i < data.prices().size(); i++) {
        a++;
      }
    }

    btnCollapse.setVisibility(a > 0 ? View.VISIBLE : View.INVISIBLE);
  }

  @OnClick(R.id.rlBarcode) void onBarcodeClicked() {
    startActivity(BarcodeInfoActivity.createLauncher(this));
  }

  enum OpeningType {SEARCH, CATALOG}
}
