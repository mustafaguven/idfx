package tr.com.idefix.android.view.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import tr.com.idefix.android.R;
import tr.com.idefix.android.internal.di.HasComponent;
import tr.com.idefix.android.internal.di.components.DaggerReviewsActivityComponent;
import tr.com.idefix.android.internal.di.components.ReviewsActivityComponent;
import tr.com.idefix.android.presenter.IReviewsActivityPresenter;
import tr.com.idefix.android.view.ReviewsActivityView;
import tr.com.idefix.android.view.adapters.ReviewsAdapter;
import tr.com.idefix.domain.Review;

public class ReviewsActivity extends BaseActivity
    implements HasComponent<ReviewsActivityComponent>, ReviewsActivityView {

  ReviewsActivityComponent reviewsActivityComponent;

  @Bind(R.id.toolbar) Toolbar toolbar;

  @Bind(R.id.category_title) AppCompatTextView category_title;

  @Inject IReviewsActivityPresenter presenter;

  @Bind(R.id.basket_count) AppCompatTextView basket_count;

  @Bind(R.id.reviews) RecyclerView mRecyclerView;
  private ReviewsAdapter mAdapter;
  private RecyclerView.LayoutManager mLayoutManager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reviews);

    ////////////////////////////////////////////////////////////////////////////////////////////
    ButterKnife.bind(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    reviewsActivityComponent = DaggerReviewsActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    reviewsActivityComponent.inject(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    presenter.setView(this);
    presenter.processIntent(getIntent());
    ////////////////////////////////////////////////////////////////////////////////////////////
    deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.bg_red_toolbar_start));
    ////////////////////////////////////////////////////////////////////////////////////////////
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toolbar.setNavigationOnClickListener(v -> finish());
    ////////////////////////////////////////////////////////////////////////////////////////////
    // use this setting to improve performance if you know that changes
    // in content do not change the layout size of the RecyclerView
    mRecyclerView.setHasFixedSize(true);

    // use a linear layout manager
    mLayoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(mLayoutManager);

    // specify an adapter (see also next example)
    mAdapter = new ReviewsAdapter(this, new ArrayList<>());
    mRecyclerView.setAdapter(mAdapter);
  }

  @Override protected void onResume() {
    super.onResume();
    presenter.resume();
  }

  @Override public void setTitle(String title) {
    category_title.setText(title);
  }

  @Override public ReviewsActivityComponent getComponent() {
    return reviewsActivityComponent;
  }

  @Override public void bindReviews(List<Review> reviews) {
    mAdapter.addList(reviews);
  }

  @OnClick(R.id.search_icon) void search() {
    navigator.navigateToSearchActivity(this);
  }

  @OnClick({ R.id.basket_count, R.id.basket_icon }) void viewBasket() {
    if (presenter.isloggedIn()) {
      navigator.navigateToBasketActivity(this);
    }
  }

  @Override public void setCartItemCount(int cartItemCount) {
    basket_count.setVisibility(View.VISIBLE);
    basket_count.setText(String.valueOf(cartItemCount));
  }

  @Override public void setBasketCount(int count) {
    basket_count.setText(String.valueOf(count));
    basket_count.setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
  }
}
