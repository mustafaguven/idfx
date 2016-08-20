package tr.com.idefix.domain.internal.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import tr.com.idefix.domain.BusProvider;
import tr.com.idefix.domain.DomainApplication;
import tr.com.idefix.domain.DomainContext;

/**
 * Created by utkan on 03/09/15.
 */
@Module public class ApplicationModule {

  private final DomainApplication application;

  public ApplicationModule(DomainApplication application) {
    this.application = application;
  }

  @Provides @Singleton Context provideApplicationContext() {
    return this.application.getApplicationContext();
  }

  @Provides @Singleton SharedPreferences provideHelpPreferenceManager() {
    return application.getSharedPreferences("D_and_R", Context.MODE_PRIVATE);
  }

  @Provides @Singleton DomainContext provideDomainContext(Context context) {
    return new DomainContext(context);
  }

  @Provides @Singleton BusProvider provideBusProvider() {
    return new BusProvider();
  }
}
