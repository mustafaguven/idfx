package tr.com.idefix.android.internal.di.modules;

import dagger.Module;
import dagger.Provides;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.mapper.UserProfileDataMapper;
import tr.com.idefix.android.presenter.IMyInformationPresenter;
import tr.com.idefix.android.presenter.IMyListPresenter;
import tr.com.idefix.android.presenter.IMyOrdersPresenter;
import tr.com.idefix.android.presenter.MyInformationPresenter;
import tr.com.idefix.android.presenter.MyListPresenter;
import tr.com.idefix.android.presenter.MyOrdersPresenter;
import tr.com.idefix.android.presenter.UserProfilePresenter;
import tr.com.idefix.domain.interactor.CatalogInterActor;
import tr.com.idefix.domain.interactor.CommonInterActor;
import tr.com.idefix.domain.interactor.CustomerInterActor;
import tr.com.idefix.domain.interactor.ICatalogInterActor;
import tr.com.idefix.domain.interactor.ICommonInterActor;
import tr.com.idefix.domain.interactor.ICustomerInterActor;
import tr.com.idefix.domain.interactor.ISignInInterActor;
import tr.com.idefix.domain.interactor.SignInInterActor;

/**
 * Created by mustafaguven on 13.10.2015.
 */

@Module public class UserProfileActivityModule {

  UserProfileDataMapper provideProfileDataMapper() {
    return new UserProfileDataMapper();
  }

  @Provides @PerActivity IMyInformationPresenter provideMyInformationPresenter(
      UserProfileDataMapper userProfileDataMapper, ISignInInterActor signInInterActor,
      CommonInterActor commonInterActor, ICustomerInterActor customerInterActor
  ) {
    return new MyInformationPresenter(userProfileDataMapper, signInInterActor, commonInterActor,
        customerInterActor);
  }

  @Provides @PerActivity IMyOrdersPresenter provideMyOrdersPresenter(
      UserProfileDataMapper userProfileDataMapper, ISignInInterActor signInInterActor,
      ICommonInterActor commonInterActor, ICustomerInterActor customerInterActor
  ) {
    return new MyOrdersPresenter(userProfileDataMapper, signInInterActor, commonInterActor,
        customerInterActor);
  }

  @Provides @PerActivity IMyListPresenter provideMyListPresenter(
      UserProfileDataMapper userProfileDataMapper, ISignInInterActor signInInterActor,
      ICommonInterActor commonInterActor, ICustomerInterActor customerInterActor,
      ICatalogInterActor catalogInterActor
  ) {
    return new MyListPresenter(userProfileDataMapper, signInInterActor, commonInterActor,
        customerInterActor, catalogInterActor);
  }

  @Provides @PerActivity UserProfilePresenter provideUserProfileActivity(
      ISignInInterActor signInInterActor, ICommonInterActor commonInterActor,
      ICustomerInterActor customerInterActor
  ) {
    return new UserProfilePresenter(signInInterActor, commonInterActor, customerInterActor);
  }

  @Provides @PerActivity ISignInInterActor provideSignInInteractor() {
    return new SignInInterActor();
  }

  @Provides @PerActivity ICustomerInterActor provideCustomerInterActor() {
    return new CustomerInterActor();
  }

  @Provides @PerActivity ICommonInterActor provideCommonInterActor() {
    return new CommonInterActor();
  }

  @Provides @PerActivity ICatalogInterActor provideCatalogInterActor() {
    return new CatalogInterActor();
  }
}
