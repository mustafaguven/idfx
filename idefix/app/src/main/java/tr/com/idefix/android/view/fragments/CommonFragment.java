package tr.com.idefix.android.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.List;
import javax.inject.Inject;
import tr.com.idefix.android.R;
import tr.com.idefix.android.internal.di.components.CommonComponent;
import tr.com.idefix.android.presenter.ICommonPresenter;
import tr.com.idefix.android.view.CommonView;
import tr.com.idefix.domain.Country;

/**
 * A placeholder fragment containing a simple view.
 */
public class CommonFragment extends BaseFragment implements CommonView {

  List<Country> countries;

  @Inject ICommonPresenter commonPresenter;

  public CommonFragment() {
  }

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
  ) {
    View view = inflater.inflate(R.layout.fragment_common, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    getComponent(CommonComponent.class).inject(this);

    commonPresenter.setView(this);
  }

  @Override public void onResume() {
    super.onResume();
    commonPresenter.resume();
  }

  @Override public void onPause() {
    commonPresenter.pause();
    super.onPause();
  }

  @Override public void onDestroy() {
    commonPresenter.destroy();
    super.onDestroy();
  }

  @OnClick(R.id.GetAvailableCountries) void getAvailableCountries() {
    commonPresenter.getAvailableCountries();
  }

  @Override public void setCountries(List<Country> countries) {
    this.countries = countries;
  }

  @Override public void setBasketCount(int count) {

  }
}
