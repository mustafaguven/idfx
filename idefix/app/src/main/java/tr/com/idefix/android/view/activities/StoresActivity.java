package tr.com.idefix.android.view.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import javax.inject.Inject;
import tr.com.idefix.android.R;
import tr.com.idefix.android.internal.di.HasComponent;
import tr.com.idefix.android.internal.di.components.DaggerStoresActivityComponent;
import tr.com.idefix.android.internal.di.components.StoresActivityComponent;
import tr.com.idefix.android.presenter.IStoresActivityPresenter;
import tr.com.idefix.android.view.StoresActivityView;

public class StoresActivity extends BaseActivity
    implements StoresActivityView, HasComponent<StoresActivityComponent> {

  @Inject IStoresActivityPresenter presenter;
  @Bind(R.id.toolbar) Toolbar toolbar;
  private StoresActivityComponent storesActivityComponent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_stores);
    ////////////////////////////////////////////////////////////////////////////////////////////
    ButterKnife.bind(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    toolbar.setTitle(R.string.stores_title);
    ////////////////////////////////////////////////////////////////////////////////////////////
    setSupportActionBar(toolbar);
    ////////////////////////////////////////////////////////////////////////////////////////////
    toolbar.setNavigationOnClickListener(v -> finish());
    toolbar.setNavigationIcon(R.drawable.ic_white_back);
    ////////////////////////////////////////////////////////////////////////////////////////////
    this.storesActivityComponent = DaggerStoresActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();

    storesActivityComponent.inject(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.status_stores_start));
    ////////////////////////////////////////////////////////////////////////////////////////////
    presenter.setView(this);
  }

  @Override public StoresActivityComponent getComponent() {
    return storesActivityComponent;
  }

  @Override public void setBasketCount(int count) {

  }
}
