package tr.com.idefix.android.view.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.squareup.picasso.Picasso;
import java.util.List;
import mu.comon.utils.FormatUtils;
import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.R;
import tr.com.idefix.android.presenter.ISearchPresenter;
import tr.com.idefix.android.view.listeners.OnBellListener;
import tr.com.idefix.android.view.listeners.OnOpenLoginListener;
import tr.com.idefix.android.view.listeners.OnOpenProductDetailListener;
import tr.com.idefix.data.entity.ProductOtherEntity;
import tr.com.idefix.data.net.RestApiBaseService;
import tr.com.idefix.domain.Alarm;
import tr.com.idefix.domain.DRItem;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.Wish;

/**
 * Created by mustafaguven on 17/10/15.
 */
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  Context context;
  List<ProductOtherEntity> products;
  ISearchPresenter presenter;
  DomainContext domainContext;
  private OnOpenLoginListener onOpenLoginListener;
  private OnBellListener onBellListener;
  private OnOpenProductDetailListener onOpenProductDetailListener;
  private int EKITAP = 5699;

  public SearchAdapter(
      Context context, List<ProductOtherEntity> products, ISearchPresenter presenter
  ) {
    this.context = context;
    this.products = products;
    this.presenter = presenter;

    domainContext =
        ApplicationController.getInstance().getDomainApplicationComponent().domainContext();
  }

  public void setOnOpenLoginListener(OnOpenLoginListener listener) {
    this.onOpenLoginListener = listener;
  }

  public void setOnBellListener(OnBellListener listener) {
    this.onBellListener = listener;
  }

  public void setOnOpenProductDetailListener(OnOpenProductDetailListener listener) {
    this.onOpenProductDetailListener = listener;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
    return new ItemHolder(v);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
    ItemHolder itemHolder = (ItemHolder) viewHolder;
    ProductOtherEntity item = products.get(i);
    String imageUrl = String.format("%s%s", RestApiBaseService.API_BASE_IMAGE_URL, item.imageUrl());
    itemHolder.title.setText(item.name());
    itemHolder.oldPrice.setText("");

    if (item.categories() != null
        && item.categories().categoryList() != null
        && item.categories().categoryList().get(0) != null) {
      if (item.categories().categoryList().get(0).id() != EKITAP) {
        itemHolder.oldPrice.setText(FormatUtils.getTrFormatWithCurrency(item.oldPrice()));
        itemHolder.oldPrice.setPaintFlags(
            itemHolder.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
      }
    }
    itemHolder.price.setText(FormatUtils.getTrFormatWithCurrency(item.price()));

    Picasso.with(context).load(imageUrl).fit().centerCrop().into(itemHolder.image);

    boolean favorite = false;
    if (domainContext.getCachedWish() != null && !TextUtils.isEmpty(item.stockCode())) {
      Wish list = domainContext.getCachedWish();

      if (list.items().size() > 0) {
        for (DRItem it : list.items()) {
          if (!TextUtils.isEmpty(it.sku()) && it.sku().equals(item.stockCode())) {
            favorite = true;
            break;
          }
        }
      }
    }

    boolean alarm = false;
    if (domainContext.getCachedAlarm() != null && !TextUtils.isEmpty(item.stockCode())) {
      Alarm list = domainContext.getCachedAlarm();

      if (list.items().size() > 0) {
        for (DRItem it : list.items()) {
          if (!TextUtils.isEmpty(it.sku()) && it.sku().equals(item.stockCode())) {
            alarm = true;
            break;
          }
        }
      }
    }
    itemHolder.favorite_view.setChecked(favorite);
    itemHolder.bell_view.setChecked(alarm);
  }

  @Override public int getItemCount() {
    return products == null ? 0 : products.size();
  }

  public void updateList(List<ProductOtherEntity> products) {
    this.products = products;
  }

  class ItemHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.title) TextView title;

    @Bind(R.id.image) ImageView image;

    @Bind(R.id.oldPrice) TextView oldPrice;

    @Bind(R.id.price) TextView price;

    @Bind(R.id.favorite_view) CheckBox favorite_view;

    @Bind(R.id.bell_view) CheckBox bell_view;

    ItemHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.favorite_view) void favorite(CheckBox v) {

      if (!presenter.isLoggedIn()) {
        if (onOpenLoginListener != null) {
          onOpenLoginListener.onOpenLogin();
        }
        return;
      }

      ProductOtherEntity item = products.get(getAdapterPosition());
      presenter.favorite(v.isChecked(), item.stockCode());
    }

    @OnClick(R.id.bell_view) void alarm(CheckBox v) {

      if (!presenter.isLoggedIn()) {
        if (onOpenLoginListener != null) {
          onOpenLoginListener.onOpenLogin();
        }
        return;
      }

      ProductOtherEntity item = products.get(getAdapterPosition());
      if (onBellListener != null) {
        onBellListener.onBell(v.isChecked(), item.stockCode());
      }
    }

    @OnClick(R.id.container) void containerClicked() {
      ProductOtherEntity item = products.get(getAdapterPosition());
      if (onOpenProductDetailListener != null && !TextUtils.isEmpty(item.stockCode())) {
        onOpenProductDetailListener.onOpenProductDetail(item.variationId(), item.stockCode());
      }
    }
  }
}