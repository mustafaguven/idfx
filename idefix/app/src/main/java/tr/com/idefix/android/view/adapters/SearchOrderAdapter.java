package tr.com.idefix.android.view.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import java.util.List;
import tr.com.idefix.domain.SearchOrderItem;

/**
 * Created by mustafaguven on 15.10.2015.
 */
public class SearchOrderAdapter extends BaseAdapter implements SpinnerAdapter {

  Context context;
  List<SearchOrderItem> searchOrderItems;

  public SearchOrderAdapter(Context context, List<SearchOrderItem> searchOrderItems) {
    this.context = context;
    this.searchOrderItems = searchOrderItems;
  }

  @Override public int getCount() {
    return searchOrderItems.size();
  }

  @Override public SearchOrderItem getItem(int position) {
    return searchOrderItems.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    TextView text;
    if (convertView != null) {
      text = (TextView) convertView;
    } else {
      text = (TextView) LayoutInflater.from(context)
          .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
    }
    text.setTextColor(Color.BLACK);
    text.setText(searchOrderItems.get(position).getName());
    return text;
  }
}
