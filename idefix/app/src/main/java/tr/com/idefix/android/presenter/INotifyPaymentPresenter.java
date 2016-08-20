package tr.com.idefix.android.presenter;

public interface INotifyPaymentPresenter extends Presenter {

  void getAvailableBankAccounts();

  void sendNotifyPayment(String orderCode, String bankInformation, String date, String description);
}
