package tr.com.idefix.android.internal.di.components;

import dagger.Component;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.internal.di.modules.ActivityModule;
import tr.com.idefix.android.internal.di.modules.SearchModule;
import tr.com.idefix.android.view.activities.BarcodeInfoActivity;

/**
 * Created by mustafaguven on 13.10.2015.
 */

@PerActivity @Component(dependencies = ApplicationComponent.class, modules = {
    ActivityModule.class, SearchModule.class
}) public interface BarcodeInfoActivityComponent extends ActivityComponent {
  void inject(BarcodeInfoActivity barcodeInfoActivity);
}
