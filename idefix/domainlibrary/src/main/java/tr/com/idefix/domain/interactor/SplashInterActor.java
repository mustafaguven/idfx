package tr.com.idefix.domain.interactor;

import java.util.List;
import javax.inject.Inject;
import rx.subscriptions.CompositeSubscription;
import tr.com.idefix.data.repository.ISplashRepository;
import tr.com.idefix.data.repository.SplashDataRepository;
import tr.com.idefix.domain.Banner;
import tr.com.idefix.domain.DomainApplication;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.MainCategory;
import tr.com.idefix.domain.SettingsItem;
import tr.com.idefix.domain.mapper.SplashEntityDataMapper;

/**
 * Created by utkan on 05/09/15.
 */
public class SplashInterActor implements ISplashInterActor {

  private final ISplashRepository splashRepository;
  SplashEntityDataMapper splashEntityDataMapper;
  DomainContext domainContext;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject public SplashInterActor() {
    this.splashRepository = new SplashDataRepository();
    domainContext = DomainApplication.getInstance().getDomainApplicationComponent().domainContext();
  }

  @Override public void unSubscribe() {
    compositeSubscription.unsubscribe();
  }

  @Override public void getMainCategories(DefaultSubscriber<List<MainCategory>> subscriber) {

    if (splashEntityDataMapper == null) {
      splashEntityDataMapper = new SplashEntityDataMapper();
    }

    compositeSubscription.add(splashRepository.getMainCategories()
        .map(splashEntityDataMapper::transform)
        .subscribe(subscriber));
  }

  @Override public void getBanners(DefaultSubscriber<List<Banner>> subscriber) {

    if (splashEntityDataMapper == null) {
      splashEntityDataMapper = new SplashEntityDataMapper();
    }

    compositeSubscription.add(
        splashRepository.getBanners().map(splashEntityDataMapper::transform).subscribe(subscriber));
  }

  @Override public void getSettings(DefaultSubscriber<SettingsItem> subscriber) {

    if (splashEntityDataMapper == null) {
      splashEntityDataMapper = new SplashEntityDataMapper();
    }

    SettingsItem settingsItem = domainContext.getSettingsItem();

    if (settingsItem == null) {
      splashRepository.getSettings()
          .map(splashEntityDataMapper::transform)
          .doOnNext(domainContext::setSettingsItem)
          .subscribe(subscriber);
    } else {
      subscriber.onNext(settingsItem);
      subscriber.onCompleted();
    }
  }
}
