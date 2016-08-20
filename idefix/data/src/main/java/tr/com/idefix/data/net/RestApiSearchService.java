package tr.com.idefix.data.net;

import retrofit.http.Body;
import rx.Observable;
import tr.com.idefix.data.entity.SearchRequestEntity;
import tr.com.idefix.data.entity.SearchResponseEntity;

/**
 * Created by mustafaguven on 24.10.2015.
 */
public class RestApiSearchService extends RestApiBaseService<RestApiSearch> implements RestApiSearch {

    static final String API_CATALOG_URL = API_BASE_URL + "ApiCatalog/";

    public RestApiSearchService() {
        super(API_CATALOG_URL, RestApiSearch.class);
    }

    @Override
    public Observable<SearchResponseEntity> search(@Body SearchRequestEntity searchRequestEntity) {
        return getApi()
                .search(searchRequestEntity)
                .flatMap(value -> Observable.just(value))
                ;
    }

    @Override
    public Observable<SearchResponseEntity> catalog(@Body SearchRequestEntity searchRequestEntity) {
        return getApi()
                .catalog(searchRequestEntity)
                .flatMap(value -> Observable.just(value))
                ;
    }

    public RestApiSearch getApi() {
        return restApi;
    }
}
