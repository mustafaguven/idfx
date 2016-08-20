package tr.com.idefix.android.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.List;
import mu.comon.utils.DateTimeUtils;
import tr.com.idefix.android.R;
import tr.com.idefix.android.presenter.IMyOrdersPresenter;
import tr.com.idefix.android.view.activities.NotifyPaymentActivity;
import tr.com.idefix.android.view.activities.OrderDetailActivity;
import tr.com.idefix.data.entity.OrderEntity;

/**
 * Created by mustafaguven on 17/10/15.
 */
public class OrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  Context context;
  List<OrderEntity> orderList;
  IMyOrdersPresenter presenter;
  private static final String WAITING_FOR_WIRE_TRANSFER = "Havale Bekleniyor";

  public OrdersAdapter(Context context, List<OrderEntity> list, IMyOrdersPresenter presenter) {
    this.context = context;
    this.orderList = list;
    this.presenter = presenter;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.order_adapter_item, viewGroup, false);
    return new ItemHolder(v);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    ItemHolder itemHolder = (ItemHolder) viewHolder;
    OrderEntity orderItem = orderList.get(i);
    itemHolder.lblOrderDate.setText(DateTimeUtils.parseDateFromDNRDate(orderItem.getCreatedOn()));
    itemHolder.lblOrderCode.setText(String.valueOf(orderItem.getOrderCode()));
    itemHolder.lblTotalAmount.setText(String.valueOf(orderItem.getOrderTotal()));
    itemHolder.lblOrderStatus.setText(String.valueOf(orderItem.getOrderStatus()));
    itemHolder.btnOrderDetail.setOnClickListener(v -> context.startActivity(
        OrderDetailActivity.createLauncher((Activity) context, String.valueOf(orderItem.getId()))));

    if (orderItem.getOrderStatus().contentEquals(WAITING_FOR_WIRE_TRANSFER)) {
      itemHolder.btnNotifyPurchasing.setVisibility(View.VISIBLE);
      itemHolder.btnNotifyPurchasing.setOnClickListener(v -> context.startActivity(
          NotifyPaymentActivity.createLauncher((Activity) context,
              String.valueOf(orderItem.getOrderCode()))));
    }
  }

  @Override public int getItemCount() {
    return orderList == null ? 0 : orderList.size();
  }

  class ItemHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.lblOrderDate) TextView lblOrderDate;
    @Bind(R.id.lblOrderCode) TextView lblOrderCode;
    @Bind(R.id.lblTotalAmount) TextView lblTotalAmount;
    @Bind(R.id.lblOrderStatus) TextView lblOrderStatus;
    @Bind(R.id.btnOrderDetail) AppCompatButton btnOrderDetail;
    @Bind(R.id.btnNotifyPurchasing) AppCompatButton btnNotifyPurchasing;

    ItemHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}