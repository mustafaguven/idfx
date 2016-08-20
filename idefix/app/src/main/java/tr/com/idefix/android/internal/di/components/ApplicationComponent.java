package tr.com.idefix.android.internal.di.components;

import android.content.Context;
import dagger.Component;
import javax.inject.Singleton;
import mu.comon.utils.DeviceUtils;
import tr.com.idefix.android.internal.di.modules.ApplicationModule;
import tr.com.idefix.android.view.activities.BaseActivity;
//import tr.com.dr.domain.repository.LoginRepository;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class) public interface ApplicationComponent {

  void inject(BaseActivity baseActivity);

  //Exposed to sub-graphs.
  Context context();

  DeviceUtils deviceUtils();
  //    LoginRepository loginRepository();
}
