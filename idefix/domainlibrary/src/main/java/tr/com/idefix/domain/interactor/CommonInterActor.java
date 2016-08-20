package tr.com.idefix.domain.interactor;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import tr.com.idefix.data.entity.AvailableBankResponse;
import tr.com.idefix.data.entity.PasswordRecoveryEntity;
import tr.com.idefix.data.entity.SaveNewCustomerRequestEntity;
import tr.com.idefix.data.entity.SaveNewCustomerResponseEntity;
import tr.com.idefix.data.entity.SozlesmeResponseEntity;
import tr.com.idefix.data.repository.CommonDataRepository;
import tr.com.idefix.data.repository.ICommonRepository;
import tr.com.idefix.domain.City;
import tr.com.idefix.domain.Country;
import tr.com.idefix.domain.DomainApplication;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.Store;
import tr.com.idefix.domain.mapper.CommonEntitiesDataMapper;

/**
 * Created by utkan on 01/09/15.
 */
public class CommonInterActor implements ICommonInterActor {

  private final ICommonRepository commonRepository;
  CommonEntitiesDataMapper commonEntitiesDataMapper;

  DomainContext domainContext;
  List<Subscriber> subscriberList;

  @Inject public CommonInterActor() {
    this.commonRepository = new CommonDataRepository();
    domainContext = DomainApplication.getInstance().getDomainApplicationComponent().domainContext();

    subscriberList = new ArrayList<>();
  }

  @Override public void unSubscribe() {
    //TODO: add volley TAG logic with iterator impl and remove
    for (Subscriber subscriber : subscriberList) {
      subscriber.unsubscribe();
    }
    subscriberList.clear();
  }

  @Override public void getAvailableCountries(DefaultSubscriber<List<Country>> subscriber) {

    subscriberList.add(subscriber);

    if (commonEntitiesDataMapper == null) {
      commonEntitiesDataMapper = new CommonEntitiesDataMapper();
    }

    List<Country> listCached = domainContext.getCachedCountries();

    if (listCached == null) {
      commonRepository.getAvailableCountries()
          .map(commonEntitiesDataMapper::transform)
          .doOnNext(domainContext::cacheCountries)
          .subscribe(subscriber);
    } else {
      subscriber.onNext(listCached);
      subscriber.onCompleted();
    }
  }

  @Override public void getRetailStores(DefaultSubscriber<List<Store>> subscriber) {

    subscriberList.add(subscriber);

    if (commonEntitiesDataMapper == null) {
      commonEntitiesDataMapper = new CommonEntitiesDataMapper();
    }

    List<Store> listCached = domainContext.getCachedStores();

    if (listCached == null) {
      commonRepository.getRetailStores()
          .map(commonEntitiesDataMapper::transform)
          .doOnNext(domainContext::cacheStores)
          .subscribe(subscriber);
    } else {
      subscriber.onNext(listCached);
      subscriber.onCompleted();
    }
  }

  @Override public void passwordRecovery(
      DefaultSubscriber<PasswordRecoveryEntity> subscriber,
      PasswordRecoveryEntity passwordRecoveryEntity
  ) {
    subscriberList.add(subscriber);

    if (commonEntitiesDataMapper == null) {
      commonEntitiesDataMapper = new CommonEntitiesDataMapper();
    }

    commonRepository.passwordRecovery(passwordRecoveryEntity).subscribe(subscriber);
  }

  @Override public void saveNewCustomer(
      DefaultSubscriber<SaveNewCustomerResponseEntity> subscriber,
      SaveNewCustomerRequestEntity saveNewCustomerRequestEntity
  ) {
    subscriberList.add(subscriber);

    if (commonEntitiesDataMapper == null) {
      commonEntitiesDataMapper = new CommonEntitiesDataMapper();
    }

    commonRepository.saveNewCustomer(saveNewCustomerRequestEntity).subscribe(subscriber);
  }

  @Override public void getCitiesByCountryId(int id, DefaultSubscriber<List<City>> subscriber) {

    subscriberList.add(subscriber);

    if (commonEntitiesDataMapper == null) {
      commonEntitiesDataMapper = new CommonEntitiesDataMapper();
    }

    List<City> listCached = domainContext.getCachedCitiesByCountryId(id);

    if (listCached == null) {
      commonRepository.getCitiesByCountryId(id)
          .map(commonEntitiesDataMapper::transformCity)
          .doOnNext(cities -> domainContext.cacheCitiesByCountryId(id, cities))
          .subscribe(subscriber);
    } else {
      subscriber.onNext(listCached);
      subscriber.onCompleted();
    }
  }

  @Override public void getSozlesme(DefaultSubscriber<SozlesmeResponseEntity> subscriber) {

    subscriberList.add(subscriber);

    if (commonEntitiesDataMapper == null) {
      commonEntitiesDataMapper = new CommonEntitiesDataMapper();
    }

    commonRepository.getSozlesme().subscribe(subscriber);
  }

  @Override public void getAvailableBankAccounts(
      DefaultSubscriber<AvailableBankResponse> subscriber
  ) {
    subscriberList.add(subscriber);

    if (commonEntitiesDataMapper == null) {
      commonEntitiesDataMapper = new CommonEntitiesDataMapper();
    }

    commonRepository.getAvailableBankAccounts().subscribe(subscriber);
  }
}
