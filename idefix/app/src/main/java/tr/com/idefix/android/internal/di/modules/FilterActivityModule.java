package tr.com.idefix.android.internal.di.modules;

import dagger.Module;
import dagger.Provides;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.presenter.FilterActivityPresenter;
import tr.com.idefix.android.presenter.IFilterActivityPresenter;

/**
 * Dagger module that provides user related collaborators.
 */
@Module public class FilterActivityModule {

  @PerActivity @Provides IFilterActivityPresenter provideFilterActivityPresenter() {
    return new FilterActivityPresenter();
  }
}