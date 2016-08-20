package tr.com.idefix.android.presenter;

/**
 * Created by utkan on 01/09/15.
 */
public interface ILoginPresenter extends Presenter {

  void login(String username, String password, boolean rememberMe);

  void logout();

}
