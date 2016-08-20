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
import tr.com.idefix.domain.City;

/**
 * Created by mustafaguven on 15.10.2015.
 */
public class CityAdapter extends BaseAdapter implements SpinnerAdapter {

  Context context;
  List<City> cities;

  public CityAdapter(Context context, List<City> cities) {
    this.context = context;
    this.cities = cities;
  }

  @Override public int getCount() {
    return cities.size();
  }

  @Override public City getItem(int position) {
    return cities.get(position);
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
    text.setText(cities.get(position).text());
    return text;
  }
}
