package tr.com.idefix.data.repository;

import rx.Observable;
import tr.com.idefix.data.entity.SearchRequestEntity;
import tr.com.idefix.data.entity.SearchResponseEntity;

/**
 * Created by utkan on 13/09/15.
 */
public interface ISearchRepository {

    Observable<SearchResponseEntity> search(SearchRequestEntity searchRequestEntity);

    Observable<SearchResponseEntity> catalog(SearchRequestEntity searchRequestEntity);

}
