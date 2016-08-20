package tr.com.idefix.android.view.fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import tr.com.idefix.android.R;
import tr.com.idefix.android.internal.di.components.StoresActivityComponent;
import tr.com.idefix.android.model.StoreModel;
import tr.com.idefix.android.presenter.IStoresFragmentPresenter;
import tr.com.idefix.android.view.StoresFragmentView;
import tr.com.idefix.android.view.adapters.AddessesAdapter;
import tr.com.idefix.android.view.components.DividerItemDecoration;

/**
 * A placeholder fragment containing a simple view.
 */
public class StoresFragment extends BaseFragment implements StoresFragmentView {

  @Bind(R.id.city_selector) AppCompatSpinner city_selector;

  @Bind(R.id.addresses) RecyclerView recyclerView;

  @Inject IStoresFragmentPresenter presenter;

  ArrayAdapter<String> dataAdapter;
  private LinearLayoutManager layoutManager;
  private AddessesAdapter addressesAdapter;

  public StoresFragment() {
  }

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
  ) {
    View view = inflater.inflate(R.layout.fragment_stores, container, false);
    ButterKnife.bind(this, view);

    // use this setting to improve performance if you know that changes
    // in content do not change the layout size of the RecyclerView
    recyclerView.setHasFixedSize(true);

    // use a linear layout manager
    layoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addItemDecoration(
        new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
    // specify an adapter (see also next example)
    addressesAdapter = new AddessesAdapter(getActivity(), new ArrayList<>());
    recyclerView.setAdapter(addressesAdapter);

    return view;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    getComponent(StoresActivityComponent.class).inject(this);

    presenter.setView(this);
    presenter.getRetailStores();
  }

  @Override public void renderCities(List<String> cities) {

    dataAdapter = new ArrayAdapter<>(
        ((AppCompatActivity) getActivity()).getSupportActionBar().getThemedContext(),
        R.layout.item_city_dropdown, cities);
    city_selector.setAdapter(dataAdapter);

    addressesAdapter.addList(presenter.getAddresses());
  }

  @Override public void renderAddresses(List<StoreModel> storeModelList) {
    addressesAdapter.addList(storeModelList);
  }

  @Override public void onResume() {
    super.onResume();
    presenter.resume();
  }

  @Override public void onPause() {
    presenter.pause();
    super.onPause();
  }

  @Override public void onDestroy() {
    presenter.destroy();
    super.onDestroy();
  }

  @OnItemSelected(value = R.id.city_selector, callback = OnItemSelected.Callback.ITEM_SELECTED)
  void onCitySelected(int position) {
    presenter.onCitySelected(dataAdapter.getItem(position));
  }

  @Override public void setBasketCount(int count) {
    //TODO: divide into interfaces activityview and fragmentview
  }
}
