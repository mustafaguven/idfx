package tr.com.idefix.android.view.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import mu.comon.utils.DateTimeUtils;
import mu.comon.utils.FormatUtils;
import tr.com.idefix.android.R;
import tr.com.idefix.android.components.SwitchButton;
import tr.com.idefix.android.internal.di.components.UserProfileActivityComponent;
import tr.com.idefix.android.presenter.IMyInformationPresenter;
import tr.com.idefix.android.view.MyInformationView;
import tr.com.idefix.android.view.adapters.CityAdapter;
import tr.com.idefix.android.view.adapters.CountryAdapter;
import tr.com.idefix.android.view.listeners.OnLogoutListener;
import tr.com.idefix.data.entity.CustomerInfoRequestEntity;
import tr.com.idefix.domain.City;
import tr.com.idefix.domain.Country;
import tr.com.idefix.domain.CustomerInfo;

/**
 * Created by mustafaguven on 13.10.2015.
 */
public class MyInformationFragment extends BaseFragment implements MyInformationView {

  private final int TRANSATION_DURATION = 500;
  @Inject IMyInformationPresenter presenter;
  @Bind(R.id.switchButton) SwitchButton switchButton;
  @Bind(R.id.rlBilgilerimContainer) RelativeLayout rlBilgilerimContainer;
  @Bind(R.id.rlSifreContainer) RelativeLayout rlSifreContainer;
  @Bind(R.id.txtMail) EditText txtMail;
  @Bind(R.id.txtName) EditText txtName;
  @Bind(R.id.txtSurname) EditText txtSurname;
  @Bind(R.id.ddlSex) Spinner ddlSex;
  @Bind(R.id.txtBirthDate) TextView txtBirthDate;
  @Bind(R.id.txtHomePhone) EditText txtHomePhone;
  public TextWatcher textWatcherHome = new TextWatcher() {
    private String current = "";
    private String phoneDummy = "xxxxxxxxxx";//getString(R.string.phone_dummy);

    @Override public void onTextChanged(
        CharSequence s, int start, int before, int count
    ) {

      if (!s.toString().equals(current)) {
        String clean = s.toString().replaceAll("[^\\d]", "");
        String cleanC = current.replaceAll("[^\\d]", "");

        Log.e("sel", clean);

        int cl = clean.length();
        int sel = cl;

        for (int i = 3; i <= cl && i < 8; i += 2) {

          //Log.e("sel", String.valueOf(sel) + " " + String.valueOf(clean));
          sel++;
        }
        if (clean.equals(cleanC)) sel--;

        if (clean.length() < 10) {
          clean = clean + phoneDummy.substring(clean.length());
        }
        clean = String.format("%s-%s-%s-%s", clean.substring(0, 3), clean.substring(3, 6),
            clean.substring(6, 8), clean.substring(8, 10));

        sel = sel < 0 ? 0 : sel;
        current = clean;

        txtHomePhone.setText(current);
        txtHomePhone.setSelection(sel < current.length() ? sel : current.length());
        //Log.e("sel", String.valueOf(sel) + " " + String.valueOf(current.length()));
      }
    }

    @Override public void afterTextChanged(Editable s) {

    }

    @Override public void beforeTextChanged(
        CharSequence s, int start, int count, int after
    ) {

    }
  };
  @Bind(R.id.txtGSMPhone) EditText txtGSMPhone;
  public TextWatcher textWatcherGSM = new TextWatcher() {
    private String current = "";
    private String phoneDummy = "xxxxxxxxxx";//getString(R.string.phone_dummy);

    @Override public void onTextChanged(
        CharSequence s, int start, int before, int count
    ) {

      if (!s.toString().equals(current)) {
        String clean = s.toString().replaceAll("[^\\d]", "");
        String cleanC = current.replaceAll("[^\\d]", "");

        Log.e("sel", clean);

        int cl = clean.length();
        int sel = cl;

        for (int i = 3; i <= cl && i < 8; i += 2) {

          //Log.e("sel", String.valueOf(sel) + " " + String.valueOf(clean));
          sel++;
        }
        if (clean.equals(cleanC)) sel--;

        if (clean.length() < 10) {
          clean = clean + phoneDummy.substring(clean.length());
        }
        clean = String.format("%s-%s-%s-%s", clean.substring(0, 3), clean.substring(3, 6),
            clean.substring(6, 8), clean.substring(8, 10));

        sel = sel < 0 ? 0 : sel;
        current = clean;

        txtGSMPhone.setText(current);
        txtGSMPhone.setSelection(sel < current.length() ? sel : current.length());
        //Log.e("sel", String.valueOf(sel) + " " + String.valueOf(current.length()));
      }
    }

    @Override public void afterTextChanged(Editable s) {

    }

    @Override public void beforeTextChanged(
        CharSequence s, int start, int count, int after
    ) {

    }
  };
  @Bind(R.id.ddlCountry) Spinner ddlCountry;
  @Bind(R.id.ddlCity) Spinner ddlCity;
  @Bind(R.id.chCampaign) CheckBox chCampaign;
  @Bind(R.id.txtCurrentPassword) EditText txtCurrentPassword;
  @Bind(R.id.txtNewPassword) EditText txtNewPassword;
  @Bind(R.id.txtConfirmNewPassword) EditText txtConfirmNewPassword;
  String phoneDummy = "xxx-xxx-xx-xx";
  boolean phoneMaskIsOk = true;
  Calendar mCalendar = DateTimeUtils.getDefaultCalendar();
  CustomerInfo mCustomerInfo;
  private OnLogoutListener onLogoutListener;
  private int countryChangeCounter = 0;

