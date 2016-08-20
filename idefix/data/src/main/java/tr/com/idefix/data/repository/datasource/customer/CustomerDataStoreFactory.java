package tr.com.idefix.data.repository.datasource.customer;

import javax.inject.Inject;
import javax.inject.Singleton;

import tr.com.idefix.data.net.RestApiCustomerService;
import tr.com.idefix.data.repository.datasource.login.LoginDataStore;

/**
 * Factory that creates different implementations of {@link LoginDataStore}.
 */
@Singleton
public class CustomerDataStoreFactory {

    @Inject
    public CustomerDataStoreFactory() {
    }

    /**
     * Create {@link CustomerDataStore} to retrieve data from the Cloud.
     */
    public CustomerDataStore createCloudDataStore() {
        return new CloudCustomerDataStore(new RestApiCustomerService());
    }
}
