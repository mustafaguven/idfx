package tr.com.idefix.android.view.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import tr.com.idefix.android.R;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.internal.di.HasComponent;
import tr.com.idefix.android.internal.di.components.DaggerFilterActivityComponent;
import tr.com.idefix.android.internal.di.components.FilterActivityComponent;
import tr.com.idefix.android.presenter.IFilterActivityPresenter;
import tr.com.idefix.android.view.FilterActivityView;
import tr.com.idefix.android.view.adapters.FilterAdapter;
import tr.com.idefix.android.view.adapters.SectionedGridRecyclerViewAdapter;
import tr.com.idefix.android.view.components.DividerItemDecoration;
import tr.com.idefix.domain.FilterItem;

public class FilterActivity extends BaseActivity
    implements HasComponent<FilterActivityComponent>, FilterActivityView {

  //<editor-fold desc="Fields">
  FilterActivityComponent filterActivityComponent;

  @Inject IFilterActivityPresenter presenter;

  @Bind(R.id.toolbar) Toolbar toolbar;

  //    @Bind(R.id.clear_filter)
  //    AppCompatButton clear_filter;
  @Bind(R.id.gridView) RecyclerView gridView;

  FilterAdapter adapter;
  List<SectionedGridRecyclerViewAdapter.Section> sectionList;
  SectionedGridRecyclerViewAdapter.Section[] sectionArray;
  SectionedGridRecyclerViewAdapter mSectionedAdapter;
  //</editor-fold>

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filter);
    ////////////////////////////////////////////////////////////////////////////////////////////
    ButterKnife.bind(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    filterActivityComponent = DaggerFilterActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    filterActivityComponent.inject(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    setSupportActionBar(toolbar);
    getSupportActionBar().setHomeButtonEnabled(true);
    //        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    //        getSupportActionBar().setDisplayShowHomeEnabled(true);

    Drawable close = getResources().getDrawable(R.drawable.ic_big_close);
    Drawable closeMutated = close.mutate();
    closeMutated.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
    toolbar.setNavigationIcon(closeMutated);
    toolbar.setNavigationOnClickListener(v -> finish());
    toolbar.setTitle("");
    setTitle("");
    deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.bg_red_toolbar_start));
    ////////////////////////////////////////////////////////////////////////////////////////////
    gridView.setHasFixedSize(true);
    gridView.setLayoutManager(new GridLayoutManager(this, 1));
    gridView.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    gridView.setItemAnimator(new DefaultItemAnimator());
    ////////////////////////////////////////////////////////////////////////////////////////////
    presenter.setView(this);
    presenter.processArguments(getIntent());
  }

  @Override public FilterActivityComponent getComponent() {
    return filterActivityComponent;
  }

  @Override public void setFilterList(List<FilterItem> filterList) {

    if (filterList != null && filterList.size() > 0) {
      adapter = new FilterAdapter(this, filterList);

      sectionArray = new SectionedGridRecyclerViewAdapter.Section[sectionList.size()];

      mSectionedAdapter =
          new SectionedGridRecyclerViewAdapter(this, R.layout.section_filter, R.id.section_text,
              gridView, adapter, null);

      mSectionedAdapter.setSections(sectionList.toArray(sectionArray));

      gridView.setAdapter(mSectionedAdapter);

      mSectionedAdapter.notifyDataSetChanged();
    }
  }

  @Override public void addSection(String title, int pos) {
    if (sectionList == null) {
      sectionList = new ArrayList<>();
    }

    sectionList.add(new SectionedGridRecyclerViewAdapter.Section(pos, title));
  }

  @OnClick(R.id.ok) void ok() {
    setResult(RESULT_OK,
        new Intent().putExtra(Keys.FILTER, (ArrayList<FilterItem>) adapter.getItems()));
    finish();
  }

  @OnClick(R.id.clear_filter) void clearFilter() {

    List<FilterItem> items = adapter.getItems();

    Observable.from(items).doOnCompleted(new Action0() {
      @Override public void call() {
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
      }
    }).forEach(new Action1<FilterItem>() {
      @Override public void call(FilterItem filterItem) {
        filterItem.selected(false);
      }
    });
  }

  @Override public void setBasketCount(int count) {

  }
}
