package tr.com.idefix.android.view.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import javax.inject.Inject;
import tr.com.idefix.android.R;
import tr.com.idefix.android.internal.di.components.BarcodeInfoActivityComponent;
import tr.com.idefix.android.internal.di.components.DaggerBarcodeInfoActivityComponent;
import tr.com.idefix.android.presenter.ISearchPresenter;
import tr.com.idefix.android.view.ISearchView;
import tr.com.idefix.data.entity.ContentEntity;
import tr.com.idefix.data.entity.SearchRequestEntity;
import tr.com.idefix.data.entity.SearchResponseEntity;
import tr.com.idefix.domain.SearchOrderItem;

public class BarcodeInfoActivity extends BaseActivity implements ISearchView {

  private static final int CAMERA_PERMISSION_REQUEST = 100;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.chkDoNotShowAgain) CheckBox chkDoNotShowAgain;
  @Bind(R.id.container) RelativeLayout container;

  @Inject ISearchPresenter presenter;
  BarcodeInfoActivityComponent barcodeInfoActivityComponent;

  @OnClick(R.id.btnOk) void onOkClicked() {
    if (chkDoNotShowAgain.isChecked()) {
      getDomainContext().setBarcodeInfoShown(true);
    }
    scan();
  }

  private void scan() {
    container.setVisibility(View.GONE);
    IntentIntegrator integrator = new IntentIntegrator(this);
    integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
    integrator.setPrompt(getString(R.string.show_camera_to_barcode));
    integrator.setCameraId(0);
    integrator.setBeepEnabled(true);
    integrator.setBarcodeImageEnabled(true);
    integrator.setCaptureActivity(BarcodeActivity.class).initiateScan();
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.barcode_info);
    ButterKnife.bind(this);
    barcodeInfoActivityComponent = DaggerBarcodeInfoActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    barcodeInfoActivityComponent.inject(this);
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_white_back);
    toolbar.setNavigationOnClickListener(v -> finish());
    presenter.setView(this);
    askPermission();
    if (getDomainContext().isBarcodeInfoShown()) {
      scan();
    }
  }

  private void askPermission() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA },
          CAMERA_PERMISSION_REQUEST);
    }
  }

  public static Intent createLauncher(Activity activity) {
    return new Intent(activity, BarcodeInfoActivity.class);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
    if (result != null) {
      if (result.getContents() == null) {
        finish();
      } else {
        getSkuByBarcode(result.getContents());
      }
    } else {
      super.onActivityResult(requestCode, resultCode, data);
    }
  }

  private void getSkuByBarcode(String barcode) {

    SearchOrderItem searchOrderItem =
        new SearchOrderItem(getString(R.string.ordering), "relevance", "desc");
    SearchRequestEntity requestEntity = new SearchRequestEntity();
    ContentEntity contentEntity = new ContentEntity();
    contentEntity.page(1);
    contentEntity.sortfield(searchOrderItem.getSortField());
    contentEntity.sortorder(searchOrderItem.getSortOrder());
    contentEntity.size(1);
    contentEntity.term(barcode);
    requestEntity.reponseType(0);
    double minPrice = -1;
    double maxPrice = -1;
    contentEntity.minPrice(minPrice);
    contentEntity.maxPrice(maxPrice);
    requestEntity.content(contentEntity);
    presenter.search(requestEntity);
  }

  @Override public void getBasketCount() {

  }

  @Override public void onSearchResult(SearchResponseEntity response) {
    if (response != null && response.searchResponse() != null && !response.searchResponse()
        .products()
        .isEmpty()) {
      navigator.navigateToProductDetailActivity(this,
          response.searchResponse().products().get(0).variationId(),
          response.searchResponse().products().get(0).stockCode(),
          getString(R.string.search_result));
    }
    finish();
  }

  @Override public void itemAddedToWishList(String code) {

  }

  @Override public void itemAddedToAlarmList(String code) {

  }

  @Override public void onRemovedFromAlarmList(String code) {

  }

  @Override public void removeItemFromWishList(String sku) {

  }

  @Override public void onCatalogResult(SearchResponseEntity searchResponseEntity) {

  }

  @Override public void setBasketCount(int count) {

  }
}
