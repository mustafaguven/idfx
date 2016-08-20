package tr.com.idefix.android.internal.di.components;

import dagger.Component;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.internal.di.modules.ActivityModule;
import tr.com.idefix.android.internal.di.modules.CategoriesActivityModule;
import tr.com.idefix.android.view.activities.CategoriesActivity;
import tr.com.idefix.android.view.fragments.ParentCategoryFragment;
import tr.com.idefix.android.view.fragments.TopCategoryInfoFragment;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity @Component(
    dependencies = ApplicationComponent.class,
    modules = {
        ActivityModule.class, CategoriesActivityModule.class
    }) public interface CategoriesActivityComponent extends ActivityComponent {

  void inject(CategoriesActivity categoriesActivity);

  void inject(ParentCategoryFragment parentCategoryFragment);

  void inject(TopCategoryInfoFragment topCategoryInfoFragment);
}
