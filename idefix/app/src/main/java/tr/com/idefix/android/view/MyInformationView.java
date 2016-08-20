package tr.com.idefix.android.view;

import java.util.List;
import tr.com.idefix.domain.City;
import tr.com.idefix.domain.Country;
import tr.com.idefix.domain.CustomerInfo;

/**
 * Created by mustafaguven on 13.10.2015.
 */
public interface MyInformationView extends IView {

  void onFetchCountries(List<Country> countryList);

  void onFetchCitiesByCountryId(List<City> cities);

  void onFetchUserInformation(CustomerInfo customerInfo);

  void onCustomerInfoUpdated();

  void onPasswordChanged(String message);

  void onSave();

  void onLogout();

  void onError(Throwable e);
}
