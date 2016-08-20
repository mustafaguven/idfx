package tr.com.idefix.domain.interactor;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;
import tr.com.idefix.data.entity.SearchRequestEntity;
import tr.com.idefix.data.entity.SearchResponseEntity;
import tr.com.idefix.data.net.RestApiBaseService;
import tr.com.idefix.data.repository.ISearchRepository;
import tr.com.idefix.data.repository.SearchDataRepository;
import tr.com.idefix.domain.DomainApplication;
import tr.com.idefix.domain.DomainContext;

/**
 * Created by mustafaguven on 24.10.2015.
 */
public class SearchInteractor implements ISearchInteractor {

  private final ISearchRepository repository;
  DomainContext domainContext;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  public SearchInteractor() {
    this.repository = new SearchDataRepository();
    domainContext = DomainApplication.getInstance().getDomainApplicationComponent().domainContext();

    if (RestApiBaseService.getMemoryCache() == null) {
      RestApiBaseService.setMemoryCache(domainContext.getCache());
    }
  }

  @Override public void unSubscribe() {

  }

  @Override public void search(
      DefaultSubscriber<SearchResponseEntity> subscriber, SearchRequestEntity searchRequestEntity
  ) {


/*        if (apiCustomerEntityDataMapper == null) {
            apiCustomerEntityDataMapper = new CustomerEntityDataMapper();
        }*/

    compositeSubscription.add(repository.search(searchRequestEntity)
        //.map(apiCustomerEntityDataMapper::transform)
        .doOnNext(new Action1<SearchResponseEntity>() {
          @Override public void call(SearchResponseEntity searchResponseEntity) {
          }
        }).doOnError(throwable -> {
          Timber.e(throwable, "Can't search");
        }).subscribe(subscriber));
  }

  @Override public void catalog(
      DefaultSubscriber<SearchResponseEntity> subscriber, SearchRequestEntity searchRequestEntity
  ) {
    compositeSubscription.add(repository.catalog(searchRequestEntity)
        //.map(apiCustomerEntityDataMapper::transform)
        .doOnNext(searchResponseEntity -> {
        }).doOnError(throwable -> {
          Timber.e(throwable, "Can't get catalog");
        }).subscribe(subscriber));
  }
}
