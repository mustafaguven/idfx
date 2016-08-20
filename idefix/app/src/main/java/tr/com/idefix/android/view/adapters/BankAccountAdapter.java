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
import tr.com.idefix.data.entity.BankEntity;

public class BankAccountAdapter extends BaseAdapter implements SpinnerAdapter {

  Context context;
  List<BankEntity> bankEntityList;

  public BankAccountAdapter(Context context, List<BankEntity> list) {
    this.context = context;
    this.bankEntityList = list;
  }

  @Override public int getCount() {
    return bankEntityList.size();
  }

  @Override public BankEntity getItem(int position) {
    return bankEntityList.get(position);
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
    text.setText(bankEntityList.get(position).Text());
    return text;
  }
}
