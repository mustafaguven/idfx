package tr.com.idefix.android.presenter;

import javax.inject.Inject;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.mapper.CommonDataMapper;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.android.view.StoresActivityView;
import tr.com.idefix.domain.interactor.ICommonInterActor;

/**
 * Created by utkan on 01/09/15.
 */
@PerActivity public class StoresActivityPresenter implements IStoresActivityPresenter {

  private final CommonDataMapper commonDataMapper;
  private final ICommonInterActor commonInterActor;
  StoresActivityView storesActivityView;
  //    Collection<String> cities;

  @Inject public StoresActivityPresenter(
      ICommonInterActor commonInterActor, CommonDataMapper commonDataMapper
  ) {
    this.commonInterActor = commonInterActor;
    this.commonDataMapper = commonDataMapper;
  }

  @Override public void resume() {

  }

  @Override public void pause() {
    commonInterActor.unSubscribe();
  }

  @Override public void destroy() {
    commonInterActor.unSubscribe();
  }

  @Override public void setView(IView iView) {
    this.storesActivityView = (StoresActivityView) iView;
  }

  //    @Override
  //    public void getRetailStores() {
  //
  //        cities = new TreeSet<>(Collator.getInstance(new Locale("tr-TR")));
  //
  //        commonInterActor.getRetailStores(new DefaultSubscriber<List<Store>>() {
  //            @Override
  //            public void onStart() {
  //                super.onStart();
  //            }
  //
  //            @Override
  //            public void onError(Throwable e) {
  //                super.onError(e);
  //            }
  //
  //            @Override
  //            public void onNext(List<Store> stores) {
  //                super.onNext(stores);
  //
  //                for (Store store : stores) {
  //                    cities.add(store.cityName());
  //                }
  //                storesActivityView.renderCities(cities);
  //            }
  //
  //            @Override
  //            public void onCompleted() {
  //                super.onCompleted();
  //            }
  //        });
  //    }
}
