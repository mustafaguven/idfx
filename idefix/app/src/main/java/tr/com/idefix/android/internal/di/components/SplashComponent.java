package tr.com.idefix.android.internal.di.components;

import dagger.Component;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.internal.di.modules.ActivityModule;
import tr.com.idefix.android.internal.di.modules.SplashModule;
import tr.com.idefix.android.view.activities.SplashActivity;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity @Component(dependencies = ApplicationComponent.class, modules = {
    ActivityModule.class, SplashModule.class
}) public interface SplashComponent extends ActivityComponent {

  void inject(SplashActivity splashActivity);
}
