package tr.com.idefix.android.view.fragments;

/**
 * Created by utkan on 14/09/15.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import tr.com.idefix.android.ApplicationController;
import tr.com.idefix.android.R;
import tr.com.idefix.android.internal.di.components.CategoriesActivityComponent;
import tr.com.idefix.android.model.TopCategoryInfoItemModel;
import tr.com.idefix.android.presenter.ITopCategoryInfoPresenter;
import tr.com.idefix.android.view.TopCategoryInfoFragmentView;
import tr.com.idefix.android.view.adapters.SectionedGridRecyclerViewAdapter;
import tr.com.idefix.android.view.adapters.SortSpinnerAdapter;
import tr.com.idefix.android.view.adapters.TopCategoryInfoAdapter;
import tr.com.idefix.android.view.listeners.OnOpenLoginListener;
import tr.com.idefix.android.view.listeners.OnOpenProductDetailListener;
import tr.com.idefix.android.view.listeners.OnSortAllSelectedListener;
import tr.com.idefix.android.view.listeners.OnSortSelectedListener;
import tr.com.idefix.domain.FilterItem;
import tr.com.idefix.domain.enums.SortEnums;

import static tr.com.idefix.android.contants.Keys.ALL;
import static tr.com.idefix.android.contants.Keys.CATEGORY_LEVEL;
import static tr.com.idefix.android.contants.Keys.CATEGORY_PATH;
import static tr.com.idefix.android.contants.Keys.PARENT_CATEGORY_ID;

public class TopCategoryInfoFragment extends BaseFragment
    implements TopCategoryInfoFragmentView, OnSortSelectedListener, OnSortAllSelectedListener {

  private static int parentID;
  private final int stackSize = 6;
  //<editor-fold desc="Fields">
  @Inject ITopCategoryInfoPresenter presenter;
  @Bind(R.id.gridView) RecyclerView gridView;
  List<SectionedGridRecyclerViewAdapter.Section> sectionList;
  SectionedGridRecyclerViewAdapter.Section[] sectionArray;
  SectionedGridRecyclerViewAdapter mSectionedAdapter;
  List<TopCategoryInfoItemModel> items;
  @Bind(R.id.filter_list_container) LinearLayout filter_list_container;
  int pastVisiblesItems, visibleItemCount, totalItemCount;
  int size;
  private TopCategoryInfoAdapter mAdapter;
  private OnOpenLoginListener onOpenLoginListener;
  private OnOpenProductDetailListener onOpenProductDetailListener;
  private OnSortAllSelectedListener onSortAllSelectedListener;
  private boolean loading = true;
  private int start = 0;
  private int page = 1;
  private boolean isLoadingActive = false;

  //</editor-fold>

  public TopCategoryInfoFragment() {
    // Required empty public constructor
  }

  public static TopCategoryInfoFragment newInstance(
      int parentCategoryID, int level, String categoryPath, boolean all
  ) {
    TopCategoryInfoFragment topCategoryInfoFragment = new TopCategoryInfoFragment();

    parentID = parentCategoryID;
    Bundle bundle = new Bundle();
    bundle.putInt(PARENT_CATEGORY_ID, parentCategoryID);
    bundle.putInt(CATEGORY_LEVEL, level);
    bundle.putString(CATEGORY_PATH, categoryPath);
    bundle.putBoolean(ALL, all);
    topCategoryInfoFragment.setArguments(bundle);
    return topCategoryInfoFragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public void onResume() {
    super.onResume();
    presenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    presenter.pause();
  }

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
  ) {

    View view = inflater.inflate(R.layout.fragment_top_category_info, container, false);
    ButterKnife.bind(this, view);

    gridView.setHasFixedSize(true);

    GridLayoutManager glm = new GridLayoutManager(getActivity(),
        ((ApplicationController) getActivity().getApplication()).getColumnCount());
    gridView.setLayoutManager(glm);
    gridView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        visibleItemCount = glm.getChildCount();
        totalItemCount = glm.getItemCount();
        pastVisiblesItems = glm.findFirstVisibleItemPosition();

        if (loading && isLoadingActive) {
          if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
            page++;
            loading = false;
            start += stackSize;
            presenter.getMore(page);
          }
        }
      }
    });

    return view;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    getComponent(CategoriesActivityComponent.class).inject(this);

    presenter.setView(this);
    presenter.processArguments(getArguments());
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);

    if (activity instanceof OnOpenLoginListener) {
      onOpenLoginListener = (OnOpenLoginListener) activity;
    }
    if (activity instanceof OnOpenProductDetailListener) {
      onOpenProductDetailListener = (OnOpenProductDetailListener) activity;
    }
    if (activity instanceof OnSortAllSelectedListener) {
      onSortAllSelectedListener = (OnSortAllSelectedListener) activity;
    }
  }

  @Override public void addBestSellerSection(List<TopCategoryInfoItemModel> bestSellers) {

    size = bestSellers.size();

    if (bestSellers == null || bestSellers.size() == 0) return;

    if (items == null) {
      items = new ArrayList<>(bestSellers);
    }

    if (mAdapter == null) {
      mAdapter = new TopCategoryInfoAdapter(getActivity(), items, parentID);
      mAdapter.setOnOpenLoginListener(onOpenLoginListener);
      mAdapter.setOnOpenProductDetailListener(onOpenProductDetailListener);
    }
    if (sectionList == null) {
      sectionList = new ArrayList<>();
    }

    sectionList.add(
        new SectionedGridRecyclerViewAdapter.Section(0, getString(R.string.title_best_sellers)));

    if (sectionArray == null) {
      sectionArray = new SectionedGridRecyclerViewAdapter.Section[sectionList.size()];
    } else {
      sectionArray = new SectionedGridRecyclerViewAdapter.Section[sectionList.size()];
    }

    if (mSectionedAdapter == null) {
      mSectionedAdapter =
          new SectionedGridRecyclerViewAdapter(getActivity(), R.layout.section, R.id.section_text,
              gridView, mAdapter, this);
    }
    mSectionedAdapter.setSections(sectionList.toArray(sectionArray));

    gridView.setAdapter(mSectionedAdapter);

    mSectionedAdapter.notifyDataSetChanged();
  }

  @Override public void addTheNewestsSection(List<TopCategoryInfoItemModel> theNewestItems) {

    if (getActivity() == null) return;

    if (theNewestItems == null || theNewestItems.size() == 0) return;

    if (mAdapter == null) {
      mAdapter = new TopCategoryInfoAdapter(getActivity(), theNewestItems, parentID);
      mAdapter.setOnOpenLoginListener(onOpenLoginListener);
      mAdapter.setOnOpenProductDetailListener(onOpenProductDetailListener);
    } else {

      if (items == null) {
        items = new ArrayList<>(theNewestItems);
      } else {
        items.addAll(theNewestItems);
      }
    }

    if (sectionList == null) {
      sectionList = new ArrayList<>();
    }

    sectionList.add(
        new SectionedGridRecyclerViewAdapter.Section(size, getString(R.string.title_most_new)));

    if (sectionArray == null) {
      sectionArray = new SectionedGridRecyclerViewAdapter.Section[sectionList.size()];
    } else {
      sectionArray = new SectionedGridRecyclerViewAdapter.Section[sectionList.size()];
    }

    if (mSectionedAdapter == null) {
      mSectionedAdapter =
          new SectionedGridRecyclerViewAdapter(getActivity(), R.layout.section, R.id.section_text,
              gridView, mAdapter, this);
    }
    mSectionedAdapter.setSections(sectionList.toArray(sectionArray));

    gridView.setAdapter(mSectionedAdapter);

    mSectionedAdapter.notifyDataSetChanged();
  }

  @Override public void bindFilteredProducts(List<TopCategoryInfoItemModel> filteredProducts) {

    isLoadingActive = true;
    if (filteredProducts == null || filteredProducts.size() == 0) return;

    mAdapter = new TopCategoryInfoAdapter(getActivity(), filteredProducts, parentID);
    mAdapter.setOnOpenLoginListener(onOpenLoginListener);
    mAdapter.setOnOpenProductDetailListener(onOpenProductDetailListener);

    items = new ArrayList<>(filteredProducts);

    sectionList = new ArrayList<>();

    sectionList.add(new SectionedGridRecyclerViewAdapter.Section(size,
        presenter.getHitCount() + " Adet Sonuç"));

    sectionArray = new SectionedGridRecyclerViewAdapter.Section[sectionList.size()];

    SortEnums[] esList = SortEnums.values();

    int position = -1;
    if (presenter.getSortEnum() != null) {
      SortEnums selectedEnum = presenter.getSortEnum();

      for (int i = 0; i < esList.length; i++) {
        if (selectedEnum.getText().equals(esList[i].getText())) {
          position = i;
          break;
        }
      }
    }
    SortSpinnerAdapter sortSpinnerAdapter = new SortSpinnerAdapter(getContext(),
        //android.R.layout.simple_spinner_dropdown_item,
        R.layout.custom_row_layout, esList);

    mSectionedAdapter = new SectionedGridRecyclerViewAdapter(getActivity(), R.layout.section_sort,
        R.id.section_text, gridView, mAdapter, sortSpinnerAdapter, position, this);

    mSectionedAdapter.setSections(sectionList.toArray(sectionArray));

    gridView.setAdapter(mSectionedAdapter);

    mSectionedAdapter.notifyDataSetChanged();
  }

  @Override public void updateFilterItems(List<FilterItem> filterItems) {

    presenter.updateFilterItems(filterItems);

    updateFilterView(filterItems);
  }

  @Override public void updateFilterView(List<FilterItem> filterItems) {

    page = 1;
    loading = this.size <= filterItems.size();
    filter_list_container.removeAllViews();

    if (filterItems != null && filterItems.size() > 0) {

      List<FilterItem> filterItemSelecteds = new ArrayList<>();

      for (FilterItem fi : filterItems) {
        if (fi.selected()) {
          filterItemSelecteds.add(fi);
        }
      }

      LayoutInflater inflater =
          (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

      LinearLayout item_filter_list = null;
      for (int i = 0; i < filterItemSelecteds.size(); i++) {

        if (i % 2 == 0) {

          item_filter_list = (LinearLayout) inflater.inflate(R.layout.item_filter_list, null);

          filter_list_container.addView(item_filter_list);
        }

        RelativeLayout fContainerA = null;
        RelativeLayout fContainerB = null;
        if (i == 0) {
          fContainerA = (RelativeLayout) item_filter_list.findViewById(R.id.filtera);
          fContainerA.setVisibility(View.VISIBLE);
        } else {
          if (i % 2 == 0) {
            fContainerA = (RelativeLayout) item_filter_list.findViewById(R.id.filtera);
            fContainerA.setVisibility(View.VISIBLE);
          } else {
            fContainerB = (RelativeLayout) item_filter_list.findViewById(R.id.filterb);
            fContainerB.setVisibility(View.VISIBLE);
          }
        }

        TextView textviewA = null;
        TextView textviewB = null;
        TextView textviewAB = null;
        ImageView imageViewA = null;
        ImageView imageViewB = null;
        ImageView imageViewAB = null;

        if (fContainerA != null) {
          textviewA = (TextView) fContainerA.findViewById(R.id.filter_texta);
          imageViewA = (ImageView) fContainerA.findViewById(R.id.filter_closea);
          imageViewAB = imageViewA;
        }
        if (fContainerB != null) {
          textviewB = (TextView) fContainerB.findViewById(R.id.filter_textb);
          imageViewB = (ImageView) fContainerB.findViewById(R.id.filter_closeb);
          imageViewAB = imageViewB;
        }

        if (textviewA != null) {
          textviewAB = textviewA;
        }
        if (textviewB != null) {
          textviewAB = textviewB;
        }

        textviewAB.setText(filterItemSelecteds.get(i).name());

        textviewAB.setTag(filterItemSelecteds.get(i));
        imageViewAB.setTag(filterItemSelecteds.get(i));

        imageViewAB.setOnClickListener(v -> {
          FilterItem filterItem = ((FilterItem) v.getTag());
          filterItem.selected(false);
          presenter.updateFilterItems(filterItem);
        });

        final ImageView finalImageViewAB = imageViewAB;
        textviewAB.setOnClickListener(v -> finalImageViewAB.performClick());
      }
    }
  }

  public void notifyItemAddedToWishList() {
    new Handler().post(() -> mSectionedAdapter.notifyDataSetChanged());
  }

  public void notifyItemAddedToAlarmList() {
    new Handler().post(() -> mSectionedAdapter.notifyDataSetChanged());
  }

  public Map<Integer, List<FilterItem>> getFilter() {
    return presenter.getFilter();
  }

  @Override public void onSortSelected(SortEnums selectedSortEnum) {
    presenter.setSortEnum(selectedSortEnum);
  }

  @Override public void onSortAllSelected() {
    onSortAllSelectedListener.onSortAllSelected();
  }

  public void setOnSortAllSelectedListener(OnSortAllSelectedListener onSortAllSelectedListener) {
    this.onSortAllSelectedListener = onSortAllSelectedListener;
  }

  @Override public void setBasketCount(int count) {
  }

  @Override public void notifyDataSetChanged() {
    if (mSectionedAdapter != null) {
      mSectionedAdapter.notifyDataSetChanged();
    }
  }

  @Override public void onMoreRecordsRetrieved(List<TopCategoryInfoItemModel> filteredProduct) {
    loading = this.size <= filteredProduct.size();
    mAdapter.setFilteredProducts(filteredProduct);
  }
}