package tr.com.idefix.android.internal.di.modules;

import dagger.Module;
import dagger.Provides;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.presenter.IReviewPresenter;
import tr.com.idefix.android.presenter.ReviewPresenter;
import tr.com.idefix.domain.interactor.CatalogInterActor;
import tr.com.idefix.domain.interactor.CustomerInterActor;
import tr.com.idefix.domain.interactor.ICatalogInterActor;
import tr.com.idefix.domain.interactor.ICustomerInterActor;

/**
 * Dagger module that provides user related collaborators.
 */
@Module public class ReviewActivityModule {

  @Provides @PerActivity ICustomerInterActor provideCustomerInterActor() {
    return new CustomerInterActor();
  }

  @Provides @PerActivity ICatalogInterActor provideCatalogInterActor() {
    return new CatalogInterActor();
  }

  @Provides @PerActivity IReviewPresenter provideReviewPresenter(
      CustomerInterActor customerInterActor, CatalogInterActor catalogInterActor
  ) {
    return new ReviewPresenter(customerInterActor, catalogInterActor);
  }
}