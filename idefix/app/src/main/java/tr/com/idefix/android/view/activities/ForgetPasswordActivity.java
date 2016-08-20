package tr.com.idefix.android.view.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import tr.com.idefix.android.R;
import tr.com.idefix.android.internal.di.HasComponent;
import tr.com.idefix.android.internal.di.components.DaggerForgetPasswordActivityComponent;
import tr.com.idefix.android.internal.di.components.ForgetPasswordActivityComponent;
import tr.com.idefix.android.view.components.SearchViewComponent;
import tr.com.idefix.android.view.listeners.OnSearchClickListener;

public class ForgetPasswordActivity extends BaseActivity
    implements HasComponent<ForgetPasswordActivityComponent>, OnSearchClickListener {
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.search_icon) SearchViewComponent searchViewComponent;

  private ForgetPasswordActivityComponent forgetPasswordActivityComponent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forget_password);

    ButterKnife.bind(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    forgetPasswordActivityComponent = DaggerForgetPasswordActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();

    forgetPasswordActivityComponent.inject(this);

    // Set a toolbar which will replace the action bar.
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_white_back);
    toolbar.setNavigationOnClickListener(v -> finish());
    toolbar.setTitle("");
    setTitle("");
    searchViewComponent.setOnSearchClickListener(this);
    //presenter.setView(this);
  }

  @Override public ForgetPasswordActivityComponent getComponent() {
    return forgetPasswordActivityComponent;
  }

  @Override public void onSearchClick() {
    openSearch();
  }
}
