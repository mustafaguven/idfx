package tr.com.idefix.android.mapper;

import javax.inject.Inject;
import tr.com.idefix.android.internal.di.PerActivity;

/**
 * Created by mustafaguven on 13.10.2015.
 */

@PerActivity public class UserProfileDataMapper {

  @Inject public UserProfileDataMapper() {
  }

/*    public CustomerInfoModel transform(CustomerInfo customerInfo) {
        if (customerInfo != null) {
            CustomerInfoModel customerInfoModel = new CustomerInfoModel();
            customerInfoModel
                    .firstName(customerInfo.firstName())
                    .fullName(customerInfo.firstName() + " " + customerInfo.lastName())
                    .lastName(customerInfo.lastName())
                    .username(customerInfo.username())
            ;
            return customerInfoModel;
        }
        return null;
    }*/
}
