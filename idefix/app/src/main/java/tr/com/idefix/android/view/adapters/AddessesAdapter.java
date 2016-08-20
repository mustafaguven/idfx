package tr.com.idefix.android.view.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.List;
import tr.com.idefix.android.R;
import tr.com.idefix.android.model.StoreModel;

/**
 * Created on 9.11.15.
 */
public class AddessesAdapter extends RecyclerView.Adapter<AddessesAdapter.StoreViewHolder> {
  private List<StoreModel> storeModelList;
  private Context mContext;

  public AddessesAdapter(Context context, List<StoreModel> storeModelList) {
    this.storeModelList = storeModelList;
    this.mContext = context;
  }

  @Override public StoreViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_adresses, null);

    StoreViewHolder viewHolder = new StoreViewHolder(view);
    return viewHolder;
  }

  @Override public void onBindViewHolder(StoreViewHolder holder, int i) {
    StoreModel storeModel = storeModelList.get(i);

    holder.title.setText(storeModel.storeName());
    holder.address.setText(storeModel.description());
  }

  @Override public int getItemCount() {
    return (null != storeModelList ? storeModelList.size() : 0);
  }

  public void addList(List<StoreModel> addresses) {
    int s = storeModelList.size();
    storeModelList.clear();
    notifyItemRangeRemoved(0, s);
    storeModelList.addAll(addresses);
    notifyItemRangeInserted(0, addresses.size());
  }

  public StoreModel getItem(int position) {
    return storeModelList.get(position);
  }

  class StoreViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.storeName) AppCompatTextView title;
    @Bind(R.id.address) AppCompatTextView address;

    public StoreViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
}