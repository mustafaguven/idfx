package tr.com.idefix.android.view.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import javax.inject.Inject;
import tr.com.idefix.android.R;
import tr.com.idefix.android.internal.di.HasComponent;
import tr.com.idefix.android.internal.di.components.DaggerReviewActivityComponent;
import tr.com.idefix.android.internal.di.components.ReviewActivityComponent;
import tr.com.idefix.android.presenter.IReviewPresenter;
import tr.com.idefix.android.view.ReviewActivityView;
import tr.com.idefix.android.view.listeners.OnReviewListener;

public class ReviewActivity extends BaseActivity
    implements HasComponent<ReviewActivityComponent>, ReviewActivityView, OnReviewListener {

  //<editor-fold desc="Fields">
  @Bind(R.id.toolbar) Toolbar toolbar;
  ReviewActivityComponent reviewActivityComponent;
  @Inject IReviewPresenter presenter;

  @Bind(R.id.category_title) AppCompatTextView category_title;

  @Bind(R.id.basket_count) AppCompatTextView basket_count;

  //</editor-fold>

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_review);
    ////////////////////////////////////////////////////////////////////////////////////////////
    ButterKnife.bind(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    reviewActivityComponent = DaggerReviewActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    reviewActivityComponent.inject(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    presenter.setView(this);
    presenter.processIntent(getIntent());
    ////////////////////////////////////////////////////////////////////////////////////////////
    deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.bg_red_toolbar_start));
    ////////////////////////////////////////////////////////////////////////////////////////////
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toolbar.setNavigationOnClickListener(v -> finish());
  }

  @Override public void setTitle(String title) {
    category_title.setText(title);
  }

  @Override public void setCartItemCount(int cartItemCount) {
    basket_count.setVisibility(View.VISIBLE);
    basket_count.setText(String.valueOf(cartItemCount));
  }

  @OnClick(R.id.search_icon) void search() {
    navigator.navigateToSearchActivity(this);
  }

  @OnClick({ R.id.basket_count, R.id.basket_icon }) void viewBasket() {
    if (presenter.isloggedIn()) {
      navigator.navigateToBasketActivity(this);
    }
  }

  @Override protected void onResume() {
    super.onResume();
    presenter.resume();
  }

  @Override public ReviewActivityComponent getComponent() {
    return reviewActivityComponent;
  }

  @Override public void onReview(String title, String review) {
    presenter.review(title, review);
  }

  @Override public void onReviewed() {

    showDialog("Yorum başarıyla gönderildi.");
  }

  @Override public void showException(String message) {

    showDialog(message);
  }

  void showDialog(String message) {

    final Dialog dialog = new Dialog(this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.dialog_ok);
    dialog.setTitle("");

    View container = dialog.findViewById(R.id.container);
    container.getLayoutParams().width = (int) (deviceUtils.getDeviceWidth(this) * 0.90);
    dialog.findViewById(R.id.ok).setOnClickListener(v -> {
      dialog.dismiss();
      finish();
    });

    TextView content = (TextView) dialog.findViewById(R.id.content);
    content.setText(message);

    dialog.show();
  }

  @Override public void setBasketCount(int count) {
    basket_count.setText(String.valueOf(count));
    basket_count.setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
  }
}
