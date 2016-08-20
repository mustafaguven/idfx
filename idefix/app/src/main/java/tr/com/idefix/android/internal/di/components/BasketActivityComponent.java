package tr.com.idefix.android.internal.di.components;

import dagger.Component;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.internal.di.modules.ActivityModule;
import tr.com.idefix.android.internal.di.modules.BasketActivityModule;
import tr.com.idefix.android.view.activities.BasketActivity;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity @Component(
    dependencies = ApplicationComponent.class,
    modules = {
        ActivityModule.class, BasketActivityModule.class
    }) public interface BasketActivityComponent extends ActivityComponent {

  void inject(BasketActivity basketActivity);
}
