package tr.com.idefix.domain.internal.di.components;

import android.content.Context;
import dagger.Component;
import javax.inject.Singleton;
import tr.com.idefix.domain.BusProvider;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.internal.di.modules.ApplicationModule;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class) public interface ApplicationComponent {

  //Exposed to sub-graphs.
  Context context();

  DomainContext domainContext();

  BusProvider busProvider();
}
