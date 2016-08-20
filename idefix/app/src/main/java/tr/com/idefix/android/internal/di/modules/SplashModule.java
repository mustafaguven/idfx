package tr.com.idefix.android.internal.di.modules;

import dagger.Module;
import dagger.Provides;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.mapper.SplashDataMapper;
import tr.com.idefix.android.presenter.ISplashPresenter;
import tr.com.idefix.android.presenter.SplashPresenter;
import tr.com.idefix.domain.interactor.ISplashInterActor;
import tr.com.idefix.domain.interactor.SplashInterActor;

/**
 * Dagger module that provides user related collaborators.
 */
@Module public class SplashModule {

  @Provides @PerActivity SplashDataMapper provideSplashDataMapper() {
    return new SplashDataMapper();
  }

  @Provides @PerActivity ISplashInterActor provideSplashInterActor() {
    return new SplashInterActor();
  }

  @Provides @PerActivity ISplashPresenter provideSplashPresenter(
      ISplashInterActor splashInterActor, SplashDataMapper splashDataMapper
  ) {
    return new SplashPresenter(splashInterActor, splashDataMapper);
  }
}