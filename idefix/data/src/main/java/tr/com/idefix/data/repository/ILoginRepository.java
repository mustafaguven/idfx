package tr.com.idefix.data.repository;

import rx.Observable;
import tr.com.idefix.data.entity.LogOutEntity;
import tr.com.idefix.data.entity.LoginEntity;

/**
 * Created by utkan on 01/09/15.
 */
public interface ILoginRepository {

    Observable<LoginEntity> login(
            final String username,
            final String password,
            final boolean rememberMe);

    Observable<LogOutEntity> logout(String sessionID);
}
