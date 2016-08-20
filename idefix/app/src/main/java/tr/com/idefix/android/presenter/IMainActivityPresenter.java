package tr.com.idefix.android.presenter;

import android.content.Intent;
import tr.com.idefix.domain.DomainContext;

/**
 * Created by utkan on 08/09/15.
 */
public interface IMainActivityPresenter extends Presenter {

  void processIntent(Intent intent);

  boolean loggedin();

  String getUserFullName();

  DomainContext getDomainContext();
}
