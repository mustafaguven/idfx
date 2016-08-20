package tr.com.idefix.data.repository.datasource.search;

import javax.inject.Inject;
import javax.inject.Singleton;

import tr.com.idefix.data.net.RestApiSearchService;
import tr.com.idefix.data.repository.datasource.login.LoginDataStore;

/**
 * Factory that creates different implementations of {@link LoginDataStore}.
 */
@Singleton
public class SearchDataStoreFactory {

    @Inject
    public SearchDataStoreFactory() {
    }

    /**
     * Create {@link SearchDataStore} to retrieve data from the Cloud.
     */
    public SearchDataStore createCloudDataStore() {
        return new CloudSearchDataStore(new RestApiSearchService());
    }
}
