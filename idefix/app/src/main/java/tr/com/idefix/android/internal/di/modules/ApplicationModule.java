package tr.com.idefix.android.internal.di.modules;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import mu.comon.utils.DeviceUtils;
import mu.comon.utils.FileUtils;
import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.navigation.Navigator;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module public class ApplicationModule
    extends tr.com.idefix.domain.internal.di.modules.ApplicationModule {

  private final ApplicationController application;

  public ApplicationModule(ApplicationController application) {
    super(application);
    this.application = application;
  }

  @Provides @Singleton Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton Navigator provideNavigator() {
    return new Navigator();
  }

  @Provides @Singleton DeviceUtils provideDeviceUtils() {
    return new DeviceUtils();
  }

  @Provides @Singleton FileUtils provideFileUtils(Context context) {
    return new FileUtils(context);
  }
}
