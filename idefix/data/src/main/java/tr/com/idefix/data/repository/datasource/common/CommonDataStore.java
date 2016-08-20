package tr.com.idefix.data.repository.datasource.common;

import java.util.List;

import rx.Observable;
import tr.com.idefix.data.entity.AvailableBankResponse;
import tr.com.idefix.data.entity.CityEntity;
import tr.com.idefix.data.entity.CountryEntity;
import tr.com.idefix.data.entity.JobEntity;
import tr.com.idefix.data.entity.PasswordRecoveryEntity;
import tr.com.idefix.data.entity.SaveNewCustomerRequestEntity;
import tr.com.idefix.data.entity.SaveNewCustomerResponseEntity;
import tr.com.idefix.data.entity.SozlesmeResponseEntity;
import tr.com.idefix.data.entity.StoreResponseEntity;

public interface CommonDataStore {

    Observable<List<CountryEntity>> getAvailableCountries();

    Observable<List<CityEntity>> getCitiesByCountryId(int id);

    Observable<StoreResponseEntity> getRetailStores();

    Observable<List<JobEntity>> getJobs();

    Observable<PasswordRecoveryEntity> passwordRecovery(PasswordRecoveryEntity passwordRecoveryEntity);

    Observable<SaveNewCustomerResponseEntity> saveNewCustomer(SaveNewCustomerRequestEntity saveNewCustomerRequestEntity);

    Observable<SozlesmeResponseEntity> getSozlesme();

    Observable<AvailableBankResponse> getAvailableBankAccounts();


}
