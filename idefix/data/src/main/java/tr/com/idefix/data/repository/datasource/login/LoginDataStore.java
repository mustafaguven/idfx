package tr.com.idefix.data.repository.datasource.login;

import rx.Observable;
import tr.com.idefix.data.entity.LogOutEntity;
import tr.com.idefix.data.entity.LoginEntity;
import tr.com.idefix.data.entity.LoginRequestEntity;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface LoginDataStore {
    /**
     * Get an {@link rx.Observable} which will emit a List of {@link tr.com.idefix.data.entity.LoginEntity}.
     *
     * @param loginRequestEntity The id to retrieve login data
     */
    Observable<LoginEntity> login(LoginRequestEntity loginRequestEntity);

    Observable<LogOutEntity> logout();
}
