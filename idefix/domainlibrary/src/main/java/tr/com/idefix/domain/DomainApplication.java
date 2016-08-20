package tr.com.idefix.domain;

import android.app.Application;
import javax.inject.Inject;
import tr.com.idefix.domain.internal.di.components.ApplicationComponent;
import tr.com.idefix.domain.internal.di.components.DaggerApplicationComponent;
import tr.com.idefix.domain.internal.di.modules.ApplicationModule;

/**
 * Created by utkan on 02/09/15.
 */
public class DomainApplication extends Application {

  private static DomainApplication instance = null;
  @Inject DomainContext domainContext;
  @Inject BusProvider busProvider;
  private ApplicationComponent domainApplicationComponent;

  protected DomainApplication() {
    // Exists only to defeat instantiation.
  }

  public static DomainApplication getInstance() {
    return instance;
  }

  @Override public void onCreate() {
    super.onCreate();

    instance = this;

    this.domainApplicationComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
  }

  public ApplicationComponent getDomainApplicationComponent() {
    return this.domainApplicationComponent;
  }
}
