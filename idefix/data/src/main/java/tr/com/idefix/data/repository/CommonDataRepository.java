package tr.com.idefix.data.repository;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
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
import tr.com.idefix.data.repository.datasource.common.CommonDataStore;
import tr.com.idefix.data.repository.datasource.common.CommonDataStoreFactory;

/**
 * Created by utkan on 01/09/15.
 */
@Singleton public class CommonDataRepository implements ICommonRepository {

  private final CommonDataStoreFactory commonStoreFactory;

  @Inject public CommonDataRepository() {
    this.commonStoreFactory = new CommonDataStoreFactory();
  }

  @Override public Observable<List<CountryEntity>> getAvailableCountries() {
    final CommonDataStore commonDataStore = this.commonStoreFactory.createCloudDataStore();
    return commonDataStore.getAvailableCountries();
  }

  @Override public Observable<List<CityEntity>> getCitiesByCountryId(int id) {
    final CommonDataStore commonDataStore = this.commonStoreFactory.createCloudDataStore();
    return commonDataStore.getCitiesByCountryId(id);
  }

  @Override public Observable<StoreResponseEntity> getRetailStores() {
    final CommonDataStore commonDataStore = this.commonStoreFactory.createCloudDataStore();
    return commonDataStore.getRetailStores();
  }

  @Override public Observable<List<JobEntity>> getJobs() {
    final CommonDataStore commonDataStore = this.commonStoreFactory.createCloudDataStore();
    return commonDataStore.getJobs();
  }

  @Override public Observable<PasswordRecoveryEntity> passwordRecovery(
      PasswordRecoveryEntity passwordRecoveryEntity
  ) {
    final CommonDataStore commonDataStore = this.commonStoreFactory.createCloudDataStore();
    return commonDataStore.passwordRecovery(passwordRecoveryEntity);
  }

  @Override public Observable<SaveNewCustomerResponseEntity> saveNewCustomer(
      SaveNewCustomerRequestEntity saveNewCustomerRequestEntity
  ) {
    final CommonDataStore commonDataStore = this.commonStoreFactory.createCloudDataStore();
    return commonDataStore.saveNewCustomer(saveNewCustomerRequestEntity);
  }

  @Override public Observable<SozlesmeResponseEntity> getSozlesme() {
    final CommonDataStore commonDataStore = this.commonStoreFactory.createCloudDataStore();
    return commonDataStore.getSozlesme();
  }

  @Override public Observable<AvailableBankResponse> getAvailableBankAccounts() {
    final CommonDataStore commonDataStore = this.commonStoreFactory.createCloudDataStore();
    return commonDataStore.getAvailableBankAccounts();
  }
}
