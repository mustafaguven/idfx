package tr.com.idefix.data.repository.datasource.splash;

import javax.inject.Inject;
import javax.inject.Singleton;

import tr.com.idefix.data.net.RestApiCatalogService;
import tr.com.idefix.data.net.RestApiCommonService;

/**
 * Factory that creates different implementations of {@link SplashDataStore}.
 */
@Singleton
public class SplashDataStoreFactory {

    @Inject
    public SplashDataStoreFactory() {
    }

    /**
     * Create {@link SplashDataStore} to retrieve data from the Cloud.
     */
    public SplashDataStore createCloudDataStore() {
        return new CloudSplashDataStore(new RestApiCatalogService(), new RestApiCommonService());
    }
}
