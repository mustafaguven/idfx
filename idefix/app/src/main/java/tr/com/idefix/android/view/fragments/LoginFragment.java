package tr.com.idefix.android.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import javax.inject.Inject;
import tr.com.idefix.android.R;
import tr.com.idefix.android.internal.di.components.LoginComponent;
import tr.com.idefix.android.presenter.ILoginPresenter;
import tr.com.idefix.android.view.LoginView;
import tr.com.idefix.android.view.activities.SignupActivity;
import tr.com.idefix.android.view.listeners.OnForgetPasswordListener;

public class LoginFragment extends BaseFragment implements LoginView {

  @Inject ILoginPresenter loginPresenter;

  @Bind(R.id.email_edit_text) AppCompatEditText email;
  @Bind(R.id.password_edit_text) AppCompatEditText password;
  @Bind(R.id.remember_me) AppCompatCheckBox remember_me;
  @Bind(R.id.forget_password) AppCompatTextView forget_password;
  @Bind(R.id.login) AppCompatButton login;

  @Bind(R.id.passLayout) TextInputLayout passLayout;

  @Bind(R.id.emailLayout) TextInputLayout emailLayout;
  OnForgetPasswordListener onForgetPasswordListener;

  public LoginFragment() {
  }

  @OnClick(R.id.lblBeMember) void beAMember() {
    startActivity(new Intent(getActivity(), SignupActivity.class));
    getActivity().finish();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
  ) {
    View view = inflater.inflate(R.layout.fragment_login, container, false);
    ButterKnife.bind(this, view);
    ////////////////////////////////////////////////////////////////////////////////////////////
    passLayout.setErrorEnabled(true);
    emailLayout.setErrorEnabled(true);

    SpannableString content = new SpannableString(forget_password.getText());
    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
    forget_password.setText(content);
    return view;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getComponent(LoginComponent.class).inject(this);
    loginPresenter.setView(this);
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);

    if (activity instanceof OnForgetPasswordListener) {
      onForgetPasswordListener = (OnForgetPasswordListener) activity;
    }
  }

  @Override public void onResume() {
    super.onResume();
    loginPresenter.resume();
  }

  @Override public void onPause() {
    loginPresenter.pause();
    super.onPause();
  }

  @Override public void onDestroy() {
    loginPresenter.destroy();
    super.onDestroy();
  }

  @OnClick(R.id.login) void login() {

    deviceUtils.closeKeyboard(getActivity());

    if (TextUtils.isEmpty(email.getText().toString())
        || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
      email.setError(getString(R.string.email));

      emailLayout.setError("Lütfen e-mailinizi girin");
      return;
    }
    if (TextUtils.isEmpty(password.getText().toString())) {
      password.setError(getString(R.string.password));

      passLayout.setError("Lütfen şifrenizi girin");
      return;
    }

    login(email.getText().toString(), password.getText().toString(), remember_me.isChecked());
  }

  @OnClick(R.id.forget_password) void forgetPassword() {
    if (onForgetPasswordListener != null) {
      onForgetPasswordListener.onOpenForgetPassword();
    }
  }

  @Override public void onError(String s) {
    Snackbar.make(getView(), s, Snackbar.LENGTH_LONG).show();
  }

  @Override public void loggedin() {
    getActivity().finish();
  }

  @Override public void setBasketCount(int count) {

  }

  public void login(String username, String password, boolean rememberme) {
    loginPresenter.login(username, password, rememberme);
  }
}
