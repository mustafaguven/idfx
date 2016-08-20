package tr.com.idefix.android.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import javax.inject.Inject;
import tr.com.idefix.android.R;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.internal.di.components.SignupActivityComponent;
import tr.com.idefix.android.presenter.ISignupPresenter;
import tr.com.idefix.android.view.SignupView;
import tr.com.idefix.android.view.activities.LoginActivity;
import tr.com.idefix.android.view.activities.SozlesmeActivity;
import tr.com.idefix.data.entity.SaveNewCustomerRequestEntity;
import tr.com.idefix.data.entity.SaveNewCustomerResponseEntity;

/**
 * A placeholder fragment containing a simple view.
 */
public class SignupFragment extends BaseFragment implements SignupView {

  @Inject ISignupPresenter presenter;

  @Bind(R.id.txtMail) EditText txtMail;

  @Bind(R.id.txtName) EditText txtName;

  @Bind(R.id.txtSurname) EditText txtSurname;

  @Bind(R.id.ddlSex) Spinner ddlSex;

  @Bind(R.id.chCampaign) CheckBox chCampaign;

  @Bind(R.id.txtPassword) EditText txtPassword;

  @Bind(R.id.txtPasswordConfirm) EditText txtPasswordConfirm;

  @Bind(R.id.chAgreementApprove) CheckBox chAgreementApprove;

  @Bind(R.id.btnBecomeAMember) Button btnBecomeAMember;

  @OnClick(R.id.btnBecomeAMember) void save() {
    SaveNewCustomerRequestEntity request = new SaveNewCustomerRequestEntity();
    String gender = ddlSex.getSelectedItemPosition() <= 1 ? "M" : "F";
    boolean valid = true;
    if (TextUtils.isEmpty(txtName.getText())) {
      txtName.setError(getString(R.string.blank_field_warning));
      valid = false;
    }
    if (TextUtils.isEmpty(txtSurname.getText())) {
      txtSurname.setError(getString(R.string.blank_field_warning));
      valid = false;
    }
    if (TextUtils.isEmpty(txtMail.getText())) {
      txtMail.setError(getString(R.string.blank_field_warning));
      valid = false;
    }
    if (TextUtils.isEmpty(txtPassword.getText())) {
      txtPassword.setError(getString(R.string.blank_field_warning));
      valid = false;
    }
    if (TextUtils.isEmpty(txtPasswordConfirm.getText())) {
      txtPasswordConfirm.setError(getString(R.string.blank_field_warning));
      valid = false;
    }
    if (!txtPasswordConfirm.getText().toString().contentEquals(txtPassword.getText().toString())) {
      txtPasswordConfirm.setError(getString(R.string.passwords_dont_match));
      txtPassword.setError(getString(R.string.passwords_dont_match));
      valid = false;
    }

    if (valid && !chAgreementApprove.isChecked()) {
      chAgreementApprove.setError(getString(R.string.approve_aggerement));
      Toast.makeText(getActivity(), getString(R.string.approve_aggerement), Toast.LENGTH_SHORT)
          .show();
      valid = false;
    }

    if (valid) {
      request.setFirstName(txtName.getText().toString());
      request.setLastName(txtSurname.getText().toString());
      request.setGender(gender);
      request.setEMail(txtMail.getText().toString());
      request.setAnnouncements(chCampaign.isChecked() ? "on" : "off");
      request.setPassword(txtPassword.getText().toString());
      request.setPassword2(txtPasswordConfirm.getText().toString());
      request.setFacebookID("");
      request.setCcs("");
      presenter.saveNewCustomer(request);
    }
  }

  @OnClick(R.id.lblAggreementDescription) void aggrementDescription() {
    startActivity(new Intent(getActivity(), SozlesmeActivity.class).putExtra(Keys.TITLE,
        getString(R.string.login_screen)));
  }

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
  ) {
    View view = inflater.inflate(R.layout.fragment_signup, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    getComponent(SignupActivityComponent.class).injectSignupFragment(this);

    populateSex("");
    presenter.setView(this);
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

  @Override
  public void onNewCustomerSaved(SaveNewCustomerResponseEntity saveNewCustomerResponseEntity) {
    if (!saveNewCustomerResponseEntity.getIsSuccess()) {
      Toast.makeText(getActivity(), saveNewCustomerResponseEntity.getMessage(), Toast.LENGTH_SHORT)
          .show();
    } else {
      Toast.makeText(getActivity(), getString(R.string.signup_succesfull), Toast.LENGTH_SHORT)
          .show();
      startActivity(new Intent(getActivity(), LoginActivity.class).putExtra(Keys.USERNAME,
          txtMail.getText().toString())
          .putExtra(Keys.PASSWORD, txtPassword.getText().toString()));
      getActivity().finish();
    }
  }

  @Override public void setBasketCount(int count) {
    //TODO: divide into interfaces activityview and fragmentview
  }
}