  @OnClick(R.id.btnUpdatePassword) void changePassword() {
    presenter.changePassword(txtCurrentPassword.getText().toString(),
        txtNewPassword.getText().toString(), txtConfirmNewPassword.getText().toString());
  }

  @OnClick(R.id.btnSaveContact) void changeCustomerInfo() {
    Country country = (Country) ddlCountry.getSelectedItem();
    String countryId = country.id();
    City city = (City) ddlCity.getSelectedItem();
    String cityId = city.id();
    String gender = ddlSex.getSelectedItemPosition() <= 1 ? "M" : "F";

    CustomerInfoRequestEntity requestEntity = new CustomerInfoRequestEntity();
    requestEntity.firstName(txtName.getText().toString())
        .lastName(txtSurname.getText().toString())
        .gender(gender)
        .birthday_month(txtBirthDate.getText().toString().contentEquals("Seçiniz") ? "0"
            : String.valueOf(mCalendar.get(Calendar.MONTH) + 1))
        .birthday_day(txtBirthDate.getText().toString().contentEquals("Seçiniz") ? "0"
            : String.valueOf(mCalendar.get(Calendar.DAY_OF_MONTH)))
        .birthday_year(txtBirthDate.getText().toString().contentEquals("Seçiniz") ? "1900"
            : String.valueOf(mCalendar.get(Calendar.YEAR)))
        .eMail(txtMail.getText().toString())
        .announcements(chCampaign.isChecked() ? "on" : "off")
/*                .phoneNoHome(txtHomePhone.getText().toString())
                .phoneNoCell(txtGSMPhone.getText().toString())*/
        .cityFieldID(cityId)
        .countryFieldID(countryId);

    requestEntity = addPhoneNumbersToRequestByCountry(requestEntity);
    if (phoneMaskIsOk) presenter.changeInfo(requestEntity);
  }

