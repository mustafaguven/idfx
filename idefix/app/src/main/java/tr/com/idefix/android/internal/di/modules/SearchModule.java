package tr.com.idefix.android.internal.di.modules;

import dagger.Module;
import dagger.Provides;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.presenter.ISearchPresenter;
import tr.com.idefix.android.presenter.SearchPresenter;
import tr.com.idefix.domain.interactor.CustomerInterActor;
import tr.com.idefix.domain.interactor.ICustomerInterActor;
import tr.com.idefix.domain.interactor.ISearchInteractor;
import tr.com.idefix.domain.interactor.SearchInteractor;

/**
 * Created by mustafaguven on 23.10.2015.
 */

@Module public class SearchModule {

  @Provides @PerActivity ISearchPresenter provideMyInformationPresenter(
      ISearchInteractor searchInteractor, ICustomerInterActor customerInterActor
  ) {
    return new SearchPresenter(searchInteractor, customerInterActor);
  }

  @Provides @PerActivity ISearchInteractor provideSearchInteractor() {
    return new SearchInteractor();
  }

  @Provides @PerActivity ICustomerInterActor provideCustomerInteractor() {
    return new CustomerInterActor();
  }
}
