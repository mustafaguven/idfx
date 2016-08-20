package tr.com.idefix.android.view.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.BuildConfig;
import tr.com.idefix.android.R;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.view.activities.MainActivity;
import tr.com.idefix.android.view.activities.UserProfileActivity;
import tr.com.idefix.domain.DomainContext;

/**
 * A placeholder fragment containing a simple view.
 */
public class BasketFragment extends Fragment {

  final static String BASE_URL = "http://www.idefix.com";
  final static String BASKET_URL = BASE_URL + "/sepetim";
  private final String SIPARIS_SCHEME = "idefix://siparislerim";
  private final String ANASAYFA_SCHEME = "idefix://anasayfa";
  @Bind(R.id.basket_webview) WebView webView;
  DomainContext domainContext;

  public BasketFragment() {
  }

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
  ) {
    View view = inflater.inflate(R.layout.fragment_basket, container, false);
    ButterKnife.bind(this, view);
    ////////////////////////////////////////////////////////////////////////////////////////////
    return view;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    WebSettings webSettings = webView.getSettings();
    webSettings.setJavaScriptEnabled(true);
    webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    webSettings.setDomStorageEnabled(true);
    webView.setWebViewClient(new DRWebViewClient(getActivity()));

    Map<String, String> additionalHttpHeaders = new HashMap<>();
    additionalHttpHeaders.put("platform", "androidphone");
    additionalHttpHeaders.put("appversion", String.valueOf(BuildConfig.VERSION_CODE));

    if (getDomainContext().getLoggedInUser() == null) {
      webView.loadUrl(BASKET_URL, additionalHttpHeaders);
    } else {
      String url = String.format("%s?signedRequest=%s", BASKET_URL,
          getDomainContext().getLoggedInUser().sessionObject());
      if (domainContext.getCache() != null) {

        HashSet<String> cookies = domainContext.getCache()
            .getCacheByType("LOGIN_COOKIES", new TypeToken<HashSet<String>>() {
            }.getType());

        if (cookies != null) {

          CookieSyncManager.createInstance(getActivity());
          CookieSyncManager.getInstance().startSync();
          android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
          cookieManager.setAcceptCookie(true);
          cookieManager.removeSessionCookie();

          StringBuilder stringBuilder = new StringBuilder();

          Object[] hArr = cookies.toArray();

          for (int i = 0; i < hArr.length; i++) {

            String cookie = hArr[i].toString();

            stringBuilder.append(cookie);

            cookieManager.setCookie(BASE_URL, cookie);

            if (i < hArr.length - 1) {
              stringBuilder.append(";");
            }
          }

          CookieSyncManager.getInstance().sync();

          String cook = stringBuilder.toString();

          additionalHttpHeaders.put("Cookie", cook);

          CookieSyncManager.createInstance(getActivity());

          CookieSyncManager.getInstance().sync();

          webView.loadUrl(url, additionalHttpHeaders);
        }
      } else {
        webView.loadUrl(BASKET_URL, additionalHttpHeaders);
      }
    }
  }

  //    @Override
  //    public void onBackPressed() {
  //        if(webView.canGoBack()) {
  //            webView.goBack();
  //        } else {
  //            super.onBackPressed();
  //        }
  //    }

  public DomainContext getDomainContext() {
    if (domainContext == null) {
      domainContext =
          ApplicationController.getInstance().getDomainApplicationComponent().domainContext();
    }
    return domainContext;
  }

  public class DRWebViewClient extends WebViewClient {

    Context context;

    DRWebViewClient(Context context) {
      this.context = context;
    }

    @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
      super.onPageStarted(view, url, favicon);
      if (url.toLowerCase().contentEquals(SIPARIS_SCHEME.toLowerCase())) {
        Intent i = new Intent(context, UserProfileActivity.class);
        i.putExtra(Keys.OPEN_FOR_MY_ORDERS, true);
        startActivity(i);
        getActivity().finish();
      } else if (url.toLowerCase().contentEquals(ANASAYFA_SCHEME.toLowerCase())) {
        startActivity(new Intent(context, MainActivity.class));
        getActivity().finish();
      }
    }

    @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {

      return false;

      //            if(Uri.parse(url).getHost().endsWith("dr.com.tr")) {
      //                return false;
      //            }
      //
      //            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
      //            view.getContext().startActivity(intent);
      //            return true;
    }
  }
}
