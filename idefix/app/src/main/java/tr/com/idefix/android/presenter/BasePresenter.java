package tr.com.idefix.android.presenter;

import com.squareup.otto.Bus;
import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.internal.di.components.ApplicationComponent;

/**
 * Created on 11.3.15.
 */
public abstract class BasePresenter {

  protected final DomainContext domainContext;
  protected final ApplicationComponent component;
  protected final Bus bus;

  public BasePresenter() {

    component = ApplicationController.getInstance().getDomainApplicationComponent();

    domainContext = component.domainContext();

    bus = component.busProvider().getBus();
  }

  public Bus getBus() {
    return bus;
  }
}
