package tr.com.idefix.android.presenter;

import tr.com.idefix.data.entity.SaveNewCustomerRequestEntity;

/**
 * Created by mustafaguven on 17.10.2015.
 */
public interface ISignupPresenter extends Presenter {

  void saveNewCustomer(SaveNewCustomerRequestEntity saveNewCustomerRequestEntity);
}
