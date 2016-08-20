package tr.com.idefix.android.view.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import javax.inject.Inject;
import tr.com.idefix.android.R;
import tr.com.idefix.android.components.SwitchButton;
import tr.com.idefix.android.internal.di.components.UserProfileActivityComponent;
import tr.com.idefix.android.presenter.IMyListPresenter;
import tr.com.idefix.android.view.MyListView;
import tr.com.idefix.android.view.activities.UserProfileActivity;
import tr.com.idefix.android.view.adapters.AlarmAdapter;
import tr.com.idefix.android.view.adapters.FavouritesAdapter;
import tr.com.idefix.data.entity.BasketItemResponseEntity;
import tr.com.idefix.domain.Alarm;
import tr.com.idefix.domain.Wish;

/**
 * Created by mustafaguven on 13.10.2015.
 */
public class MyListsFragment extends BaseFragment implements MyListView {

  private final int TRANSATION_DURATION = 500;
  @Inject IMyListPresenter presenter;
  @Bind(R.id.switchButton) SwitchButton switchButton;
  @Bind(R.id.rlFavouriteList) RelativeLayout rlFavouriteList;
  @Bind(R.id.rlNoFavourites) RelativeLayout rlNoFavourites;
  @Bind(R.id.rlAlarmList) RelativeLayout rlAlarmList;
  @Bind(R.id.rlNoAlarm) RelativeLayout rlNoAlarm;
  @Bind(R.id.rvFavourites) RecyclerView rvFavourites;
  @Bind(R.id.rvAlarm) RecyclerView rvAlarm;

  @Override public void onFetchFavouriteList(Wish wish) {
    ((UserProfileActivity) getActivity()).getBasketCount();
    if (wish == null) {
      rlNoFavourites.setVisibility(View.VISIBLE);
      rvFavourites.setVisibility(View.GONE);
      rvFavourites.setAdapter(null);
    } else {
      rlNoFavourites.setVisibility(View.GONE);
      rvFavourites.setVisibility(View.VISIBLE);
      FavouritesAdapter adapter = new FavouritesAdapter(getActivity(), wish.items(), presenter);
      rvFavourites.setAdapter(adapter);
    }
  }

  @Override public void onFetchAlarmList(Alarm alarm) {
    ((UserProfileActivity) getActivity()).getBasketCount();
    if (alarm == null) {
      rlNoAlarm.setVisibility(View.VISIBLE);
      rvAlarm.setVisibility(View.GONE);
      rvAlarm.setAdapter(null);
    } else {
      rlNoAlarm.setVisibility(View.GONE);
      rvAlarm.setVisibility(View.VISIBLE);
      AlarmAdapter alarmAdapter = new AlarmAdapter(getActivity(), alarm.items(), presenter);
      rvAlarm.setAdapter(alarmAdapter);
    }
  }

  @Override
  public void onMovedItemsFromCardToBasket(BasketItemResponseEntity basketItemResponseEntity) {
    ((UserProfileActivity) getActivity()).getBasketCount();
    Snackbar.make(getView(), R.string.product_added_to_basket, Snackbar.LENGTH_SHORT).show();
  }

  @Override public void onRemovedFromWishList(BasketItemResponseEntity basketItemResponseEntity) {
    Snackbar.make(getView(), basketItemResponseEntity.message(), Snackbar.LENGTH_SHORT).show();
    presenter.getFavouriteList();
  }

  @Override public void onError(Throwable throwable) {

  }

  @Override public void onRemovedFromAlarmList(BasketItemResponseEntity basketItemResponseEntity) {
    Snackbar.make(getView(), basketItemResponseEntity.message(), Snackbar.LENGTH_SHORT).show();
    presenter.getAlarmList();
  }

  @Override
  public void onMovedAlarmItemsFromCardToBasket(BasketItemResponseEntity basketItemResponseEntity) {
    ((UserProfileActivity) getActivity()).getBasketCount();
    Snackbar.make(getView(), R.string.product_added_to_basket, Snackbar.LENGTH_SHORT).show();
  }

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
  ) {
    View view = inflater.inflate(R.layout.fragment_my_list, container, false);
    ButterKnife.bind(this, view);

    switchButton.setTitles(getResources().getStringArray(R.array.my_list_switchbutton));
    switchButton.setOnSwitchListener(isFirstItemSelected -> {

      rlFavouriteList.setAlpha(0f);
      rlAlarmList.setAlpha(0f);
      if (isFirstItemSelected) {
        presenter.getFavouriteList();
        rlFavouriteList.animate()
            .setDuration(TRANSATION_DURATION)
            .alpha(1f)
            .setInterpolator(new DecelerateInterpolator(2))
            .start();
      } else {
        presenter.getAlarmList();
        rlAlarmList.animate()
            .setDuration(TRANSATION_DURATION)
            .alpha(1f)
            .setInterpolator(new DecelerateInterpolator(2))
            .start();
      }

      rlFavouriteList.setVisibility(isFirstItemSelected ? View.VISIBLE : View.GONE);
      rlAlarmList.setVisibility(isFirstItemSelected ? View.GONE : View.VISIBLE);
    });

    return view;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    rvFavourites.setHasFixedSize(true);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    rvFavourites.setLayoutManager(linearLayoutManager);
    rvFavourites.setItemAnimator(new DefaultItemAnimator());
    LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
    rvAlarm.setHasFixedSize(true);
    rvAlarm.setLayoutManager(linearLayoutManager2);
    rvAlarm.setItemAnimator(new DefaultItemAnimator());
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    getComponent(UserProfileActivityComponent.class).injectMyListFragment(this);

    presenter.setView(this);
    presenter.getFavouriteList();
  }

  @Override public void setBasketCount(int count) {
    //TODO: divide into interfaces activityview and fragmentview
  }
}
