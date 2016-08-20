package tr.com.idefix.android.internal.di.modules;

import dagger.Module;
import dagger.Provides;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.presenter.INotifyPaymentPresenter;
import tr.com.idefix.android.presenter.NotifyPaymentPresenter;
import tr.com.idefix.domain.interactor.CommonInterActor;
import tr.com.idefix.domain.interactor.CustomerInterActor;
import tr.com.idefix.domain.interactor.ICommonInterActor;
import tr.com.idefix.domain.interactor.ICustomerInterActor;

@Module public class NotifyPaymentModule {

  @Provides @PerActivity INotifyPaymentPresenter provideNotifyPaymentPresenter(
      ICustomerInterActor customerInterActor, ICommonInterActor commonInterActor
  ) {
    return new NotifyPaymentPresenter(customerInterActor, commonInterActor);
  }

  @Provides @PerActivity ICustomerInterActor provideCustomerInteractor() {
    return new CustomerInterActor();
  }

  @Provides @PerActivity ICommonInterActor provideCommonInterActor() {
    return new CommonInterActor();
  }
}
