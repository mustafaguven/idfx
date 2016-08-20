package tr.com.idefix.android.presenter;

import android.util.SparseArray;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import javax.inject.Inject;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.mapper.CommonDataMapper;
import tr.com.idefix.android.model.StoreModel;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.android.view.StoresFragmentView;
import tr.com.idefix.domain.Store;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICommonInterActor;

/**
 * Created by utkan on 01/09/15.
 */
@PerActivity public class StoresFragmentPresenter implements IStoresFragmentPresenter {

  //<editor-fold desc="Fields">
  private final CommonDataMapper commonDataMapper;
  private final ICommonInterActor commonInterActor;
  StoresFragmentView storesFragmentView;
  Collection<String> cities;
  SparseArray<List<StoreModel>> sparseArray;
  private Store firstStore;
  //</editor-fold>

  @Inject public StoresFragmentPresenter(
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
    this.storesFragmentView = (StoresFragmentView) iView;
  }

  @Override public void getRetailStores() {

    cities = new TreeSet<>(Collator.getInstance(new Locale("tr-TR")));

    commonInterActor.getRetailStores(new DefaultSubscriber<List<Store>>() {
      @Override public void onStart() {
        super.onStart();
        storesFragmentView.showProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        storesFragmentView.hideProgress();
      }

      @Override public void onNext(List<Store> stores) {
        super.onNext(stores);

        firstStore = stores.get(0);

        sparseArray = commonDataMapper.transform(stores, cities);

        storesFragmentView.renderCities(new ArrayList<>(cities));
      }

      @Override public void onCompleted() {
        super.onCompleted();
        storesFragmentView.hideProgress();
      }
    });
  }

  @Override public List<StoreModel> getAddresses() {
    return sparseArray.get(firstStore.cityName().hashCode());
  }

  @Override public void onCitySelected(String city) {
    storesFragmentView.renderAddresses(sparseArray.get(city.hashCode()));
  }
}
