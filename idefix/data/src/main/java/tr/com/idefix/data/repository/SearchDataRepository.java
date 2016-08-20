package tr.com.idefix.data.repository;

import javax.inject.Inject;

import rx.Observable;
import tr.com.idefix.data.entity.SearchRequestEntity;
import tr.com.idefix.data.entity.SearchResponseEntity;
import tr.com.idefix.data.repository.datasource.search.SearchDataStore;
import tr.com.idefix.data.repository.datasource.search.SearchDataStoreFactory;

/**
 * Created by mustafaguven on 24.10.2015.
 */
public class SearchDataRepository implements ISearchRepository {

    private final SearchDataStoreFactory apiSearchDataStoreFactory;

    @Inject
    public SearchDataRepository() {
        this.apiSearchDataStoreFactory = new SearchDataStoreFactory();
    }

    @Override
    public Observable<SearchResponseEntity> search(SearchRequestEntity searchRequestEntity) {
        final SearchDataStore apiSearchDataStore = apiSearchDataStoreFactory.createCloudDataStore();
        return apiSearchDataStore.search(searchRequestEntity);
    }

    @Override
    public Observable<SearchResponseEntity> catalog(SearchRequestEntity searchRequestEntity) {
        final SearchDataStore apiSearchDataStore = apiSearchDataStoreFactory.createCloudDataStore();
        return apiSearchDataStore.catalog(searchRequestEntity);
    }
}
