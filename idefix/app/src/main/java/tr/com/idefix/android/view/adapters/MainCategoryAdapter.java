package tr.com.idefix.android.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.R;
import tr.com.idefix.android.events.MainCategorySelectedEvent;
import tr.com.idefix.android.model.MainCategoryModel;

/**
 * Created by utkan on 03/10/15.
 */
public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.ViewHolder> {

  List<MainCategoryModel> list;

  public MainCategoryAdapter(List<MainCategoryModel> list) {
    this.list = list;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_main_category, viewGroup, false);
    ViewHolder viewHolder = new ViewHolder(v);
    return viewHolder;
  }

  @Override public void onBindViewHolder(ViewHolder viewHolder, int i) {

    MainCategoryModel menu = list.get(i);
    viewHolder.title.setText(menu.name());

    viewHolder.title.setOnClickListener(v -> {

      ApplicationController.getInstance()
          .getDomainApplicationComponent()
          .busProvider()
          .getBus()
          .post(new MainCategorySelectedEvent(menu));
    });
  }

  @Override public int getItemCount() {
    return list == null ? 0 : list.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    TextView title;

    ViewHolder(View itemView) {
      super(itemView);
      title = (TextView) itemView.findViewById(R.id.title);
    }
  }
}