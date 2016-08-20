package tr.com.idefix.android.internal.di.components;

import dagger.Component;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.internal.di.modules.ActivityModule;
import tr.com.idefix.android.internal.di.modules.OrderDetailModule;
import tr.com.idefix.android.view.activities.OrderDetailActivity;

/**
 * Created by mustafaguven on 13.10.2015.
 */

@PerActivity @Component(dependencies = ApplicationComponent.class, modules = {
    ActivityModule.class, OrderDetailModule.class
}) public interface OrderDetailActivityComponent extends ActivityComponent {
  void inject(OrderDetailActivity orderDetailActivity);
}
