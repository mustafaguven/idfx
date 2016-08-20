package tr.com.idefix.data.repository.datasource.common;

import javax.inject.Inject;
import javax.inject.Singleton;

import tr.com.idefix.data.net.RestApiCommonService;
import tr.com.idefix.data.repository.datasource.login.LoginDataStore;

/**
 * Factory that creates different implementations of {@link LoginDataStore}.
 */
@Singleton
public class CommonDataStoreFactory {

    @Inject
    public CommonDataStoreFactory() {
    }

    /**
     * Create {@link CommonDataStore} to retrieve data from the Cloud.
     */
    public CommonDataStore createCloudDataStore() {
        return new CloudCommonDataStore(new RestApiCommonService());
    }
}
