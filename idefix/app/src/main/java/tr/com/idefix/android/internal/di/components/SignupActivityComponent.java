package tr.com.idefix.android.internal.di.components;

import dagger.Component;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.internal.di.modules.ActivityModule;
import tr.com.idefix.android.internal.di.modules.SignupModule;
import tr.com.idefix.android.view.activities.SignupActivity;
import tr.com.idefix.android.view.fragments.SignupFragment;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity @Component(
    dependencies = ApplicationComponent.class,
    modules = {
        ActivityModule.class, SignupModule.class
    }) public interface SignupActivityComponent extends ActivityComponent {

  void inject(SignupActivity signupActivity);

  void injectSignupFragment(SignupFragment signupFragment);
}
