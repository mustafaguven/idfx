package tr.com.idefix.android.view.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import javax.inject.Inject;
import tr.com.idefix.android.R;
import tr.com.idefix.android.internal.di.components.UserProfileActivityComponent;
import tr.com.idefix.android.presenter.IMyOrdersPresenter;
import tr.com.idefix.android.view.MyOrdersView;
import tr.com.idefix.android.view.adapters.OrdersAdapter;
import tr.com.idefix.data.entity.CustomerOrdersBaseEntity;

/**
 * Created by mustafaguven on 13.10.2015.
 */
public class MyOrdersFragment extends BaseFragment implements MyOrdersView {

  @Inject IMyOrdersPresenter presenter;

  @Bind(R.id.rvOrders) RecyclerView rvOrders;

  @Bind(R.id.rlNoOrder) RelativeLayout rlNoOrder;

  @Override public void onFetchOrders(CustomerOrdersBaseEntity customerOrdersBaseEntity) {
    rvOrders.setVisibility(View.GONE);
    rlNoOrder.setVisibility(View.GONE);
    if (customerOrdersBaseEntity.getOrders().size() > 0) {
      OrdersAdapter adapter =
          new OrdersAdapter(getActivity(), customerOrdersBaseEntity.getOrders(), presenter);
      rvOrders.setAdapter(adapter);
      rvOrders.setVisibility(View.VISIBLE);
    } else {
      rlNoOrder.setVisibility(View.VISIBLE);
    }
  }

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
  ) {
    View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
    ButterKnife.bind(this, view);

    return view;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    rvOrders.setHasFixedSize(true);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    rvOrders.setLayoutManager(linearLayoutManager);
    rvOrders.setItemAnimator(new DefaultItemAnimator());
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    getComponent(UserProfileActivityComponent.class).injectMyOrdersFragment(this);

    presenter.setView(this);
    presenter.getCustomerOrders();
  }

  @Override public void setBasketCount(int count) {
    //TODO: divide into interfaces activityview and fragmentview
  }
}
