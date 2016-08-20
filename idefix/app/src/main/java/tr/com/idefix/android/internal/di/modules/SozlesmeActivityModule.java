package tr.com.idefix.android.internal.di.modules;

import dagger.Module;
import dagger.Provides;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.presenter.SozlesmePresenter;
import tr.com.idefix.domain.interactor.CommonInterActor;
import tr.com.idefix.domain.interactor.CustomerInterActor;
import tr.com.idefix.domain.interactor.ICommonInterActor;
import tr.com.idefix.domain.interactor.ICustomerInterActor;

/**
 * Created by mustafaguven on 13.10.2015.
 */

@Module public class SozlesmeActivityModule {

  @Provides @PerActivity SozlesmePresenter provideSozlesmeActivity(
      ICommonInterActor commonInterActor
  ) {
    return new SozlesmePresenter(commonInterActor);
  }

  @Provides @PerActivity ICustomerInterActor provideCustomerInterActor() {
    return new CustomerInterActor();
  }

  @Provides @PerActivity ICommonInterActor provideCommonInterActor() {
    return new CommonInterActor();
  }
}
