package tr.com.idefix.android.view.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import java.util.List;
import tr.com.idefix.android.R;
import tr.com.idefix.data.entity.ItemEntity;
import tr.com.idefix.data.entity.OrderDetail;
import tr.com.idefix.data.net.RestApiBaseService;

public class OrderDetailItemView extends LinearLayout {

  Context context;
  List<ItemEntity> listItemEntity;

  public OrderDetailItemView(Context context) {
    super(context);
    init(context);
  }

  private void init(Context context) {
    this.context = context;
  }

  public OrderDetailItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public OrderDetailItemView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  public void create(OrderDetail orderDetail) {
    this.listItemEntity = orderDetail.items();
    for (ItemEntity itemEntity : listItemEntity) {
      View view = LayoutInflater.from(context).inflate(R.layout.order_detail_item_entity_row, null);
      String imageUrl =
          String.format("%s%s", RestApiBaseService.API_BASE_IMAGE_URL, itemEntity.itemImageUrl());
      ButterKnife.<TextView>findById(view, R.id.lblTitle).setText(
          String.valueOf(itemEntity.productName()));
      ButterKnife.<TextView>findById(view, R.id.lblCategoryName).setText(
          String.valueOf(itemEntity.categoryName()));
      ButterKnife.<TextView>findById(view, R.id.lblShippingStatus).setText(
          String.valueOf(orderDetail.shippingStatus()));
      ButterKnife.<TextView>findById(view, R.id.lblUnit).setText(
          String.valueOf(itemEntity.quantity()));
      ButterKnife.<TextView>findById(view, R.id.lblUnitPrice).setText(
          String.valueOf(itemEntity.unitPrice()));
      ButterKnife.<TextView>findById(view, R.id.lblTotalPrice).setText(
          String.valueOf(itemEntity.subTotal()));
      Picasso.with(context)
          .load(imageUrl)
          .fit()
          .centerCrop()
          .into(ButterKnife.<ImageView>findById(view, R.id.imgPicture));

      this.setOrientation(VERTICAL);
      this.addView(view);
    }
  }
}
