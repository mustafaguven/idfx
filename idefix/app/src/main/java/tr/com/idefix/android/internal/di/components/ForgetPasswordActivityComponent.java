package tr.com.idefix.android.internal.di.components;

import dagger.Component;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.internal.di.modules.ActivityModule;
import tr.com.idefix.android.internal.di.modules.ForgetPasswordModule;
import tr.com.idefix.android.view.activities.ForgetPasswordActivity;
import tr.com.idefix.android.view.fragments.ForgetPasswordFragment;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity @Component(
    dependencies = ApplicationComponent.class,
    modules = {
        ActivityModule.class, ForgetPasswordModule.class
    }) public interface ForgetPasswordActivityComponent extends ActivityComponent {

  void inject(ForgetPasswordActivity forgetPasswordActivity);

  void injectForgetPasswordFragment(ForgetPasswordFragment forgetPasswordFragment);
}
