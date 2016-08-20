package tr.com.idefix.android.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import javax.inject.Inject;
import mu.comon.utils.DeviceUtils;
import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.internal.di.HasComponent;
import tr.com.idefix.android.view.activities.BaseActivity;

/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment {

  @Inject DeviceUtils deviceUtils;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);

    deviceUtils = ((BaseActivity) getActivity()).getApplicationComponent().deviceUtils();
  }

  /**
   * Gets a component for dependency injection by its type.
   */
  @SuppressWarnings("unchecked") protected <C> C getComponent(Class<C> componentType) {
    return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
  }

  public void showProgress() {
    if (getActivity() != null && !getActivity().isFinishing()) {
      ((BaseActivity) getActivity()).showProgress();
    }
  }

  public void hideProgress() {
    if (getActivity() != null && !getActivity().isFinishing()) {
      ((BaseActivity) getActivity()).hideProgress();
    }
  }

  public boolean isTablet() {
    if (getActivity() != null) {
      return ((ApplicationController) (getActivity().getApplication())).isTablet();
    } else {
      return false;
    }
  }
}
