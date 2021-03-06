package tr.com.idefix.android.internal.di.components;

import dagger.Component;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.internal.di.modules.ActivityModule;
import tr.com.idefix.android.internal.di.modules.CommonModule;
import tr.com.idefix.android.view.activities.StoresActivity;
import tr.com.idefix.android.view.fragments.StoresFragment;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity @Component(
    dependencies = ApplicationComponent.class,
    modules = {
        ActivityModule.class, CommonModule.class
    }) public interface StoresActivityComponent extends ActivityComponent {
  void inject(StoresFragment storesFragment);

  void inject(StoresActivity storesActivity);
}
