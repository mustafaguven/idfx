package tr.com.idefix.data.repository.datasource.login;

import javax.inject.Inject;
import javax.inject.Singleton;

import tr.com.idefix.data.net.RestApiCommonService;

/**
 * Factory that creates different implementations of {@link LoginDataStore}.
 */
@Singleton
public class LoginDataStoreFactory {

    @Inject
    public LoginDataStoreFactory() {
    }

    /**
     * Create {@link LoginDataStore} to retrieve data from the Cloud.
     */
    public LoginDataStore createCloudDataStore() {
        return new CloudLoginDataStore(new RestApiCommonService());
    }
}
