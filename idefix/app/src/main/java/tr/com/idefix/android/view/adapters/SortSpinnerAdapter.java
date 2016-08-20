package tr.com.idefix.android.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import mu.comon.utils.DeviceUtils;
import tr.com.idefix.domain.enums.SortEnums;

/**
 * Created by utkan on 30/10/15.
 */
public class SortSpinnerAdapter extends ArrayAdapter<SortEnums> {

  private Context context;
  private SortEnums[] values;

  public SortSpinnerAdapter(
      Context context, int textViewResourceId, SortEnums[] values
  ) {
    super(context, textViewResourceId, values);
    this.context = context;
    this.values = values;
  }

  public int getCount() {
    return values.length;
  }

  public SortEnums getItem(int position) {
    return values[position];
  }

  public long getItemId(int position) {
    return position;
  }

  // And the "magic" goes here
  // This is for the "passive" state of the spinner
  @Override public View getView(int position, View convertView, ViewGroup parent) {
    // I created a dynamic TextView here, but you can reference your own  custom layout for each
    // spinner item
    TextView label = new TextView(context);
    label.setTextColor(Color.BLACK);
    LinearLayout.LayoutParams ll =
        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    ll.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
    ll.leftMargin = (int) new DeviceUtils().dpToPx((Activity) context, 10);
    label.setPadding((int) new DeviceUtils().dpToPx((Activity) context, 10), 0, 0, 0);
    label.setLayoutParams(ll);
    // Then you can get the current item using the values array (Users array) and the current
    // position
    // You can NOW reference each method you has created in your bean object (User class)
    label.setText(values[position].getText());

    // And finally return your dynamic (or custom) view for each spinner item
    return label;
  }

  // And here is when the "chooser" is popped up
  // Normally is the same view, but you can customize it if you want
  @Override public View getDropDownView(
      int position, View convertView, ViewGroup parent
  ) {
    TextView label = new TextView(context);
    label.setTextColor(Color.BLACK);
    label.setText(values[position].getText());
    label.setPadding((int) new DeviceUtils().dpToPx((Activity) context, 15), 0, 0, 0);
    AbsListView.LayoutParams params =
        new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            (int) new DeviceUtils().dpToPx((Activity) context, 30));
    label.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
    label.setLayoutParams(params);

    return label;
  }
}
