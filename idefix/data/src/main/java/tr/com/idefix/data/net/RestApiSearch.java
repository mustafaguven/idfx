package tr.com.idefix.data.net;


import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;
import tr.com.idefix.data.entity.SearchRequestEntity;
import tr.com.idefix.data.entity.SearchResponseEntity;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApiSearch {

    @POST("/Search/")
    Observable<SearchResponseEntity> search(
            @Body SearchRequestEntity searchRequestEntity);


    @POST("/CatalogProducts/")
    Observable<SearchResponseEntity> catalog(
            @Body SearchRequestEntity searchRequestEntity);
}