  private CustomerInfoRequestEntity addPhoneNumbersToRequestByCountry(
      CustomerInfoRequestEntity requestEntity
  ) {
    phoneMaskIsOk = true;
    String phoneNumber = txtHomePhone.getText().toString();
    String gsmNumber = txtGSMPhone.getText().toString();
    if (requestEntity.countryFieldID().contentEquals("77")) {
      // TÜRKİYE SEÇİLİYSE EK OLARAK KONTROL ET
      phoneNumber = FormatUtils.isValidPhone(phoneNumber);
      gsmNumber = FormatUtils.isValidPhone(gsmNumber);
      if (phoneNumber != null) {
        requestEntity.phoneNoHome(phoneNumber);
        txtHomePhone.setError(null);
      } else {
        txtHomePhone.setError("Ev telefonu doğru belirtilmedi");
        phoneMaskIsOk = false;
      }
      if (gsmNumber != null) {
        requestEntity.phoneNoCell(gsmNumber);
        txtGSMPhone.setError(null);
      } else {
        txtGSMPhone.setError("Cep telefonu doğru belirtilmedi");
        phoneMaskIsOk = false;
      }
    } else {
      //TÜRKİYE HARİCİ BİR YER İSE
      requestEntity.phoneNoHome(phoneNumber);
      requestEntity.phoneNoCell(gsmNumber);
    }
    return requestEntity;
  }

