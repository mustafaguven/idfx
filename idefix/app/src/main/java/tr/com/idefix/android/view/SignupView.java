package tr.com.idefix.android.view;

import tr.com.idefix.data.entity.SaveNewCustomerResponseEntity;

/**
 * Created by mustafaguven on 17.10.2015.
 */
public interface SignupView extends IView {

  void onNewCustomerSaved(SaveNewCustomerResponseEntity saveNewCustomerResponseEntity);
}
