package tr.com.idefix.data.net;

import java.util.List;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;
import tr.com.idefix.data.entity.AvailableBankResponse;
import tr.com.idefix.data.entity.CityEntity;
import tr.com.idefix.data.entity.CountryEntity;
import tr.com.idefix.data.entity.EmptyRequestBody;
import tr.com.idefix.data.entity.JobEntity;
import tr.com.idefix.data.entity.LogOutEntity;
import tr.com.idefix.data.entity.LoginEntity;
import tr.com.idefix.data.entity.LoginRequestEntity;
import tr.com.idefix.data.entity.PasswordRecoveryEntity;
import tr.com.idefix.data.entity.SaveNewCustomerRequestEntity;
import tr.com.idefix.data.entity.SaveNewCustomerResponseEntity;
import tr.com.idefix.data.entity.SettingsResponseEntity;
import tr.com.idefix.data.entity.SozlesmeResponseEntity;
import tr.com.idefix.data.entity.StoreResponseEntity;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApiCommon {

  /**
   * Retrieves an {@link Observable} which will emit a List of {@link LoginEntity}.
   *
   * @param loginRequestEntity used to try to login
   */
  @POST("/Login") Observable<LoginEntity> login(@Body LoginRequestEntity loginRequestEntity);

  @POST("/Logout") Observable<LogOutEntity> logout(@Body EmptyRequestBody emptyRequestBody);

  @POST("/GetAvailableCountries") Observable<List<CountryEntity>> getAvailableCountries(
      @Body EmptyRequestBody emptyRequestBody
  );

  @POST("/GetCitiesByCountryId") Observable<List<CityEntity>> getCitiesByCountryId(
      @Body EmptyRequestBody emptyRequestBody, @Query(value = "id", encodeValue = false) int id
  );

  @POST("/GetRetailStores") Observable<StoreResponseEntity> getRetailStores(
      @Body EmptyRequestBody emptyRequestBody
  );

  @POST("/GetAvailableJobs") Observable<List<JobEntity>> getJobs(
      @Body EmptyRequestBody emptyRequestBody
  );

  @POST("/PasswordRecovery") Observable<PasswordRecoveryEntity> passwordRecovery(
      @Body PasswordRecoveryEntity passwordRecoveryEntity
  );

  @POST("/SaveNewCustomer") Observable<SaveNewCustomerResponseEntity> saveNewCustomer(
      @Body SaveNewCustomerRequestEntity saveNewCustomerRequestEntity
  );

  @POST("/GetSettings") Observable<SettingsResponseEntity> getSettings(
      @Body EmptyRequestBody emptyRequestBody
  );

  @POST("/CustomerAgreement") Observable<SozlesmeResponseEntity> getSozlesme(
      @Body EmptyRequestBody emptyRequestBody
  );

  @POST("/GetAvailableBankAccounts") Observable<AvailableBankResponse> getAvailableBankAccounts(
      @Body EmptyRequestBody emptyRequestBody
  );
}