  private void setPhoneBox(String countryId, EditText phoneBox, TextWatcher tw) {
    if (countryId.contentEquals("77")) {
      phoneBox.setHint(phoneDummy);
      phoneBox.addTextChangedListener(tw);
      String number = phoneBox.getText().toString();
      if (number.length() < 10) {
        number = (number + phoneDummy.substring(number.length()));
      }
      phoneBox.setText(number);
    } else {
      phoneBox.setHint("");
      phoneBox.removeTextChangedListener(tw);
      phoneBox.setText(phoneBox.getText().toString().replaceAll("[^\\d]", ""));
    }
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @OnClick(R.id.txtBirthDate) void setBirthDate() {
    if (txtBirthDate.getText().toString().contentEquals("Seçiniz")) {
      mCalendar.setTimeInMillis(new Date().getTime());
    }
    DatePickerDialog datePicker = new DatePickerDialog(
        getActivity(), new DatePickerDialog.OnDateSetListener() {
      @Override public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, monthOfYear);
        mCalendar.set(Calendar.DATE, dayOfMonth);
        txtBirthDate.setText(DateTimeUtils.getFormattedDate(mCalendar));
      }
    }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DATE));

    datePicker.show();
  }

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
  ) {
    View view = inflater.inflate(R.layout.fragment_my_information, container, false);
    ButterKnife.bind(this, view);

    switchButton.setTitles(getResources().getStringArray(R.array.my_informations_switchbutton));
    switchButton.setOnSwitchListener(isFirstItemSelected -> {

      rlBilgilerimContainer.setAlpha(0f);
      rlSifreContainer.setAlpha(0f);
      if (isFirstItemSelected) {
        rlBilgilerimContainer.animate()
            .setDuration(TRANSATION_DURATION)
            .alpha(1f)
            .setInterpolator(new DecelerateInterpolator(2))
            .start();
      } else {
        rlSifreContainer.animate()
            .setDuration(TRANSATION_DURATION)
            .alpha(1f)
            .setInterpolator(new DecelerateInterpolator(2))
            .start();
      }

      rlBilgilerimContainer.setVisibility(isFirstItemSelected ? View.VISIBLE : View.GONE);
      rlSifreContainer.setVisibility(isFirstItemSelected ? View.GONE : View.VISIBLE);
    });

    return view;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override public void onFetchUserInformation(CustomerInfo customerInfo) {
    this.mCustomerInfo = customerInfo;
    presenter.getCountries();
    txtMail.setText(customerInfo.email());
    txtName.setText(customerInfo.firstName());
    txtSurname.setText(customerInfo.lastName());
    populateSex(customerInfo.gender());
    mCalendar.set(
        customerInfo.dateofbirthyear(), customerInfo.dateofbirthmonth(),
        customerInfo.dateofbirthday());
    if (mCalendar.get(Calendar.YEAR) > 1900) {
      txtBirthDate.setText(DateTimeUtils.getFormattedDate(mCalendar));
    } else {
      txtBirthDate.setText("Seçiniz");
    }
    chCampaign.setChecked(customerInfo.newsletter());
    if (!TextUtils.isEmpty(customerInfo.phone()) && customerInfo.countryId() == 77) {
      String home = customerInfo.phone().replaceAll("\\D+", "").trim(); //getonly numbers
      home += getString(R.string.phone_dummy_temp).substring(home.length());
      txtHomePhone.setText(home);
    } else {
      txtHomePhone.setText(customerInfo.phone());
    }
    if (!TextUtils.isEmpty(customerInfo.gsm()) && customerInfo.countryId() == 77) {
      String gsm = customerInfo.gsm().replaceAll("\\D+", "").trim(); //getonly numbers
      gsm += customerInfo.gsm() + getString(R.string.phone_dummy_temp).substring(gsm.length());
      txtGSMPhone.setText(gsm);
    } else {
      txtGSMPhone.setText(customerInfo.gsm());
    }
  }

  @Override public void onCustomerInfoUpdated() {
    Snackbar.make(
        getView(), R.string.your_informations_is_changed_successfully, Snackbar.LENGTH_SHORT)
        .show();
  }

  @Override public void onPasswordChanged(String message) {
    txtCurrentPassword.setText("");
    txtNewPassword.setText("");
    txtConfirmNewPassword.setText("");
    Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
  }

  private void populateSex(String selectedGender) {
    ArrayAdapter<String> spinnerArrayAdapter =
        new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
            getResources().getStringArray(R.array.sex_list));
    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    ddlSex.setAdapter(spinnerArrayAdapter);
    if (!TextUtils.isEmpty(selectedGender)) {
      if (String.valueOf(selectedGender).toLowerCase().contentEquals("f")) {
        ddlSex.setSelection(2);
      } else if (String.valueOf(selectedGender).toLowerCase().contentEquals("m")) {
        ddlSex.setSelection(1);
      }
    }
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    getComponent(UserProfileActivityComponent.class).injectMyInformationFragment(this);

    presenter.setView(this);
    presenter.getUserInformation();
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);

    if (activity instanceof OnLogoutListener) {
      onLogoutListener = (OnLogoutListener) activity;
    }
  }

  @Override public void onFetchCountries(List<Country> countryList) {
    CountryAdapter adapter = new CountryAdapter(getActivity(), countryList);
    ddlCountry.setAdapter(adapter);
    int position = 0;
    for (int i = 0; i < countryList.size(); i++) {
      Country country = countryList.get(i);
      if (String.valueOf(country.id()).contentEquals(String.valueOf(mCustomerInfo.countryId()))) {
        position = i;
        break;
      }
    }
    ddlCountry.setSelection(position);
    ddlCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        countryChangeCounter++;
        Country c = adapter.getItem(position);
        presenter.getCityByCountryId(Integer.parseInt(c.id()));
        setPhoneBox(c.id(), txtHomePhone, textWatcherHome);
        setPhoneBox(c.id(), txtGSMPhone, textWatcherGSM);
      }

      @Override public void onNothingSelected(AdapterView<?> parent) {
      }
    });
  }

  @Override public void onFetchCitiesByCountryId(List<City> cities) {
    CityAdapter adapter = new CityAdapter(getActivity(), cities);
    ddlCity.setAdapter(adapter);
    int position = 0;
    for (int i = 0; i < cities.size(); i++) {
      City city = cities.get(i);
      if (String.valueOf(city.id()).contentEquals(mCustomerInfo.cityId())
          && countryChangeCounter <= 1) {
        position = i;
        break;
      }
    }
    ddlCity.setSelection(position);
  }

  @Override public void onSave() {

  }

  @OnClick(R.id.btnLogout) void doLogout() {
    presenter.onLogout();
  }

  @Override public void onLogout() {
    if (onLogoutListener != null) {
      onLogoutListener.onLogout();
    } else {
      getActivity().finish();
    }
  }

  @Override public void onError(Throwable e) {
    Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
  }

  @Override public void setBasketCount(int count) {
    //TODO: divide into interfaces activityview and fragmentview
  }
}
