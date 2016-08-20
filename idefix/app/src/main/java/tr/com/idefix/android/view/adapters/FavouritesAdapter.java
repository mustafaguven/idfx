package tr.com.idefix.android.view.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import tr.com.idefix.android.R;
import tr.com.idefix.android.presenter.IMyListPresenter;
import tr.com.idefix.data.net.RestApiBaseService;
import tr.com.idefix.domain.DRItem;

/**
 * Created by mustafaguven on 17/10/15.
 */
public class FavouritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int TYPE_HEADER = 0;
  private static final int TYPE_NORMAL = 1;
  private static final int BLINK_DURATION = 300;

  HeaderViewHolder headerViewHolder = null;
  Context context;
  List<DRItem> favouritesList;
  Set<Integer> clickedItems = new HashSet<>();
  IMyListPresenter presenter;

  public FavouritesAdapter(Context context, List<DRItem> list, IMyListPresenter presenter) {
    this.context = context;
    this.favouritesList = new ArrayList<>(list);
    this.favouritesList.add(0, new DRItem());
    this.presenter = presenter;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    if (viewType == TYPE_HEADER) {
      View v = LayoutInflater.from(viewGroup.getContext())
          .inflate(R.layout.favourites_adapter_header_item, viewGroup, false);
      return new HeaderViewHolder(v);
    } else {
      View v = LayoutInflater.from(viewGroup.getContext())
          .inflate(R.layout.favourite_adapter_item, viewGroup, false);
      return new ItemHolder(v);
    }
  }

  @Override public int getItemViewType(int position) {
    if (position == 0) {
      return TYPE_HEADER;
    } else {
      return TYPE_NORMAL;
    }
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    if (viewHolder instanceof HeaderViewHolder) {
      headerViewHolder = (HeaderViewHolder) viewHolder;
    } else {

      ItemHolder itemHolder = (ItemHolder) viewHolder;
      DRItem favouriteItem = favouritesList.get(i);
      String imageUrl =
          String.format("%s%s", RestApiBaseService.API_BASE_IMAGE_URL, favouriteItem.imageUrl());
      itemHolder.lblTitle.setText(favouriteItem.name());
      itemHolder.lblPrice.setText(String.valueOf(favouriteItem.unitPrice()));
      itemHolder.lblCategoryName.setText(String.valueOf(favouriteItem.categoryName()));
      //itemHolder.lblStockStatus.setText(favouriteItem.sku());
      Picasso.with(context).load(imageUrl).fit().centerCrop().into(itemHolder.imgPicture);
      itemHolder.imgCheck.setImageResource(
          clickedItems.contains(i) ? R.drawable.ic_checked : R.drawable.ic_unchecked);
    }
  }

  @Override public int getItemCount() {
    return favouritesList == null ? 0 : favouritesList.size();
  }

  void showAnim(View v) {
    //v.setVisibility(View.VISIBLE);
    v.animate()
        .setDuration(BLINK_DURATION)
        .alpha(1f)
        .setInterpolator(new DecelerateInterpolator(2))
        .start();
  }

  void closeAnim(View v) {
    v.animate()
        .setDuration(BLINK_DURATION)
        .alpha(0f)
        .setInterpolator(new DecelerateInterpolator(2))
        .setListener(new AnimatorListenerAdapter() {
          @Override public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            //v.setVisibility(clickedItems.size() > 0 ? View.VISIBLE : View.GONE);
          }
        })
        .start();
  }

  class ItemHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.lnFavouriteCard) LinearLayout lnFavouriteCard;

    @Bind(R.id.lblTitle) TextView lblTitle;

    @Bind(R.id.lblCategoryName) TextView lblCategoryName;

    @Bind(R.id.lblPrice) TextView lblPrice;

    @Bind(R.id.lblStockStatus) TextView lblStockStatus;

    @Bind(R.id.imgPicture) ImageView imgPicture;

    @Bind(R.id.imgCheck) ImageView imgCheck;

    ItemHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.lnFavouriteCard) void onClickedFavouriteCard() {
      if (clickedItems.contains(getAdapterPosition())) {
        clickedItems.remove(getAdapterPosition());
      } else {
        clickedItems.add(getAdapterPosition());
      }

      imgCheck.setImageResource(clickedItems.contains(getAdapterPosition()) ? R.drawable.ic_checked
          : R.drawable.ic_unchecked);
      headerViewHolder.requestToChangeHeader();
    }
  }

  class HeaderViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.rlSelectAll) RelativeLayout rlSelectAll;
    @Bind(R.id.rlPurchase) RelativeLayout rlPurchase;
    @Bind(R.id.rlDelete) RelativeLayout rlDelete;
    @Bind(R.id.imgDelete) ImageView imgDelete;
    @Bind(R.id.imgPurchase) ImageView imgPurchase;
    @Bind(R.id.imgSelectAll) ImageView imgSelectAll;
    @Bind(R.id.lblPurchase) TextView lblPurchase;
    @Bind(R.id.lblDelete) TextView lblDelete;
    private boolean headerOldVisibilityStatus = false, headerNewVisibilityStatus = false;

    HeaderViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.rlSelectAll) void selectAllClicked() {
      boolean isAllClicked = (clickedItems.size() == (favouritesList.size() - 1));
      clickedItems.clear();
      if (!isAllClicked) {
        for (int i = 1; i < favouritesList.size(); i++) {
          clickedItems.add(i);
        }
      }
      rlPurchase.setAlpha(clickedItems.size() > 0 ? 1f : 0f);
      rlDelete.setAlpha(clickedItems.size() > 0 ? 1f : 0f);
      headerOldVisibilityStatus = clickedItems.size() > 0;
      notifyDataSetChanged();
    }

    @OnClick(R.id.rlPurchase) void purchaseClicked() {
      if (clickedItems.size() > 0) {
        presenter.moveItemsToCartFromWishlist(getClickedFavouriteItems());
      }
    }

    List<DRItem> getClickedFavouriteItems() {
      List<DRItem> clickedFavouriteItems = new ArrayList<>();
      for (int i : clickedItems) {
        clickedFavouriteItems.add(favouritesList.get(i));
      }
      return clickedFavouriteItems;
    }

    @OnClick(R.id.rlDelete) void deleteClicked() {
      if (clickedItems.size() > 0) {
        presenter.removeFromWishList(getClickedFavouriteItems());
      }
    }

    void requestToChangeHeader() {
      headerNewVisibilityStatus = clickedItems.size() > 0;

      if (!headerOldVisibilityStatus && headerNewVisibilityStatus) {
        // turn visible on
        showAnim(rlPurchase);
        showAnim(rlDelete);
      } else if (headerOldVisibilityStatus && !headerNewVisibilityStatus) {
        // turn visible off
        closeAnim(rlPurchase);
        closeAnim(rlDelete);
      }
      headerOldVisibilityStatus = headerNewVisibilityStatus;
    }
  }
}