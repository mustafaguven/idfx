package tr.com.idefix.android.internal.di.modules;

import dagger.Module;
import dagger.Provides;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.mapper.CommonDataMapper;
import tr.com.idefix.android.presenter.CommonPresenter;
import tr.com.idefix.android.presenter.ICommonPresenter;
import tr.com.idefix.domain.interactor.CommonInterActor;
import tr.com.idefix.domain.interactor.ICommonInterActor;

/**
 * Dagger module that provides user related collaborators.
 */
@Module public class StoresModule {

  @Provides @PerActivity CommonDataMapper provideCommonDataMapper() {
    return new CommonDataMapper();
  }

  @Provides @PerActivity ICommonInterActor provideCommonInterActor() {
    return new CommonInterActor();
  }

  @Provides @PerActivity ICommonPresenter provideCommonPresenter(
      ICommonInterActor commonInterActor, CommonDataMapper commonDataMapper
  ) {
    return new CommonPresenter(commonInterActor, commonDataMapper);
  }
}