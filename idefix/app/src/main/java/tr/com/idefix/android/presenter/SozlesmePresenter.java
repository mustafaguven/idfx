package tr.com.idefix.android.presenter;

import timber.log.Timber;
import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.android.view.SozlesmeActivityView;
import tr.com.idefix.data.entity.SozlesmeResponseEntity;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICommonInterActor;

/**
 * Created by mustafaguven on 19.10.2015.
 */
public class SozlesmePresenter implements Presenter {

  DomainContext domainContext;
  SozlesmeActivityView view;

  ICommonInterActor commonInterActor;

  public SozlesmePresenter(ICommonInterActor commonInterActor) {
    this.commonInterActor = commonInterActor;

    domainContext =
        ApplicationController.getInstance().getDomainApplicationComponent().domainContext();
  }

  public void getSozlesme() {
    commonInterActor.getSozlesme(new DefaultSubscriber<SozlesmeResponseEntity>() {

      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onNext(SozlesmeResponseEntity response) {
        super.onNext(response);

        view.showSozlesme(response.html());
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
      }

      @Override public void onError(Throwable e) {
        Timber.e(e, "sozlesme yok");
        super.onError(e);
        view.hideProgress();
      }
    });
  }

  @Override public void resume() {
  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  @Override public void setView(IView iView) {
    this.view = (SozlesmeActivityView) iView;
  }
}
