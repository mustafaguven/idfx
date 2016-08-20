package tr.com.idefix.domain.interactor;

import tr.com.idefix.data.entity.SearchRequestEntity;
import tr.com.idefix.data.entity.SearchResponseEntity;

/**
 * Created by mustafaguven on 24.10.2015.
 */
public interface ISearchInteractor extends IInterActor {

  void search(
      DefaultSubscriber<SearchResponseEntity> subscriber, SearchRequestEntity searchRequestEntity
  );

  void catalog(
      DefaultSubscriber<SearchResponseEntity> defaultSubscriber,
      SearchRequestEntity searchRequestEntity
  );
}
