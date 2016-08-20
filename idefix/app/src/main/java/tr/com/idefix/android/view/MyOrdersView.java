package tr.com.idefix.android.view;

import tr.com.idefix.data.entity.CustomerOrdersBaseEntity;

/**
 * Created by mustafaguven on 20.10.2015.
 */
public interface MyOrdersView extends IView {

  void onFetchOrders(CustomerOrdersBaseEntity customerOrdersBaseEntity);
}
