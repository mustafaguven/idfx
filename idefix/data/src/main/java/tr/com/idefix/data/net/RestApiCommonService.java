package tr.com.idefix.data.net;

import android.text.TextUtils;
import android.util.Log;
import java.util.List;
import retrofit.http.Body;
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
import tr.com.idefix.data.exception.LoginFailException;
import tr.com.idefix.data.exception.ResponseFailException;
import tr.com.idefix.data.exception.ResponseNullException;

/**
 * RestApi for retrieving data from the network.
 */
public class RestApiCommonService extends RestApiBaseService<RestApiCommon>
    implements RestApiCommon {

  static final String API_COMMON_URL = API_BASE_URL + "ApiCommon/";

  public RestApiCommonService() {

    super(API_COMMON_URL, RestApiCommon.class);
  }

  public RestApiCommon getApi() {

    return restApi;
  }

  @Override public Observable<LoginEntity> login(LoginRequestEntity loginRequestEntity) {

    if (memoryCache != null) {
      memoryCache.removeCache(LOGIN_COOKIES);
    }
    if (cookies != null) {
      cookies.clear();
    }
    return getApi().login(loginRequestEntity).flatMap(entity -> {
      if (entity != null) {
        if (entity.success() != null && entity.success()) {
          if (entity.loginSubEntity() != null && entity.loginSubEntity().success()) {

            if (memoryCache != null) {
              Log.i("<->", "put cache");
              memoryCache.putCache(LOGIN_COOKIES, cookies);
            }
            return Observable.just(entity);
          } else {
            String eMsg = "";
            if (!TextUtils.isEmpty(entity.errorMessage())) {
              eMsg = entity.errorMessage();
            } else {
              if (entity.loginSubEntity() != null && !TextUtils.isEmpty(
                  entity.loginSubEntity().errorMessage())) {
                eMsg = entity.loginSubEntity().errorMessage();
              } else {
                eMsg = "";
              }
            }
            if (cookies != null) {
              cookies.clear();
            }
            memoryCache.removeCache(LOGIN_COOKIES);
            return Observable.error(new LoginFailException(eMsg));
          }
        } else {
          if (cookies != null) {
            cookies.clear();
          }
          memoryCache.removeCache(LOGIN_COOKIES);
          return Observable.error(new ResponseFailException());
        }
      } else {
        if (cookies != null) {
          cookies.clear();
        }
        memoryCache.removeCache(LOGIN_COOKIES);
        return Observable.error(new ResponseNullException());
      }
    });
  }

  @Override
  public Observable<List<CountryEntity>> getAvailableCountries(EmptyRequestBody emptyRequestBody) {
    return getApi().getAvailableCountries(emptyRequestBody);
  }

  @Override public Observable<List<CityEntity>> getCitiesByCountryId(
      @Body EmptyRequestBody emptyRequestBody, @Query(value = "id", encodeValue = false) int id
  ) {
    return getApi().getCitiesByCountryId(emptyRequestBody, id);
  }

  @Override
  public Observable<StoreResponseEntity> getRetailStores(@Body EmptyRequestBody emptyRequestBody) {
    return getApi().getRetailStores(emptyRequestBody).flatMap(entity -> {
      if (entity != null && entity.getSuccess() != null && entity.getSuccess()) {
        return Observable.just(entity);
      } else {
        if (entity != null && !TextUtils.isEmpty(entity.getMessage())) {
          return Observable.error(new ResponseFailException(entity.getMessage()));
        }
        return Observable.error(new ResponseNullException());
      }
    });
  }

  @Override public Observable<List<JobEntity>> getJobs(@Body EmptyRequestBody emptyRequestBody) {
    return getApi().getJobs(emptyRequestBody);
  }

  @Override public Observable<LogOutEntity> logout(
      @Body EmptyRequestBody emptyRequestBody
  ) {

    return getApi().logout(emptyRequestBody).flatMap(entity -> {
      if (entity != null && entity.success()) {
        return Observable.just(entity);
      } else {
        if (entity != null && !TextUtils.isEmpty(entity.errorMessage())) {
          return Observable.error(new ResponseFailException(entity.errorMessage()));
        }
        return Observable.error(new ResponseNullException());
      }
    });
  }

  @Override public Observable<PasswordRecoveryEntity> passwordRecovery(
      @Body PasswordRecoveryEntity passwordRecoveryEntity
  ) {
    return getApi().passwordRecovery(passwordRecoveryEntity).flatMap(Observable::just);
  }

  @Override public Observable<SaveNewCustomerResponseEntity> saveNewCustomer(
      @Body SaveNewCustomerRequestEntity saveNewCustomerRequestEntity
  ) {
    return getApi().saveNewCustomer(saveNewCustomerRequestEntity).flatMap(Observable::just);
  }

  @Override
  public Observable<SettingsResponseEntity> getSettings(@Body EmptyRequestBody emptyRequestBody) {
    return getApi().getSettings(emptyRequestBody).flatMap(Observable::just);
  }

  @Override
  public Observable<SozlesmeResponseEntity> getSozlesme(@Body EmptyRequestBody emptyRequestBody) {
    return getApi().getSozlesme(emptyRequestBody).flatMap(entity -> {
      if (entity == null) {
        return Observable.error(new ResponseNullException("Sozlesme servisine bağlanılamadı"));
      } else {
        return Observable.just(entity);
      }
    });
  }

  @Override public Observable<AvailableBankResponse> getAvailableBankAccounts(
      @Body EmptyRequestBody emptyRequestBody
  ) {
    return getApi().getAvailableBankAccounts(emptyRequestBody).flatMap(entity -> {
      if (entity != null && entity.success() != null && entity.success()) {
        return Observable.just(entity);
      } else {
        if (entity != null && !TextUtils.isEmpty(entity.message())) {
          return Observable.error(new ResponseFailException(entity.message()));
        }
        return Observable.error(new ResponseNullException());
      }
    });
  }
}
