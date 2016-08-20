package tr.com.idefix.data.repository.datasource.search;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tr.com.idefix.data.entity.SearchRequestEntity;
import tr.com.idefix.data.entity.SearchResponseEntity;
import tr.com.idefix.data.net.RestApiSearch;


public class CloudSearchDataStore implements SearchDataStore {

    private final RestApiSearch restApi;


    public CloudSearchDataStore(RestApiSearch restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<SearchResponseEntity> search(SearchRequestEntity searchRequestEntity) {
        return restApi
                .search(searchRequestEntity)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    @Override
    public Observable<SearchResponseEntity> catalog(SearchRequestEntity searchRequestEntity) {
        return restApi
                .catalog(searchRequestEntity)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }
}
