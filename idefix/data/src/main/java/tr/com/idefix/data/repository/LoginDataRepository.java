package tr.com.idefix.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import mu.comon.utils.FileUtils;
import rx.Observable;
import tr.com.idefix.data.entity.CustomPropertiesEntity;
import tr.com.idefix.data.entity.LogOutEntity;
import tr.com.idefix.data.entity.LoginEntity;
import tr.com.idefix.data.entity.LoginRequestEntity;
import tr.com.idefix.data.repository.datasource.login.LoginDataStore;
import tr.com.idefix.data.repository.datasource.login.LoginDataStoreFactory;

/**
 * Created by utkan on 01/09/15.
 */
@Singleton
public class LoginDataRepository
        implements ILoginRepository {

    private final LoginDataStoreFactory loginDataStoreFactory;
    private FileUtils fileUtils;

    @Inject
    public LoginDataRepository() {
        this.loginDataStoreFactory = new LoginDataStoreFactory();
    }

    @Override
    public Observable<LoginEntity> login(
            String email,
            String password,
            boolean rememberMe) {
        //we always get all users from the cloud
        final LoginDataStore loginDataStore = this.loginDataStoreFactory.createCloudDataStore();

        LoginRequestEntity loginRequestEntity = new LoginRequestEntity();
        loginRequestEntity
                .email(email)
                .password(password)
                .rememberMe(rememberMe)
                .checkoutAsGuest(false)
                .userNamesEnabled(false)
                .displayCaptcha(false)
                .customProperties(new CustomPropertiesEntity())
//                .setUsername(null)
        ;

        return loginDataStore
                .login(loginRequestEntity);
    }

    @Override
    public Observable<LogOutEntity> logout(String sessionID) {
        final LoginDataStore loginDataStore = this.loginDataStoreFactory.createCloudDataStore();
        return loginDataStore
                .logout();
    }
}
