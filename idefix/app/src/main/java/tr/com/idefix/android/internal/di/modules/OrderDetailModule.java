package tr.com.idefix.android.internal.di.modules;

import dagger.Module;
import dagger.Provides;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.presenter.IOrderDetailPresenter;
import tr.com.idefix.android.presenter.OrderDetailPresenter;
import tr.com.idefix.domain.interactor.CustomerInterActor;
import tr.com.idefix.domain.interactor.ICustomerInterActor;

/**
 * Created by mustafaguven on 23.10.2015.
 */

@Module public class OrderDetailModule {

  @Provides @PerActivity IOrderDetailPresenter provideOrderDetailPresenter(
      ICustomerInterActor customerInterActor
  ) {
    return new OrderDetailPresenter(customerInterActor);
  }

  @Provides @PerActivity ICustomerInterActor provideCustomerInteractor() {
    return new CustomerInterActor();
  }
}
