package tr.com.idefix.data.repository.datasource.catalog;

import javax.inject.Inject;
import javax.inject.Singleton;

import tr.com.idefix.data.net.RestApiCatalogService;
import tr.com.idefix.data.repository.datasource.login.LoginDataStore;

/**
 * Factory that creates different implementations of {@link LoginDataStore}.
 */
@Singleton
public class CatalogDataStoreFactory {

    @Inject
    public CatalogDataStoreFactory() {
    }

    /**
     * Create {@link CatalogDataStore} to retrieve data from the Cloud.
     */
    public CatalogDataStore createCloudDataStore() {
        return new CloudCatalogDataStore(new RestApiCatalogService());
    }
}
