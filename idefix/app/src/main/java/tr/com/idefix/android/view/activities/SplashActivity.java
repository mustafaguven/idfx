package tr.com.idefix.android.view.activities;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tr.com.idefix.android.R;
import tr.com.idefix.android.internal.di.HasComponent;
import tr.com.idefix.android.internal.di.components.DaggerSplashComponent;
import tr.com.idefix.android.internal.di.components.SplashComponent;
import tr.com.idefix.android.presenter.ISplashPresenter;
import tr.com.idefix.android.view.SplashView;

public class SplashActivity
    extends BaseActivity
    implements
    HasComponent<SplashComponent>,
    SplashView {

  @Bind(R.id.next)
  ImageView next;
  @Inject
  ISplashPresenter splashPresenter;
  private SplashComponent splashComponent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    ButterKnife.bind(this);

    splashComponent = DaggerSplashComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();


    getComponent().inject(this);

    splashPresenter.setView(this);

    deviceUtils.setStatusBarColor(
        this,
        getResources().getColor(R.color.bg_splash)
    );

    splashPresenter.getData();
  }

  @Override
  public void setNextVisible() {

/*        //TODO:remove this lines and open directly
        next
                .animate()
                .setStartDelay(250)
                .alpha(1)
                .start();*/

    new Handler().postDelayed(() -> next(), 750);
  }

  @Override
  public void getSettings() {

  }

  @OnClick(R.id.next)
  void next() {
    navigator.navigateToMainActivity(this, splashPresenter.getBundle());
    finish();
  }

  @Override
  public SplashComponent getComponent() {
    return splashComponent;
  }

  @Override
  public void setBasketCount(int count) {

  }
}