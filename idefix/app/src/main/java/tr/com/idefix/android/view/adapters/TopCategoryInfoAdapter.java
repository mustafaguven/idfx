package tr.com.idefix.android.view.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;
import java.util.List;
import mu.comon.utils.FormatUtils;
import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.R;
import tr.com.idefix.android.model.TopCategoryInfoItemModel;
import tr.com.idefix.android.view.listeners.OnOpenLoginListener;
import tr.com.idefix.android.view.listeners.OnOpenProductDetailListener;
import tr.com.idefix.domain.Alarm;
import tr.com.idefix.domain.DRItem;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.Wish;
import tr.com.idefix.domain.events.AlarmWishEvent;
import tr.com.idefix.domain.internal.di.components.ApplicationComponent;

/**
 * Created on 10.3.15.
 */
public class TopCategoryInfoAdapter
    extends RecyclerView.Adapter<TopCategoryInfoAdapter.TopCategoryInfoHolder> {

  //<editor-fold desc="Fields">
  private final Context mContext;
  private final List<TopCategoryInfoItemModel> mItems;
  private final Bus bus;
  private final int EKITAP = 5699;
  DomainContext domainContext;
  int parentCategoryID;
  private OnOpenLoginListener onOpenLoginListener;
  private OnOpenProductDetailListener onOpenProductDetailListener;

  //</editor-fold>

  public TopCategoryInfoAdapter(
      Context context, List<TopCategoryInfoItemModel> items, int parentCategoryID
  ) {
    mContext = context;
    mItems = items;
    this.parentCategoryID = parentCategoryID;

    ApplicationComponent component =
        ApplicationController.getInstance().getDomainApplicationComponent();
    domainContext = component.domainContext();
    bus = component.busProvider().getBus();
  }

  public TopCategoryInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
    return new TopCategoryInfoHolder(view);
  }

  @Override public void onBindViewHolder(TopCategoryInfoHolder holder, final int position) {

    TopCategoryInfoItemModel item = mItems.get(position);

    holder.title.setText(item.name());

    Picasso.with(mContext).load(item.imageUrl()).into(holder.image);

    if (item.oldPrice().equals(item.price())) {
      holder.oldPrice.setText("");
    } else {
      holder.oldPrice.setText(FormatUtils.getTrFormatWithCurrency(item.oldPrice()));
      holder.oldPrice.setPaintFlags(holder.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    holder.favorite_view.setOnCheckedChangeListener(null);
    holder.favorite_view.setOnClickListener(null);
    holder.favorite_view.setChecked(false);
    holder.favorite_view.setEnabled(false);

    holder.bell_view.setOnCheckedChangeListener(null);
    holder.bell_view.setOnClickListener(null);
    holder.bell_view.setChecked(false);
    //        holder.bell_view.setEnabled(false);

    if (domainContext.getLoggedInUser() != null) {

      boolean favorite = false;
      if (domainContext.getCachedWish() != null && !TextUtils.isEmpty(item.sku())) {
        Wish list = domainContext.getCachedWish();

        if (list.items().size() > 0) {
          for (DRItem it : list.items()) {
            if (!TextUtils.isEmpty(it.sku()) && it.sku().equals(item.sku())) {
              favorite = true;
              break;
            }
          }
        }
      }

      if (!favorite) {
        holder.favorite_view.setChecked(false);
        holder.favorite_view.setEnabled(true);
        holder.favorite_view.setOnCheckedChangeListener((buttonView, isChecked) -> {
          if (isChecked) {
            holder.favorite_view.setEnabled(false);
            holder.favorite_view.setOnCheckedChangeListener(null);
            bus.post(new AlarmWishEvent(isChecked, item.sku()).favorite(true));
          }
        });
      } else {
        holder.favorite_view.setOnCheckedChangeListener(null);
        holder.favorite_view.setChecked(true);
        holder.favorite_view.setEnabled(false);
      }

      boolean bell = false;
      if (domainContext.getCachedAlarm() != null && !TextUtils.isEmpty(item.sku())) {
        Alarm list = domainContext.getCachedAlarm();

        if (list.items().size() > 0) {
          for (DRItem it : list.items()) {
            if (!TextUtils.isEmpty(it.sku()) && it.sku().equals(item.sku())) {
              bell = true;
              break;
            }
          }
        }
      }

      holder.bell_view.setChecked(bell);

      holder.bell_view.setOnCheckedChangeListener((buttonView, isChecked) -> {
        bus.post(new AlarmWishEvent(isChecked, item.sku()).bell(true));
      });
    } else {

      holder.favorite_view.setEnabled(true);
      holder.bell_view.setEnabled(true);

      holder.favorite_view.setOnClickListener(v -> {
        holder.favorite_view.setChecked(false);
        if (onOpenLoginListener != null) {
          onOpenLoginListener.onOpenLogin();
        }
      });

      holder.bell_view.setOnClickListener(v -> {
        holder.bell_view.setChecked(false);
        if (onOpenLoginListener != null) {
          onOpenLoginListener.onOpenLogin();
        }
      });
    }

    holder.price.setText(FormatUtils.getTrFormatWithCurrency(item.price()));

    holder.container.setOnClickListener(v -> {
      if (onOpenProductDetailListener != null && !TextUtils.isEmpty(item.sku())) {
        onOpenProductDetailListener.onOpenProductDetail(item.id(), item.sku());
      }
    });
  }

  @Override public int getItemCount() {
    return mItems.size();
  }

  public void setOnOpenLoginListener(OnOpenLoginListener listener) {
    this.onOpenLoginListener = listener;
  }

  public void setOnOpenProductDetailListener(
      OnOpenProductDetailListener onOpenProductDetailListener
  ) {
    this.onOpenProductDetailListener = onOpenProductDetailListener;
  }

  public void setFilteredProducts(List<TopCategoryInfoItemModel> filteredProduct) {
    this.mItems.addAll(filteredProduct);
    notifyDataSetChanged();
  }

  static class TopCategoryInfoHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.image) ImageView image;

    @Bind(R.id.container) RelativeLayout container;

    @Bind(R.id.title) AppCompatTextView title;

    @Bind(R.id.oldPrice) AppCompatTextView oldPrice;

    @Bind(R.id.price) AppCompatTextView price;

    @Bind(R.id.favorite_view) CheckBox favorite_view;

    @Bind(R.id.bell_view) CheckBox bell_view;

    TopCategoryInfoHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
}
