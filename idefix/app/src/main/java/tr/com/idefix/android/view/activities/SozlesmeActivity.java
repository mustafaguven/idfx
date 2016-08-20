package tr.com.idefix.android.view.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import javax.inject.Inject;
import tr.com.idefix.android.R;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.internal.di.HasComponent;
import tr.com.idefix.android.internal.di.components.DaggerSozlesmeActivityComponent;
import tr.com.idefix.android.internal.di.components.SozlesmeActivityComponent;
import tr.com.idefix.android.presenter.SozlesmePresenter;
import tr.com.idefix.android.view.SozlesmeActivityView;

/**
 * Created by mustafaguven on 06.11.2015.
 */
public class SozlesmeActivity extends BaseActivity
    implements SozlesmeActivityView, HasComponent<SozlesmeActivityComponent> {

  @Bind(R.id.toolbar) Toolbar toolbar;

  @Bind(R.id.category_title) TextView category_title;

  @Bind(R.id.title) TextView title;

  SozlesmeActivityComponent sozlesmeActivityComponent;

  @Inject SozlesmePresenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sozlesme_activity);
    ButterKnife.bind(this);

    sozlesmeActivityComponent = DaggerSozlesmeActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();

    getComponent().inject(this);
    presenter.setView(this);
    presenter.getSozlesme();
    deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.bg_red_toolbar_start));
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_white_back);
    toolbar.setNavigationOnClickListener(v -> finish());
    if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Keys.TITLE)) {
      category_title.setText(getIntent().getExtras().getString(Keys.TITLE));
    }
  }

  @Override public SozlesmeActivityComponent getComponent() {
    return sozlesmeActivityComponent;
  }

  @Override public void showSozlesme(String html) {
    title.setText(Html.fromHtml(html));
  }

  @Override public void setBasketCount(int count) {

  }
}
