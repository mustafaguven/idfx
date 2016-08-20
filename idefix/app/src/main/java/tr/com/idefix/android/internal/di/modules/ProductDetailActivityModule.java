package tr.com.idefix.android.internal.di.modules;

import dagger.Module;
import dagger.Provides;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.mapper.CatalogDataMapper;
import tr.com.idefix.android.presenter.IProductDetailPresenter;
import tr.com.idefix.android.presenter.ProductDetailPresenter;
import tr.com.idefix.domain.interactor.CatalogInterActor;
import tr.com.idefix.domain.interactor.CustomerInterActor;
import tr.com.idefix.domain.interactor.ICatalogInterActor;
import tr.com.idefix.domain.interactor.ICustomerInterActor;

/**
 * Dagger module that provides user related collaborators.
 */
@Module public class ProductDetailActivityModule {

  @Provides @PerActivity CatalogDataMapper provideCatalogDataMapper() {
    return new CatalogDataMapper();
  }

  @Provides @PerActivity ICatalogInterActor provideCatalogInterActor() {
    return new CatalogInterActor();
  }

  @Provides @PerActivity ICustomerInterActor provideCustomerInterActor() {
    return new CustomerInterActor();
  }

  @Provides @PerActivity IProductDetailPresenter provideProductDetailPresenter(
      ICatalogInterActor catalogInterActor, ICustomerInterActor customerInterActor,
      CatalogDataMapper catalogDataMapper
  ) {
    return new ProductDetailPresenter(catalogInterActor, customerInterActor, catalogDataMapper);
  }
}