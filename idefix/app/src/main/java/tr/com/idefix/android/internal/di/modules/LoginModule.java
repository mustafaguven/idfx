package tr.com.idefix.android.internal.di.modules;

import dagger.Module;
import dagger.Provides;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.mapper.LoginDataMapper;
import tr.com.idefix.android.presenter.ILoginPresenter;
import tr.com.idefix.android.presenter.LoginPresenter;
import tr.com.idefix.domain.interactor.ISignInInterActor;
import tr.com.idefix.domain.interactor.SignInInterActor;

/**
 * Dagger module that provides user related collaborators.
 */
@Module public class LoginModule {

  @Provides @PerActivity LoginDataMapper provideLoginDataMapper() {
    return new LoginDataMapper();
  }

  @Provides @PerActivity ISignInInterActor provideSignInInterActor() {
    return new SignInInterActor();
  }

  @Provides @PerActivity ILoginPresenter provideLoginPresenter(
      ISignInInterActor signInInterActor, LoginDataMapper loginDataMapper
  ) {
    return new LoginPresenter(signInInterActor, loginDataMapper);
  }
}