package tr.com.idefix.android.view;

import tr.com.idefix.data.entity.OrderDetail;

public interface IOrderDetailView extends IView {

  void fetchOrderDetail(OrderDetail orderDetail);
}
