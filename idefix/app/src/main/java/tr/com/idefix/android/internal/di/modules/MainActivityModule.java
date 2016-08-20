package tr.com.idefix.android.internal.di.modules;

import dagger.Module;
import dagger.Provides;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.mapper.CustomerDataMapper;
import tr.com.idefix.android.mapper.SplashDataMapper;
import tr.com.idefix.android.presenter.IMainActivityPresenter;
import tr.com.idefix.android.presenter.MainActivityPresenter;
import tr.com.idefix.domain.interactor.CustomerInterActor;
import tr.com.idefix.domain.interactor.ICustomerInterActor;
import tr.com.idefix.domain.interactor.ISplashInterActor;
import tr.com.idefix.domain.interactor.SplashInterActor;

/**
 * Dagger module that provides user related collaborators.
 */
@Module public class MainActivityModule {

  @Provides @PerActivity CustomerDataMapper provideApiCustomerDataMapper() {
    return new CustomerDataMapper();
  }

  @Provides @PerActivity SplashDataMapper provideSplashDataMapper() {
    return new SplashDataMapper();
  }

  @Provides @PerActivity ICustomerInterActor provideApiCustomerInterActor() {
    return new CustomerInterActor();
  }

  @Provides @PerActivity ISplashInterActor provideSplashInterActor() {
    return new SplashInterActor();
  }

  @Provides @PerActivity IMainActivityPresenter provideMainActivityPresenter(
      ICustomerInterActor apiCustomerInterActor, ISplashInterActor splashInterActor,
      CustomerDataMapper apiCustomerDataMapper, SplashDataMapper splashDataMapper
  ) {
    return new MainActivityPresenter(apiCustomerDataMapper, splashDataMapper, apiCustomerInterActor,
        splashInterActor);
  }
}