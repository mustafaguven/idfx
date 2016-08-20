package tr.com.idefix.android.view.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import tr.com.idefix.android.R;
import tr.com.idefix.android.internal.di.HasComponent;
import tr.com.idefix.android.internal.di.components.BasketActivityComponent;
import tr.com.idefix.android.internal.di.components.DaggerBasketActivityComponent;

public class BasketActivity extends BaseActivity implements HasComponent<BasketActivityComponent> {

  @Bind(R.id.toolbar) Toolbar toolbar;

  BasketActivityComponent basketActivityComponent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_basket);
    ////////////////////////////////////////////////////////////////////////////////////////////
    ButterKnife.bind(this);
    ////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////
    basketActivityComponent = DaggerBasketActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    basketActivityComponent.inject(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.bg_red_toolbar_start));
    ////////////////////////////////////////////////////////////////////////////////////////////
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toolbar.setNavigationOnClickListener(v -> finish());
    getSupportActionBar().setTitle(R.string.turn_your_back);
    ////////////////////////////////////////////////////////////////////////////////////////////
  }

  @Override public BasketActivityComponent getComponent() {
    return basketActivityComponent;
  }
}
