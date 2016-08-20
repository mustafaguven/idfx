package tr.com.idefix.android;

import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.microsoft.windowsazure.notifications.NotificationsManager;
import timber.log.Timber;
import tr.com.idefix.android.azure.NotificationHub;
import tr.com.idefix.android.handler.MyHandler;
import tr.com.idefix.android.internal.di.components.ApplicationComponent;
import tr.com.idefix.android.internal.di.components.DaggerApplicationComponent;
import tr.com.idefix.android.internal.di.modules.ApplicationModule;
import tr.com.idefix.domain.DomainApplication;

/**
 * Created by utkan on 01/09/15.
 */
public class ApplicationController extends DomainApplication {

  String registrationId = "";
  private ApplicationComponent applicationComponent;
  private String SENDER_ID = "798818025223";
  private GoogleCloudMessaging gcm;
  private NotificationHub hub;

  public boolean isTablet() {
    return getResources().getBoolean(R.bool.isTablet);
  }

  public int getColumnCount() {
    return isTablet() ? 3 : 2;
  }

  @Override public void onCreate() {
    super.onCreate();
    this.initializeInjector();

    NotificationsManager.handleNotifications(this, SENDER_ID, MyHandler.class);
    gcm = GoogleCloudMessaging.getInstance(this);
    hub = new NotificationHub(BuildConfig.HUBNAME, BuildConfig.HUBLISTENCONNECTIONSTRING, this);
    registerWithNotificationHubs();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    } else {
      Timber.plant(new CrashReportingTree());
    }
  }

  @SuppressWarnings("unchecked") private void registerWithNotificationHubs() {
    new AsyncTask() {
      @Override protected Object doInBackground(Object... params) {
        try {
          String regid = gcm.register(SENDER_ID);
          registrationId = hub.register(regid).getRegistrationId();
          Log.d("PUSH_NOTIFICATIONS", registrationId);
        } catch (Exception e) {
          Log.e("PUSH_NOTIFICATIONS", "azure notification hub register edilemedi" + e.getMessage());
          return e;
        }
        return null;
      }
    }.execute(null, null, null);
  }

  private void initializeInjector() {
    this.applicationComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
  }

  public ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }

  /**
   * A tree which logs important information for crash reporting.
   */
  private static class CrashReportingTree extends Timber.Tree {
    @Override protected void log(int priority, String tag, String message, Throwable t) {
      if (priority == Log.VERBOSE || priority == Log.DEBUG) {
        return;
      }

      //            CrashLibrary.log(priority, tag, message);

      if (t != null) {
        if (priority == Log.ERROR) {
          //                    CrashLibrary.logError(t);
        } else if (priority == Log.WARN) {
          //                    CrashLibrary.logWarning(t);
        }
      }
    }
  }
}
