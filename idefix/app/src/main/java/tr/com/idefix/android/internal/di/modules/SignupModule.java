package tr.com.idefix.android.internal.di.modules;

import dagger.Module;
import dagger.Provides;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.presenter.ISignupPresenter;
import tr.com.idefix.android.presenter.SignupPresenter;
import tr.com.idefix.domain.interactor.CommonInterActor;
import tr.com.idefix.domain.interactor.ICommonInterActor;

/**
 * Dagger module that provides user related collaborators.
 */
@Module public class SignupModule {

  @Provides @PerActivity ICommonInterActor provideCommonInterActor() {
    return new CommonInterActor();
  }

  @Provides @PerActivity ISignupPresenter provideSignupFragmentPresenter(
      ICommonInterActor commonInterActor
  ) {
    return new SignupPresenter(commonInterActor);
  }
}