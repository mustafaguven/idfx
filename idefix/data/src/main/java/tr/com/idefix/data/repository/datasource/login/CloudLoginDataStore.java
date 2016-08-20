package tr.com.idefix.data.repository.datasource.login;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tr.com.idefix.data.entity.EmptyRequestBody;
import tr.com.idefix.data.entity.LogOutEntity;
import tr.com.idefix.data.entity.LoginEntity;
import tr.com.idefix.data.entity.LoginRequestEntity;
import tr.com.idefix.data.net.RestApiCommon;

/**
 * {@link LoginDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudLoginDataStore implements LoginDataStore {

    private final RestApiCommon restApi;

    /**
     * Construct a {@link LoginDataStore} based on connections to the api (Cloud).
     *
     * @param restApi The {@link RestApiCommon} implementation to use.
     */
    public CloudLoginDataStore(RestApiCommon restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<LoginEntity> login(LoginRequestEntity loginRequestEntity) {

        return restApi
                .login(loginRequestEntity)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    @Override
    public Observable<LogOutEntity> logout() {
        return restApi
                .logout(new EmptyRequestBody())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
