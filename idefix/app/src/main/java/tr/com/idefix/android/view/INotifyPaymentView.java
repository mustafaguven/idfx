package tr.com.idefix.android.view;

import java.util.List;
import tr.com.idefix.data.entity.BankEntity;
import tr.com.idefix.data.entity.SendPaymentInfoForOrderResponse;

public interface INotifyPaymentView extends IView {

  void fetchBankAccounts(List<BankEntity> bankEntities);

  void sentPaymentInfoForOrderSuccesfully(SendPaymentInfoForOrderResponse response);
}
