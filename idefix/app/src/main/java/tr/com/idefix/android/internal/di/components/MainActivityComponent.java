package tr.com.idefix.android.internal.di.components;

import dagger.Component;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.internal.di.modules.ActivityModule;
import tr.com.idefix.android.internal.di.modules.MainActivityModule;
import tr.com.idefix.android.view.activities.MainActivity;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity @Component(
    dependencies = ApplicationComponent.class,
    modules = {
        ActivityModule.class, MainActivityModule.class
    }) public interface MainActivityComponent extends ActivityComponent {

  void inject(MainActivity mainActivity);
}
