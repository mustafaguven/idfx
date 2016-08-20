package tr.com.idefix.android.internal.di.modules;

import dagger.Module;
import dagger.Provides;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.presenter.ForgetPasswordPresenter;
import tr.com.idefix.android.presenter.IForgetPasswordPresenter;
import tr.com.idefix.domain.interactor.CommonInterActor;
import tr.com.idefix.domain.interactor.ICommonInterActor;

/**
 * Dagger module that provides user related collaborators.
 */
@Module public class ForgetPasswordModule {

  @Provides @PerActivity ICommonInterActor provideCommonInterActor() {
    return new CommonInterActor();
  }

  @Provides @PerActivity IForgetPasswordPresenter provideForgetPasswordPresenter(
      ICommonInterActor commonInterActor
  ) {
    return new ForgetPasswordPresenter(commonInterActor);
  }
}