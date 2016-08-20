package tr.com.idefix.android.internal.di.components;

import dagger.Component;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.internal.di.modules.ActivityModule;
import tr.com.idefix.android.internal.di.modules.SozlesmeActivityModule;
import tr.com.idefix.android.view.activities.SozlesmeActivity;

/**
 * Created by mustafaguven on 13.10.2015.
 */

@PerActivity @Component(dependencies = ApplicationComponent.class, modules = {
    ActivityModule.class, SozlesmeActivityModule.class
}) public interface SozlesmeActivityComponent extends ActivityComponent {
  void inject(SozlesmeActivity sozlesmeActivity);
}
