package tr.com.idefix.android.view.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;
import com.newrelic.agent.android.NewRelic;
import com.squareup.otto.Bus;
import javax.inject.Inject;
import mu.comon.utils.DeviceUtils;
import mu.comon.utils.FileUtils;
import timber.log.Timber;
import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.R;
import tr.com.idefix.android.components.ProgressDialogDNR;
import tr.com.idefix.android.contants.RequestCodes;
import tr.com.idefix.android.internal.di.components.ApplicationComponent;
import tr.com.idefix.android.internal.di.modules.ActivityModule;
import tr.com.idefix.android.navigation.Navigator;
import tr.com.idefix.android.view.listeners.OnSearchClickListener;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.LoggedInUser;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity {

  private static final String CONTAINER_ID = "GTM-MVL85L";
  protected Bus bus;
  DataLayer dataLayer;
  TagManager tagManager;
  @Inject Navigator navigator;
  @Inject DeviceUtils deviceUtils;
  @Inject FileUtils fileUtils;
  private ProgressDialogDNR mProgress;
  private DomainContext domainContext;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Timber.i(this.getClass().getSimpleName());

    this.getApplicationComponent().inject(this);

    fileUtils.test();
    deviceUtils.setStatusBarColor(
        this,
        getResources().getColor(R.color.bg_splash)
    );

    TagManager tagManager = TagManager.getInstance(this);

    // Modify the log level of the logger to print out not only
    // warning and error messages, but also verbose, debug, info messages.
    tagManager.setVerboseLoggingEnabled(true);

    //        PendingResult<ContainerHolder> pending =
    //                tagManager.loadContainerPreferNonDefault(CONTAINER_ID,
    //                        R.raw.gtm_mvl85l_json);
    //
    //        // The onResult method will be called as soon as one of the following happens:
    //        //     1. a saved container is loaded
    //        //     2. if there is no saved container, a network container is loaded
    //        //     3. the request times out. The example below uses a constant to manage the
    // timeout period.
    //        pending.setResultCallback(new ResultCallback<ContainerHolder>() {
    //            @Override
    //            public void onResult(ContainerHolder containerHolder) {
    //                ContainerHolderSingleton.setContainerHolder(containerHolder);
    //                Container container = containerHolder.getContainer();
    //                if (!containerHolder.getStatus().isSuccess()) {
    //                    Log.e("CuteAnimals", "failure loading container");
    //                    displayErrorToUser(R.string.load_error);
    //                    return;
    //                }
    //                ContainerHolderSingleton.setContainerHolder(containerHolder);
    //                ContainerLoadedCallback.registerCallbacksForContainer(container);
    //                containerHolder.setContainerAvailableListener(new ContainerLoadedCallback());
    //                startMainActivity();
    //            }
    //        }, 2, TimeUnit.SECONDS);

    //https://docs.newrelic.com/docs/mobile-monitoring/mobile-monitoring-installation/android
    // /installing-android-apps-gradle-android-studio
    NewRelic.withApplicationToken("AAc9d64e28bff1130716a693f11e568af614b5c8f6")
        .start(this.getApplication());

    tr.com.idefix.domain.internal.di.components.ApplicationComponent component =
        ApplicationController.getInstance().getDomainApplicationComponent();
    domainContext = component.domainContext();
    bus = component.busProvider().getBus();

    LoggedInUser lu = domainContext.getLoggedInUser();

    getDataLayer().push(dataLayer.mapOf("CD_UserID", lu != null ? lu.email() : null,
        //Login olmus kullaniciya ait userID. Butun platformlarda ayni olmali

        "CD_Login", lu != null ? "Yes" : "NO",                      //Yes / No olarak Login durumu

        "CD_SignupDate", null,            //Login olmus kullanicinin uyelik tarihi YYYYMMDD

        "CD_TransactionCount", null,             //Login olmus kullanicinin toplam siparis adedi

        "CD_TotalRevenue", null,              //Login olmus kullanicinin toplam siparis tutari

        "CD_AvgOrderValue", null,              //Login olmus kullanicinin ortalama siparis tutari

        "CD_FirstTransactionDate", null,  //Kullanicinin ilk siparis tarihi YYYYMMDD

        "CD_LastTransactionDate", null    //Kullanicinin son siparis tarihi YYYYMMDD

    ));
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    switch (requestCode) {
      case RequestCodes.SEARCH:
        switch (resultCode) {
          case RESULT_CANCELED:
            Timber.i("Search RESULT_CANCELED");
            break;
          case RESULT_OK:
            Timber.i("Search RESULT_OK");
            break;
        }
        break;
    }
  }

  void openSearch() {
    if (this instanceof OnSearchClickListener) {
      navigator.navigateToSearchActivity(this);
    }
  }

  /**
   * Adds a {@link Fragment} to this activity's layout.
   *
   * @param containerViewId The container view to where add the fragment.
   * @param fragment The fragment to be added.
   */
  protected void addFragment(int containerViewId, Fragment fragment) {
    FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  /**
   * Get the Main Application component for dependency injection.
   *
   * @return {@link ApplicationComponent}
   */
  public ApplicationComponent getApplicationComponent() {
    return ((ApplicationController) getApplication()).getApplicationComponent();
  }

  /**
   * Get an Activity module for dependency injection.
   *
   * @return {@link ActivityModule}
   */
  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }

  public void showProgress() {
    try {
      if (this.mProgress == null) {
        mProgress = new ProgressDialogDNR(this);
        mProgress.setCancelable(false);
      }
      if (!mProgress.isShowing() && !isFinishing()) {
        mProgress.show();
      }
    } catch (Exception e) {
      Timber.e("showProgress: " + (e.getMessage() != null ? e.getMessage() : ""));
    }
  }

  public void hideProgress() {
    try {
      if (mProgress != null && mProgress.isShowing()) {
        mProgress.cancel();
        mProgress.dismiss();
      }
    } catch (Exception e) {
      e.printStackTrace();
      Timber.e("hideProgress" + (e.getMessage() != null ? e.getMessage() : ""));
    }
  }

  /**
   * Hides the soft keyboard
   */
  public void hideSoftKeyboard() {
    if (getCurrentFocus() != null) {
      InputMethodManager inputMethodManager =
          (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
  }

  /**
   * Shows the soft keyboard
   */
  public void showSoftKeyboard(View view) {
    InputMethodManager inputMethodManager =
        (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    view.requestFocus();
    inputMethodManager.showSoftInput(view, 0);
  }

  public Navigator getNavigator() {
    return this.navigator;
  }

  public DataLayer getDataLayer() {

    if (dataLayer == null) {
      dataLayer = TagManager.getInstance(this).getDataLayer();
    }
    return dataLayer;
  }

  public boolean isTablet() {
    return ((ApplicationController) (getApplication())).isTablet();
  }

  public Bus getBus() {
    return bus;
  }

  public DomainContext getDomainContext(){
    return
        ApplicationController.getInstance().getDomainApplicationComponent().domainContext();
  }
}
