package tr.com.idefix.android.presenter;

import java.util.List;
import javax.inject.Inject;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.mapper.CommonDataMapper;
import tr.com.idefix.android.view.CommonView;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.domain.Country;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICommonInterActor;

/**
 * Created by utkan on 01/09/15.
 */
@PerActivity public class CommonPresenter implements ICommonPresenter {

  private final CommonDataMapper commonDataMapper;
  private final ICommonInterActor commonInterActor;
  CommonView commonView;

  @Inject public CommonPresenter(
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
    this.commonView = (CommonView) iView;
  }

  @Override public void getAvailableCountries() {
    commonInterActor.getAvailableCountries(new DefaultSubscriber<List<Country>>() {
      @Override public void onStart() {
        super.onStart();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
      }

      @Override public void onNext(List<Country> countries) {
        super.onNext(countries);
        commonView.setCountries(countries);
      }

      @Override public void onCompleted() {
        super.onCompleted();
      }
    });
  }
}
