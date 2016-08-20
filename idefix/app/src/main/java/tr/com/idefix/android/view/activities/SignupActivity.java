package tr.com.idefix.android.view.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import tr.com.idefix.android.R;
import tr.com.idefix.android.internal.di.HasComponent;
import tr.com.idefix.android.internal.di.components.DaggerSignupActivityComponent;
import tr.com.idefix.android.internal.di.components.SignupActivityComponent;
import tr.com.idefix.android.view.components.SearchViewComponent;
import tr.com.idefix.android.view.listeners.OnSearchClickListener;

public class SignupActivity extends BaseActivity
    implements HasComponent<SignupActivityComponent>, OnSearchClickListener {
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.search_icon) SearchViewComponent searchViewComponent;

  private SignupActivityComponent signupActivityComponent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup);

    ButterKnife.bind(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    signupActivityComponent = DaggerSignupActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();

    signupActivityComponent.inject(this);

    // Set a toolbar which will replace the action bar.
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_white_back);
    toolbar.setNavigationOnClickListener(v -> finish());
    toolbar.setTitle("");
    setTitle("");
    searchViewComponent.setOnSearchClickListener(this);
    //presenter.setView(this);
  }

  @Override public SignupActivityComponent getComponent() {
    return signupActivityComponent;
  }

  @Override public void onSearchClick() {
    openSearch();
  }
}
