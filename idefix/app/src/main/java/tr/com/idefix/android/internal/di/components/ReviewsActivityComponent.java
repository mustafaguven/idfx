package tr.com.idefix.android.internal.di.components;

import dagger.Component;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.internal.di.modules.ActivityModule;
import tr.com.idefix.android.internal.di.modules.ReviewsActivityModule;
import tr.com.idefix.android.view.activities.ReviewsActivity;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity @Component(
    dependencies = ApplicationComponent.class,
    modules = {
        ActivityModule.class, ReviewsActivityModule.class
    }) public interface ReviewsActivityComponent extends ActivityComponent {

  void inject(ReviewsActivity reviewsActivity);
}
