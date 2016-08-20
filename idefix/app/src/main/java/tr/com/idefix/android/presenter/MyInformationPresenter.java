package tr.com.idefix.android.presenter;

import android.text.TextUtils;
import java.util.List;
import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.R;
import tr.com.idefix.android.mapper.UserProfileDataMapper;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.android.view.MyInformationView;
import tr.com.idefix.android.view.fragments.MyInformationFragment;
import tr.com.idefix.data.entity.ChangePasswordRequestEntity;
import tr.com.idefix.data.entity.ChangePasswordResponseEntity;
import tr.com.idefix.data.entity.CustomerInfoRequestEntity;
import tr.com.idefix.data.entity.CustomerInfoResponseEntity;
import tr.com.idefix.domain.City;
import tr.com.idefix.domain.Country;
import tr.com.idefix.domain.CustomerInfo;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.LogOut;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICommonInterActor;
import tr.com.idefix.domain.interactor.ICustomerInterActor;
import tr.com.idefix.domain.interactor.ISignInInterActor;

/**
 * Created by mustafaguven on 13.10.2015.
 */
public class MyInformationPresenter implements IMyInformationPresenter {

  DomainContext domainContext;
  MyInformationView view;

  UserProfileDataMapper userProfileDataMapper;
  ISignInInterActor signInInterActor;
  ICommonInterActor commonInterActor;
  ICustomerInterActor customerInterActor;

  public MyInformationPresenter(
      UserProfileDataMapper userProfileDataMapper, ISignInInterActor signInInterActor,
      ICommonInterActor commonInterActor, ICustomerInterActor customerInterActor
  ) {
    this.userProfileDataMapper = userProfileDataMapper;
    this.signInInterActor = signInInterActor;
    this.commonInterActor = commonInterActor;
    this.customerInterActor = customerInterActor;

    domainContext =
        ApplicationController.getInstance().getDomainApplicationComponent().domainContext();
  }

  @Override public void onSave() {

  }

  @Override public void getUserInformation() {
    customerInterActor.getInfo(new DefaultSubscriber<CustomerInfo>() {
      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.hideProgress();
      }

      @Override public void onNext(CustomerInfo customerInfo) {
        super.onNext(customerInfo);
        view.onFetchUserInformation(customerInfo);
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
      }
    });
  }

  @Override public void changeInfo(CustomerInfoRequestEntity customerInfoRequestEntity) {
    customerInterActor.changeInfo(new DefaultSubscriber<CustomerInfoResponseEntity>() {
      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.onError(e);
        view.hideProgress();
      }

      @Override public void onNext(CustomerInfoResponseEntity responseEntity) {
        super.onNext(responseEntity);
        view.onCustomerInfoUpdated();
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
      }
    }, customerInfoRequestEntity);
  }

  @Override public void getCountries() {
    commonInterActor.getAvailableCountries(new DefaultSubscriber<List<Country>>() {
      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.hideProgress();
      }

      @Override public void onNext(List<Country> countries) {
        super.onNext(countries);
/*                ArrayList<String> list = new ArrayList<>();
                for (Country country : countries ) {
                    list.add(country.text());
                }*/
        view.onFetchCountries(countries);
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
      }
    });
  }

  @Override public void getCityByCountryId(int id) {
    commonInterActor.getCitiesByCountryId(id, new DefaultSubscriber<List<City>>() {
      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.hideProgress();
      }

      @Override public void onNext(List<City> cities) {
        super.onNext(cities);
        view.onFetchCitiesByCountryId(cities);
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
      }
    });
  }

  @Override public void onLogout() {
    signInInterActor.logout(
        domainContext.getLoggedInUser().sessionObject(), new DefaultSubscriber<LogOut>() {

          @Override public void onStart() {
            super.onStart();
            view.showProgress();
          }

          @Override public void onNext(LogOut logOut) {
            super.onNext(logOut);
          }

          @Override public void onCompleted() {
            super.onCompleted();
            view.hideProgress();
            view.onLogout();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            view.onError(e);
            view.hideProgress();
          }
        });
  }

  @Override public void changePassword(
      String currentPassword, String newPassword, String confirmNewPassword
  ) {
    MyInformationFragment fragment = (MyInformationFragment) view;
    if (TextUtils.isEmpty(currentPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(
        confirmNewPassword)) {
      view.onPasswordChanged(fragment.getActivity().getString(R.string.fill_in_the_blanks));
    } else if (!newPassword.contentEquals(confirmNewPassword)) {
      view.onPasswordChanged(fragment.getActivity().getString(R.string.passwords_are_not_same));
    } else {
      ChangePasswordRequestEntity changePasswordRequestEntity = new ChangePasswordRequestEntity();
      changePasswordRequestEntity.setOldpassword(currentPassword);
      changePasswordRequestEntity.setNewpassword(newPassword);
      changePasswordRequestEntity.setConfirmpassword(confirmNewPassword);
      customerInterActor.changePassword(new DefaultSubscriber<ChangePasswordResponseEntity>() {
        @Override public void onStart() {
          super.onStart();
          view.showProgress();
        }

        @Override public void onError(Throwable e) {
          super.onError(e);
          view.hideProgress();
          view.onPasswordChanged(e.getMessage());
        }

        @Override public void onNext(ChangePasswordResponseEntity changePasswordResponseEntity) {
          super.onNext(changePasswordResponseEntity);
          view.onPasswordChanged(changePasswordResponseEntity.result());
        }

        @Override public void onCompleted() {
          super.onCompleted();
          view.hideProgress();
        }
      }, changePasswordRequestEntity);
    }
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  @Override public void setView(IView iView) {
    this.view = (MyInformationView) iView;
  }
}
