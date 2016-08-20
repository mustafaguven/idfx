package tr.com.idefix.android.view.fragments;

/**
 * Created by utkan on 14/09/15.
 */

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.List;
import javax.inject.Inject;
import tr.com.idefix.android.R;
import tr.com.idefix.android.internal.di.components.CategoriesActivityComponent;
import tr.com.idefix.android.model.CategoryModel;
import tr.com.idefix.android.presenter.IParentCategoryPresenter;
import tr.com.idefix.android.view.ParentCategoryFragmentView;
import tr.com.idefix.android.view.adapters.CategoriesAdapter;
import tr.com.idefix.android.view.listeners.OnCategorySelectedListener;

import static tr.com.idefix.android.contants.Keys.PARENT_CATEGORY_ID;

public class ParentCategoryFragment extends BaseFragment implements ParentCategoryFragmentView {

  @Inject IParentCategoryPresenter presenter;

  @Bind(R.id.categories) RecyclerView categories;
  private CategoriesAdapter categoriesAdapter;
  private OnCategorySelectedListener onCategorySelectedListener;

  public ParentCategoryFragment() {
    // Required empty public constructor
  }

  public static ParentCategoryFragment newInstance(int parentCategoryID) {
    ParentCategoryFragment parentCategoryFragment = new ParentCategoryFragment();

    Bundle bundle = new Bundle();
    bundle.putInt(PARENT_CATEGORY_ID, parentCategoryID);
    parentCategoryFragment.setArguments(bundle);
    return parentCategoryFragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
  ) {
    View view = inflater.inflate(R.layout.fragment_parent_category, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    categories.setLayoutManager(new LinearLayoutManager(getActivity()));
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    getComponent(CategoriesActivityComponent.class).inject(this);

    presenter.setView(this);
    presenter.processArguments(getArguments());
  }

  @Override public void renderCategories(List<CategoryModel> list) {
    categoriesAdapter = new CategoriesAdapter(getActivity(), list);
    categoriesAdapter.setOnCategorySelectedListener(onCategorySelectedListener);
    categories.setAdapter(categoriesAdapter);
  }

  public void setOnCategorySelectedListener(OnCategorySelectedListener onCategorySelectedListener) {
    this.onCategorySelectedListener = onCategorySelectedListener;
  }

  @Override public void showProgress() {
    super.showProgress();
  }

  @Override public void hideProgress() {
    super.hideProgress();
  }

  @Override public void setBasketCount(int count) {

  }
}