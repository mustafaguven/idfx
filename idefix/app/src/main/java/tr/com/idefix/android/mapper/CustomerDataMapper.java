package tr.com.idefix.android.mapper;

import javax.inject.Inject;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.model.CustomerInfoModel;
import tr.com.idefix.domain.CustomerInfo;

/**
 * Created by utkan on 13/09/15.
 */
@PerActivity public class CustomerDataMapper {

  @Inject public CustomerDataMapper() {
  }

  public CustomerInfoModel transform(CustomerInfo customerInfo) {
    if (customerInfo != null) {
      CustomerInfoModel customerInfoModel = new CustomerInfoModel();
      customerInfoModel.firstName(customerInfo.firstName())
          .fullName(customerInfo.firstName() + " " + customerInfo.lastName())
          .lastName(customerInfo.lastName())
          .username(customerInfo.username());
      return customerInfoModel;
    }
    return null;
  }
}
