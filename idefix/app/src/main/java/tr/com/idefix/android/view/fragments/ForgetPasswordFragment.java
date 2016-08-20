package tr.com.idefix.android.view.fragments;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import javax.inject.Inject;
import mu.comon.utils.FormatUtils;
import tr.com.idefix.android.R;
import tr.com.idefix.android.internal.di.components.ForgetPasswordActivityComponent;
import tr.com.idefix.android.presenter.IForgetPasswordPresenter;
import tr.com.idefix.android.view.ForgetPasswordView;
import tr.com.idefix.data.entity.PasswordRecoveryEntity;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForgetPasswordFragment extends BaseFragment implements ForgetPasswordView {

  @Inject IForgetPasswordPresenter presenter;

  @Bind(R.id.lnMain) LinearLayout lnMain;

  @Bind(R.id.txtEmail) AppCompatEditText txtEmail;

  @OnClick(R.id.btnPasswordRecovery) void passwordRecovery() {
    String mail = txtEmail.getText().toString();
    if (FormatUtils.isEmailValid(mail)) {
      presenter.passwordRecovery(mail);
    } else {
      Toast.makeText(getActivity(), R.string.invalid_email, Toast.LENGTH_SHORT).show();
    }
  }

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
  ) {
    View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    getComponent(ForgetPasswordActivityComponent.class).injectForgetPasswordFragment(this);

    presenter.setView(this);
  }

  @Override public void onMailSent(PasswordRecoveryEntity passwordRecoveryEntity) {
    Toast.makeText(getActivity(), passwordRecoveryEntity.getResult(), Toast.LENGTH_SHORT).show();
  }

  @Override public void setBasketCount(int count) {

  }
}
