package tr.com.idefix.android.view.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import mu.comon.utils.DateTimeUtils;
import tr.com.idefix.android.R;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.internal.di.components.DaggerNotifyPaymentActivityComponent;
import tr.com.idefix.android.internal.di.components.NotifyPaymentActivityComponent;
import tr.com.idefix.android.presenter.INotifyPaymentPresenter;
import tr.com.idefix.android.view.INotifyPaymentView;
import tr.com.idefix.android.view.adapters.BankAccountAdapter;
import tr.com.idefix.data.entity.BankEntity;
import tr.com.idefix.data.entity.SendPaymentInfoForOrderResponse;

public class NotifyPaymentActivity extends BaseActivity implements INotifyPaymentView {

  @Inject INotifyPaymentPresenter presenter;
  NotifyPaymentActivityComponent notifyPaymentActivityComponent;

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.txtDate) TextView txtDate;
  @Bind(R.id.txtNote) TextView txtNote;
  @Bind(R.id.ddlSube) AppCompatSpinner ddlSube;
  String orderCode;
  Calendar mCalendar = Calendar.getInstance();

  @OnClick(R.id.txtDate) void onDateClicked() {
    if (txtDate.getText().toString().contentEquals("Seçiniz")) {
      mCalendar.setTimeInMillis(new Date().getTime());
    }
    DatePickerDialog datePicker = new DatePickerDialog(
        this, new DatePickerDialog.OnDateSetListener() {
      @Override public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, monthOfYear);
        mCalendar.set(Calendar.DATE, dayOfMonth);
        txtDate.setText(DateTimeUtils.getFormattedDate(mCalendar));
      }
    }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DATE));

    datePicker.show();
  }

  @OnClick(R.id.btnSend) void onSendClicked() {
    if (!TextUtils.isEmpty(txtDate.getText()) && !txtDate.getText()
        .toString()
        .contentEquals("Seçiniz")) {
      if (ddlSube.getSelectedItemPosition() > 0) {
        sendNotifyPayment();
      } else {
        Toast.makeText(this, "Şube seçiniz", Toast.LENGTH_SHORT).show();
      }
    } else {
      Toast.makeText(this, "Tarih seçiniz", Toast.LENGTH_SHORT).show();
    }
  }

  private void sendNotifyPayment() {
    BankEntity bank = (BankEntity) ddlSube.getSelectedItem();
    presenter.sendNotifyPayment(
        orderCode, bank.Value(), txtDate.getText().toString(), txtNote.getText().toString());
  }

  public static Intent createLauncher(Activity activity, String orderCode) {
    return new Intent(activity, NotifyPaymentActivity.class).putExtra(Keys.ORDER_CODE, orderCode);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.notify_payment);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.bg_red_toolbar_start));
    toolbar.setNavigationIcon(R.drawable.ic_white_back);
    toolbar.setNavigationOnClickListener(v -> finish());
    notifyPaymentActivityComponent = DaggerNotifyPaymentActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    notifyPaymentActivityComponent.inject(this);
    orderCode = getIntent().getStringExtra(Keys.ORDER_CODE);
    presenter.setView(this);
    presenter.getAvailableBankAccounts();
  }

  @Override public void setBasketCount(int count) {

  }

  @Override public void fetchBankAccounts(List<BankEntity> bankEntities) {
    BankAccountAdapter adapter = new BankAccountAdapter(this, bankEntities);
    ddlSube.setAdapter(adapter);
  }

  @Override public void sentPaymentInfoForOrderSuccesfully(
      SendPaymentInfoForOrderResponse response
  ) {
    Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show();
    finish();
  }
}
