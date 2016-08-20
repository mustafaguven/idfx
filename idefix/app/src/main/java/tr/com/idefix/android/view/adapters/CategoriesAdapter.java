package tr.com.idefix.android.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import tr.com.idefix.android.R;
import tr.com.idefix.android.model.CategoryModel;
import tr.com.idefix.android.view.listeners.OnCategorySelectedListener;

/**
 * Created by utkan on 16/09/15.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CustomViewHolder> {
  OnCategorySelectedListener onCategorySelectedListener;
  private List<CategoryModel> categoryModelList;
  private Context mContext;

  public CategoriesAdapter(Context context, List<CategoryModel> categoryModelList) {
    this.categoryModelList = categoryModelList;
    this.mContext = context;
  }

  public void setOnCategorySelectedListener(OnCategorySelectedListener onCategorySelectedListener) {
    this.onCategorySelectedListener = onCategorySelectedListener;
  }

  @Override public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, null);

    CustomViewHolder viewHolder = new CustomViewHolder(view);
    return viewHolder;
  }

  @Override public void onBindViewHolder(CustomViewHolder holder, int i) {
    CategoryModel categoryModel = categoryModelList.get(i);

    holder.textView.setText(categoryModel.name());

    holder.textView.setOnClickListener(v -> {
      if (onCategorySelectedListener != null) {
        onCategorySelectedListener.onCategorySelected(categoryModel);
      }
    });
  }

  @Override public int getItemCount() {
    return (null != categoryModelList ? categoryModelList.size() : 0);
  }

  public class CustomViewHolder extends RecyclerView.ViewHolder {

    protected TextView textView;

    public CustomViewHolder(View view) {
      super(view);
      this.textView = (TextView) view.findViewById(R.id.title);
    }
  }
}