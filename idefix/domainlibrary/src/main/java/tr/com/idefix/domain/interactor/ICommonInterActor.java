package tr.com.idefix.domain.interactor;

import java.util.List;
import tr.com.idefix.data.entity.AvailableBankResponse;
import tr.com.idefix.data.entity.PasswordRecoveryEntity;
import tr.com.idefix.data.entity.SaveNewCustomerRequestEntity;
import tr.com.idefix.data.entity.SaveNewCustomerResponseEntity;
import tr.com.idefix.data.entity.SozlesmeResponseEntity;
import tr.com.idefix.domain.City;
import tr.com.idefix.domain.Country;
import tr.com.idefix.domain.Store;

/**
 * Created by utkan on 01/09/15.
 */
public interface ICommonInterActor extends IInterActor {

  void getAvailableCountries(DefaultSubscriber<List<Country>> subscriber);

  void getCitiesByCountryId(int id, DefaultSubscriber<List<City>> subscriber);

  void getRetailStores(DefaultSubscriber<List<Store>> subscriber);

  void passwordRecovery(
      DefaultSubscriber<PasswordRecoveryEntity> subscriber,
      PasswordRecoveryEntity passwordRecoveryEntity
  );

  void saveNewCustomer(
      DefaultSubscriber<SaveNewCustomerResponseEntity> subscriber,
      SaveNewCustomerRequestEntity saveNewCustomerRequestEntity
  );

  void getSozlesme(DefaultSubscriber<SozlesmeResponseEntity> subscriber);

  void getAvailableBankAccounts(DefaultSubscriber<AvailableBankResponse> subscriber);
}
