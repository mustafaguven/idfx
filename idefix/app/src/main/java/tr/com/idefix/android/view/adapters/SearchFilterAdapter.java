package tr.com.idefix.android.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import tr.com.idefix.android.R;
import tr.com.idefix.domain.SearchFilterItem;

/**
 * Created by mustafaguven on 17/10/15.
 */
public class SearchFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  Context context;
  List<SearchFilterItem> list;
  LinkedHashMap<String, SearchFilterItem> clickedItems = new LinkedHashMap<>();
  Set<Integer> headerPositions = new HashSet<>();
  String selectedCategory;

  public SearchFilterAdapter(Context context, List<SearchFilterItem> list) {
    this.context = context;
    this.list = list;
    defineHeaderPositions(list);
  }

  private void defineHeaderPositions(List<SearchFilterItem> list) {
    String mHeader = "";
    for (int i = 0; i < list.size(); i++) {
      String header = list.get(i).getParent();
      if (!mHeader.contentEquals(header)) {
        headerPositions.add(i);
        mHeader = header;
      }
    }
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

    View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.search_filter_item, viewGroup, false);
    return new ItemHolder(v);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    ItemHolder itemHolder = (ItemHolder) viewHolder;
    SearchFilterItem item = list.get(i);
    itemHolder.rlHeader.setVisibility(View.GONE);

    if (headerPositions.contains(i)) {
      itemHolder.rlHeader.setVisibility(View.VISIBLE);
      itemHolder.lblHeader.setText(item.getParent());
    }
    itemHolder.lblItem.setText(item.getName());

    itemHolder.imgCheck.setImageResource(
        clickedItems.get(item.getName()) != null && clickedItems.get(item.getName())
            .getName()
            .contentEquals(item.getName()) ? R.drawable.ic_checked : R.drawable.ic_unchecked);
  }

  @Override public int getItemCount() {
    return list == null ? 0 : list.size();
  }

  public LinkedHashMap<String, SearchFilterItem> getList() {
    return clickedItems;
  }

  public void setList(LinkedHashMap<String, SearchFilterItem> list) {
    this.clickedItems = list;
  }

  class ItemHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.rlHeader) RelativeLayout rlHeader;

    @Bind(R.id.rlItem) RelativeLayout rlItem;

    @Bind(R.id.lblHeader) TextView lblHeader;

    @Bind(R.id.lblItem) TextView lblItem;

    @Bind(R.id.imgCheck) ImageView imgCheck;

    ItemHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.rlItem) void selected() {
      SearchFilterItem item = list.get(getAdapterPosition());
      if (clickedItems.containsKey(item.getName())) {
        clickedItems.remove(item.getName());
      } else {
        // fiyat single selectable olmak zorunda
        if (item.getParent().contentEquals(context.getString(R.string.prices))) {
          Iterator it = clickedItems.entrySet().iterator();
          while (it.hasNext()) {
            LinkedHashMap.Entry<String, SearchFilterItem> entry =
                (LinkedHashMap.Entry<String, SearchFilterItem>) it.next();
            if (entry.getValue().getParent().contentEquals(context.getString(R.string.prices))) {
              it.remove();
            }
          }
        }
        if (item.getParent().contentEquals(context.getString(R.string.category))) {
          Iterator it = clickedItems.entrySet().iterator();
          while (it.hasNext()) {
            LinkedHashMap.Entry<String, SearchFilterItem> entry =
                (LinkedHashMap.Entry<String, SearchFilterItem>) it.next();
            if (entry.getValue().getParent().contentEquals(context.getString(R.string.category))) {
              if (!TextUtils.isEmpty(selectedCategory)) {
                if (entry.getKey().contentEquals(selectedCategory)) {
                  it.remove();
                }
              }
            }
          }
          selectedCategory = item.getName();
        }

        clickedItems.put(item.getName(), item);
      }
      notifyDataSetChanged();
    }
  }
}