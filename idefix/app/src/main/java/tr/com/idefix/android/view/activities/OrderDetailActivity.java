package tr.com.idefix.android.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import javax.inject.Inject;
import mu.comon.utils.DateTimeUtils;
import tr.com.idefix.android.R;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.internal.di.components.DaggerOrderDetailActivityComponent;
import tr.com.idefix.android.internal.di.components.OrderDetailActivityComponent;
import tr.com.idefix.android.presenter.IOrderDetailPresenter;
import tr.com.idefix.android.view.IOrderDetailView;
import tr.com.idefix.android.view.components.OrderDetailItemView;
import tr.com.idefix.data.entity.OrderDetail;

public class OrderDetailActivity extends BaseActivity implements IOrderDetailView {

  @Inject IOrderDetailPresenter presenter;
  OrderDetailActivityComponent orderDetailActivityComponent;

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.lblOrderDate) TextView lblOrderDate;
  @Bind(R.id.lblOrderStatus) TextView lblOrderStatus;
  @Bind(R.id.lblPriceWithoutDiscount) TextView lblPriceWithoutDiscount;
  @Bind(R.id.lblDiscount) TextView lblDiscount;
  @Bind(R.id.rlDiscount) RelativeLayout rlDiscount;
  @Bind(R.id.lblPriceTotal) TextView lblPriceTotal;
  @Bind(R.id.rlItems) RelativeLayout rlItems;
  @Bind(R.id.lblCargoPrice) TextView lblCargoPrice;
  @Bind(R.id.lblShippingAddress) TextView lblShippingAddress;
  @Bind(R.id.lblInvoiceAddress) TextView lblInvoiceAddress;
  @Bind(R.id.scrollView) ScrollView scrollView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.order_detail);
    ButterKnife.bind(this);
    orderDetailActivityComponent = DaggerOrderDetailActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    orderDetailActivityComponent.inject(this);

    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_white_back);
    toolbar.setNavigationOnClickListener(v -> finish());
    presenter.setView(this);
    presenter.getOrderDetail(getIntent().getStringExtra(Keys.ORDER_ID));
  }

  public static Intent createLauncher(Activity activity, String orderId) {
    return new Intent(activity, OrderDetailActivity.class).putExtra(Keys.ORDER_ID, orderId);
  }

  @Override public void setBasketCount(int count) {

  }

  @Override public void fetchOrderDetail(OrderDetail orderDetail) {
    scrollView.setVisibility(View.VISIBLE);
    populateOrderInfo(orderDetail);
    populateItems(orderDetail);
    lblCargoPrice.setText(orderDetail.orderShippingtotal());
    lblShippingAddress.setText(getShippingAddress(orderDetail));
    lblInvoiceAddress.setText(getInvoiceAddress(orderDetail));
  }

  @NonNull private String getInvoiceAddress(OrderDetail orderDetail) {
    StringBuilder sb = new StringBuilder();
    sb.append(orderDetail.billingAddress().address1());
    sb.append("\n");
    if (!TextUtils.isEmpty(orderDetail.shippingAddress().zipPostalCode())) {
      sb.append(orderDetail.billingAddress().zipPostalCode());
      sb.append(" - ");
    }
    if (!TextUtils.isEmpty(orderDetail.shippingAddress().county())) {
      sb.append(orderDetail.billingAddress().county());
      sb.append(" - ");
    }
    if (!TextUtils.isEmpty(orderDetail.shippingAddress().city())) {
      sb.append(orderDetail.billingAddress().city());
      sb.append(" - ");
    }
    if (!TextUtils.isEmpty(orderDetail.shippingAddress().countryName())) {
      sb.append(orderDetail.billingAddress().countryName());
    }
    return sb.toString();
  }

  @NonNull private String getShippingAddress(OrderDetail orderDetail) {
    StringBuilder sb = new StringBuilder();
    sb.append(orderDetail.shippingAddress().address1());
    sb.append("\n");
    if (!TextUtils.isEmpty(orderDetail.shippingAddress().zipPostalCode())) {
      sb.append(orderDetail.shippingAddress().zipPostalCode());
      sb.append(" - ");
    }
    if (!TextUtils.isEmpty(orderDetail.shippingAddress().county())) {
      sb.append(orderDetail.shippingAddress().county());
      sb.append(" - ");
    }
    if (!TextUtils.isEmpty(orderDetail.shippingAddress().city())) {
      sb.append(orderDetail.shippingAddress().city());
      sb.append(" - ");
    }
    if (!TextUtils.isEmpty(orderDetail.shippingAddress().countryName())) {
      sb.append(orderDetail.shippingAddress().countryName());
    }
    return sb.toString();
  }

  private void populateOrderInfo(OrderDetail orderDetail) {
    rlDiscount.setVisibility(View.GONE);
    lblOrderDate.setText(DateTimeUtils.parseDateFromDNRDate(orderDetail.createdOn()));
    lblOrderStatus.setText(orderDetail.orderStatus());
    lblPriceWithoutDiscount.setText(orderDetail.orderSubtotal());
    lblPriceTotal.setText(orderDetail.orderTotal());
    if (orderDetail.orderTotalDiscount() != null) {
      rlDiscount.setVisibility(View.VISIBLE);
      lblDiscount.setText(orderDetail.orderTotalDiscount().toString());
    }
  }

  private void populateItems(OrderDetail orderDetail) {
    OrderDetailItemView view = new OrderDetailItemView(this);
    view.create(orderDetail);
    rlItems.addView(view);
  }
}
