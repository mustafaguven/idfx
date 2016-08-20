package tr.com.idefix.android.view.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import tr.com.idefix.android.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {

  public SearchFragment() {
  }

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
  ) {
    return inflater.inflate(R.layout.fragment_search, container, false);
  }
}
