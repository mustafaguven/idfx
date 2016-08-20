package tr.com.idefix.android.presenter;

import tr.com.idefix.data.entity.CustomerInfoRequestEntity;

/**
 * Created by mustafaguven on 13.10.2015.
 */
public interface IMyInformationPresenter extends Presenter {

  void onSave();

  void getUserInformation();

  void changeInfo(CustomerInfoRequestEntity customerInfoRequestEntity);

  void getCountries();

  void getCityByCountryId(int id);

  void onLogout();

  void changePassword(String currentPassword, String newPassword, String confirmNewPassword);
}
