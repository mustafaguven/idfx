package tr.com.idefix.android.internal.di.components;

import dagger.Component;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.internal.di.modules.ActivityModule;
import tr.com.idefix.android.internal.di.modules.NotifyPaymentModule;
import tr.com.idefix.android.view.activities.NotifyPaymentActivity;

@PerActivity @Component(dependencies = ApplicationComponent.class, modules = {
    ActivityModule.class, NotifyPaymentModule.class
}) public interface NotifyPaymentActivityComponent extends ActivityComponent {
  void inject(NotifyPaymentActivity notifyPaymentActivity);
}
