package tr.com.idefix.android.internal.di.components;

import dagger.Component;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.internal.di.modules.ActivityModule;
import tr.com.idefix.android.internal.di.modules.ReviewActivityModule;
import tr.com.idefix.android.view.activities.ReviewActivity;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity @Component(
    dependencies = ApplicationComponent.class,
    modules = {
        ActivityModule.class, ReviewActivityModule.class
    }) public interface ReviewActivityComponent extends ActivityComponent {

  void inject(ReviewActivity reviewActivity);
}
