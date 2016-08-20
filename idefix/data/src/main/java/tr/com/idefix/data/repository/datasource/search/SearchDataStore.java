package tr.com.idefix.data.repository.datasource.search;

import rx.Observable;
import tr.com.idefix.data.entity.SearchRequestEntity;
import tr.com.idefix.data.entity.SearchResponseEntity;

public interface SearchDataStore {

    Observable<SearchResponseEntity> search(SearchRequestEntity searchRequestEntity);

    Observable<SearchResponseEntity> catalog(SearchRequestEntity searchRequestEntity);
}
