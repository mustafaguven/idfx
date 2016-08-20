package tr.com.idefix.android.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tr.com.idefix.android.R;
import tr.com.idefix.android.view.activities.AboutActivity;
import tr.com.idefix.android.view.listeners.OnOpenContractsListener;
import tr.com.idefix.android.view.listeners.OnOpenInstagramListener;
import tr.com.idefix.android.view.listeners.OnOpenStoresListener;

/**
 * Created by utkan on 07/09/15.
 */
public class MainMenuBottomFragment extends BaseFragment {

  private OnOpenStoresListener onOpenStoresListener;
  private OnOpenContractsListener onOpenContractsListener;
  private OnOpenInstagramListener onOpenInstagramListener;

  //<editor-fold desc="Fields">
  //    @Bind(R.id.login_facebook)
  //    ImageButton login_facebook;
  //    @Bind(R.id.login_twitter)
  //    ImageButton login_twitter;
  //    @Bind(R.id.login_instagram)
  //    ImageButton login_instagram;
  //    @Bind(R.id.about)
  //    TextView about;
  //    @Bind(R.id.stores)
  //    TextView stores;
  //    @Bind(R.id.contracts)
  //    TextView contracts;
  //</editor-fold>

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
  ) {

    View view = inflater.inflate(R.layout.fragment_mainmenu_bottom, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);

    if (activity instanceof OnOpenStoresListener) {
      onOpenStoresListener = (OnOpenStoresListener) activity;
    }
    if (activity instanceof OnOpenContractsListener) {
      onOpenContractsListener = (OnOpenContractsListener) activity;
    }
    if (activity instanceof OnOpenInstagramListener) {
      onOpenInstagramListener = (OnOpenInstagramListener) activity;
    }
  }

  @OnClick(R.id.login_facebook) void login_facebook() {
    String facebookUrl = "https://www.facebook.com/idefixcom/";
    try {
      int versionCode =
          getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
      if (versionCode >= 3002850) {
        Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
        ;
      } else {
        // open the Facebook app using the old method (fb://profile/id or fb://page/id)
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/122758601090625")));
      }
    } catch (PackageManager.NameNotFoundException e) {
      // Facebook is not installed. Open the browser
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
    }
  }

  @OnClick(R.id.login_twitter) void login_twitter() {
    try {
      startActivity(
          new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=idefixcom")));
    } catch (Exception e) {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/idefixcom")));
    }
  }

  @OnClick(R.id.about) void about() {
    startActivity(new Intent(getActivity(), AboutActivity.class));
  }

  @OnClick(R.id.login_instagram) void login_instagram() {
    if (onOpenInstagramListener != null) {
      onOpenInstagramListener.openInstagramPage();
    }
  }

  @OnClick(R.id.stores) void stores() {
    if (onOpenStoresListener != null) {
      onOpenStoresListener.onOpenStore();
    }
  }

  @OnClick(R.id.contracts) void contracts() {
    onOpenContractsListener.openContracts();
    //startActivity(new Intent(getActivity(), SozlesmeActivity.class));
  }
}