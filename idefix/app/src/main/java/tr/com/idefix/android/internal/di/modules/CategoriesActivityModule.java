package tr.com.idefix.android.internal.di.modules;

import dagger.Module;
import dagger.Provides;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.mapper.CatalogDataMapper;
import tr.com.idefix.android.mapper.CustomerDataMapper;
import tr.com.idefix.android.presenter.CategoriesActivityPresenter;
import tr.com.idefix.android.presenter.ICategoriesActivityPresenter;
import tr.com.idefix.android.presenter.IParentCategoryPresenter;
import tr.com.idefix.android.presenter.ITopCategoryInfoPresenter;
import tr.com.idefix.android.presenter.ParentCategoryPresenter;
import tr.com.idefix.android.presenter.TopCategoryInfoPresenter;
import tr.com.idefix.domain.interactor.CatalogInterActor;
import tr.com.idefix.domain.interactor.CustomerInterActor;
import tr.com.idefix.domain.interactor.ICatalogInterActor;
import tr.com.idefix.domain.interactor.ICustomerInterActor;

/**
 * Dagger module that provides user related collaborators.
 */
@Module public class CategoriesActivityModule {

  @Provides @PerActivity CustomerDataMapper provideCustomerDataMapper() {
    return new CustomerDataMapper();
  }

  @Provides @PerActivity CatalogDataMapper provideCatalogDataMapper() {
    return new CatalogDataMapper();
  }

  @Provides @PerActivity ICustomerInterActor provideCustomerInterActor() {
    return new CustomerInterActor();
  }

  @Provides @PerActivity ICatalogInterActor provideCatalogInterActor() {
    return new CatalogInterActor();
  }

  @Provides @PerActivity ICategoriesActivityPresenter provideICategoriesActivityPresenter(
      ICustomerInterActor customerInterActor, CustomerDataMapper customerDataMapper
  ) {
    return new CategoriesActivityPresenter(customerDataMapper, customerInterActor);
  }

  @Provides @PerActivity IParentCategoryPresenter provideParentCategoryPresenter(
      ICatalogInterActor catalogInterActor, CatalogDataMapper catalogDataMapper
  ) {
    return new ParentCategoryPresenter(catalogInterActor, catalogDataMapper);
  }

  @Provides @PerActivity ITopCategoryInfoPresenter provideTopCategoryInfoPresenter(
      ICatalogInterActor catalogInterActor, CatalogDataMapper catalogDataMapper

  ) {
    return new TopCategoryInfoPresenter(catalogInterActor, catalogDataMapper);
  }
}