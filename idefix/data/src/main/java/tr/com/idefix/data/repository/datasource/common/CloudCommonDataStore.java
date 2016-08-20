package tr.com.idefix.data.repository.datasource.common;

import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tr.com.idefix.data.entity.AvailableBankResponse;
import tr.com.idefix.data.entity.CityEntity;
import tr.com.idefix.data.entity.CountryEntity;
import tr.com.idefix.data.entity.EmptyRequestBody;
import tr.com.idefix.data.entity.JobEntity;
import tr.com.idefix.data.entity.PasswordRecoveryEntity;
import tr.com.idefix.data.entity.SaveNewCustomerRequestEntity;
import tr.com.idefix.data.entity.SaveNewCustomerResponseEntity;
import tr.com.idefix.data.entity.SozlesmeResponseEntity;
import tr.com.idefix.data.entity.StoreResponseEntity;
import tr.com.idefix.data.net.RestApiCommon;

public class CloudCommonDataStore implements CommonDataStore {

  private final RestApiCommon restApi;

  public CloudCommonDataStore(RestApiCommon restApi) {
    this.restApi = restApi;
  }

  @Override public Observable<List<CountryEntity>> getAvailableCountries() {

    return restApi.getAvailableCountries(new EmptyRequestBody())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<List<CityEntity>> getCitiesByCountryId(int id) {
    return restApi.getCitiesByCountryId(new EmptyRequestBody(), id)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<StoreResponseEntity> getRetailStores() {
    return restApi.getRetailStores(new EmptyRequestBody())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<List<JobEntity>> getJobs() {
    return restApi.getJobs(new EmptyRequestBody())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<PasswordRecoveryEntity> passwordRecovery(
      PasswordRecoveryEntity passwordRecoveryEntity
  ) {
    return restApi.passwordRecovery(passwordRecoveryEntity)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<SaveNewCustomerResponseEntity> saveNewCustomer(
      SaveNewCustomerRequestEntity saveNewCustomerRequestEntity
  ) {
    return restApi.saveNewCustomer(saveNewCustomerRequestEntity)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<SozlesmeResponseEntity> getSozlesme() {
    return restApi.getSozlesme(new EmptyRequestBody())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<AvailableBankResponse> getAvailableBankAccounts() {
    return restApi.getAvailableBankAccounts(new EmptyRequestBody())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
