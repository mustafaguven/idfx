package tr.com.idefix.android.view.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import tr.com.idefix.android.R;
import tr.com.idefix.android.view.adapters.SearchFilterAdapter;
import tr.com.idefix.data.entity.SearchSubResponseEntity;
import tr.com.idefix.domain.SearchFilterItem;

/**
 * Created by mustafaguven on 26.10.2015.
 */
public class SearchFilterDialog extends DialogFragment {

  static SearchSubResponseEntity data;
  static LinkedHashMap<String, SearchFilterItem> filterList;
  private static SearchFilterDialog instance;
  @Bind(R.id.rvFilter) RecyclerView rvFilter;
  private OnFilterClickListener onFilterClickListener;

  public SearchFilterDialog() {
  }

  public static SearchFilterDialog getInstance(
      SearchSubResponseEntity searchSubResponseEntity, LinkedHashMap<String, SearchFilterItem> list
  ) {
    if (instance == null) {
      instance = new SearchFilterDialog();
    }
    data = searchSubResponseEntity;
    filterList = list;
    return instance;
  }

  public void setOnFilterClicked(OnFilterClickListener event) {
    this.onFilterClickListener = event;
  }

  @OnClick(R.id.btnCollapse) void okClicked() {

    if (onFilterClickListener != null) {
      SearchFilterAdapter adapter = (SearchFilterAdapter) rvFilter.getAdapter();
      onFilterClickListener.onFilterClicked(adapter.getList());
    }
    dismiss();
  }

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
  ) {
    View view = inflater.inflate(R.layout.search_filter, container);
    getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
    ButterKnife.bind(this, view);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    rvFilter.setHasFixedSize(true);
    rvFilter.setLayoutManager(linearLayoutManager);
    rvFilter.setItemAnimator(new DefaultItemAnimator());

    convertToAdapter();

    return view;
  }

  private void convertToAdapter() {

    List<SearchFilterItem> list = new ArrayList<>();
    if (data.brands() != null && data.brands().size() > 1) {
      for (int i = 0; i < data.brands().size(); i++) {
        list.add(new SearchFilterItem(getString(R.string.brands), data.brands().get(i).name(),
            data.brands().get(i).id().toString()));
      }
    }

    if (data.mediaTypes() != null && data.mediaTypes().size() > 1) {
      for (int i = 0; i < data.mediaTypes().size(); i++) {
        list.add(
            new SearchFilterItem(getString(R.string.media_types), data.mediaTypes().get(i).name()));
      }
    }
    if (data.categories() != null && data.categories().size() > 1) {
      for (int i = 0; i < data.categories().size(); i++) {
        list.add(new SearchFilterItem(getString(R.string.category), data.categories().get(i).name(),
            data.categories().get(i).parentPath() + "-" + data.categories().get(i).id()));
      }
    }

    if (data.prices() != null && data.prices().size() > 1) {
      for (int i = 0; i < data.prices().size(); i++) {
        list.add(new SearchFilterItem(getString(R.string.prices), data.prices().get(i).name()));
      }
    }

    SearchFilterAdapter adapter = new SearchFilterAdapter(getActivity(), list);
    adapter.setList(filterList);
    rvFilter.setAdapter(adapter);
  }

  @Override public void onStart() {
    super.onStart();
    Dialog dialog = getDialog();
    if (dialog != null) {
      int width = ViewGroup.LayoutParams.MATCH_PARENT;
      int height = ViewGroup.LayoutParams.MATCH_PARENT;
      dialog.getWindow().setLayout(width, height);
    }
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    Dialog dialog = super.onCreateDialog(savedInstanceState);

    // request a window without the title
    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    return dialog;
  }

  public interface OnFilterClickListener {
    void onFilterClicked(LinkedHashMap<String, SearchFilterItem> list);
  }
}
