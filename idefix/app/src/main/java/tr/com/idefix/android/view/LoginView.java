package tr.com.idefix.android.view;

/**
 * Created by utkan on 01/09/15.
 */
public interface LoginView extends IView {

  void onError(String s);

  void loggedin();
}
