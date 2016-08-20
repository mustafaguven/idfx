package tr.com.idefix.android.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import tr.com.idefix.android.R;
import tr.com.idefix.android.model.MainCategoryModel;
import tr.com.idefix.android.view.adapters.MainCategoryAdapter;

/**
 * Created by utkan on 07/09/15.
 */
public class MainMenuFragment extends BaseFragment {

  RecyclerView gridView;
  //<editor-fold desc="Fields">
  private ArrayList<MainCategoryModel> categories;
  //</editor-fold>

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
  ) {
    View view = inflater.inflate(R.layout.fragment_mainmenu, container, false);

    gridView = (RecyclerView) view;
    gridView.setHasFixedSize(true);

    GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
    gridView.setLayoutManager(mLayoutManager);

    gridView.setAdapter(new MainCategoryAdapter(null));

    return view;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  public void setCategories(ArrayList<MainCategoryModel> categories) {
    this.categories = categories;
    MainCategoryAdapter adapter = new MainCategoryAdapter(categories);
    gridView.setAdapter(adapter);
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
  }
}
