package tr.com.idefix.android.view.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.List;
import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.R;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.FilterItem;
import tr.com.idefix.domain.constants.FilterMap;

/**
 * Created on 10.3.15.
 */
public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.TopCategoryInfoHolder> {

  //<editor-fold desc="Fields">
  private final Context mContext;
  private final List<FilterItem> mItems;
  DomainContext domainContext;
  private List<FilterItem> items;
  //</editor-fold>

  public FilterAdapter(Context context, List<FilterItem> items) {
    mContext = context;
    mItems = items;

    domainContext =
        ApplicationController.getInstance().getDomainApplicationComponent().domainContext();
  }

  public TopCategoryInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(mContext).inflate(R.layout.filter_item, parent, false);
    return new TopCategoryInfoHolder(view);
  }

  @Override public void onBindViewHolder(TopCategoryInfoHolder holder, final int position) {

    FilterItem item = mItems.get(position);

    holder.name.setText(item.name());

    holder.checkBox.setOnCheckedChangeListener(null);
    holder.checkBox.setChecked(item.selected());

    holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

      item.selected(isChecked);

      if (isChecked && item.filterType() == FilterMap.FILTER_TYPE_PRICE) {

        for (int i = 0; i < mItems.size(); i++) {

          FilterItem filterItem = mItems.get(i);

          if (filterItem.filterType() == FilterMap.FILTER_TYPE_PRICE &&
              filterItem.id() != item.id() && filterItem.selected()) {
            filterItem.selected(false);

            break;
          }
        }

        notifyDataSetChanged();
      }
    });
  }

  @Override public int getItemCount() {
    return mItems.size();
  }

  public List<FilterItem> getItems() {
    return mItems;
  }

  public void setItems(List<FilterItem> items) {
    this.items = items;
  }

  static class TopCategoryInfoHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.name) AppCompatTextView name;

    @Bind(R.id.checkbox) AppCompatCheckBox checkBox;

    TopCategoryInfoHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
}
