package tr.com.idefix.android.view.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import java.util.Arrays;
import tr.com.idefix.android.R;
import tr.com.idefix.android.view.listeners.OnSortAllSelectedListener;
import tr.com.idefix.android.view.listeners.OnSortSelectedListener;

/**
 * Created on 10.3.15.
 */

public class SectionedGridRecyclerViewAdapter
    extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int SECTION_TYPE = 0;
  private final Context mContext;
  OnSortAllSelectedListener onSortAllSelectedListener;
  private OnSortSelectedListener sortListener;
  private int selectedSortPosition;
  private SortSpinnerAdapter sortSpinnerAdapter;
  private boolean mValid = true;
  private int mSectionResourceId;
  private int mTextResourceId;
  private LayoutInflater mLayoutInflater;
  private RecyclerView.Adapter mBaseAdapter;
  private SparseArray<Section> mSections = new SparseArray<>();
  private RecyclerView mRecyclerView;

  public SectionedGridRecyclerViewAdapter(
      Context context, @LayoutRes int sectionResourceId, @IdRes int textResourceId,
      RecyclerView recyclerView, RecyclerView.Adapter baseAdapter,
      SortSpinnerAdapter sortSpinnerAdapter, int position, OnSortSelectedListener listener
  ) {

    this(context, sectionResourceId, textResourceId, recyclerView, baseAdapter, null);

    this.sortSpinnerAdapter = sortSpinnerAdapter;
    this.selectedSortPosition = position;
    this.sortListener = listener;
  }

  public SectionedGridRecyclerViewAdapter(
      Context context, @LayoutRes int sectionResourceId, @IdRes int textResourceId,
      RecyclerView recyclerView, RecyclerView.Adapter baseAdapter,
      OnSortAllSelectedListener alllistener
  ) {

    mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mSectionResourceId = sectionResourceId;
    mTextResourceId = textResourceId;
    mBaseAdapter = baseAdapter;
    mContext = context;
    mRecyclerView = recyclerView;
    this.onSortAllSelectedListener = alllistener;

    mBaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
      @Override public void onChanged() {
        mValid = mBaseAdapter.getItemCount() > 0;
        notifyDataSetChanged();
      }

      @Override public void onItemRangeChanged(int positionStart, int itemCount) {
        mValid = mBaseAdapter.getItemCount() > 0;
        notifyItemRangeChanged(positionStart, itemCount);
      }

      @Override public void onItemRangeInserted(int positionStart, int itemCount) {
        mValid = mBaseAdapter.getItemCount() > 0;
        notifyItemRangeInserted(positionStart, itemCount);
      }

      @Override public void onItemRangeRemoved(int positionStart, int itemCount) {
        mValid = mBaseAdapter.getItemCount() > 0;
        notifyItemRangeRemoved(positionStart, itemCount);
      }
    });

    final GridLayoutManager layoutManager = (GridLayoutManager) (mRecyclerView.getLayoutManager());
    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override public int getSpanSize(int position) {
        return (isSectionHeaderPosition(position)) ? layoutManager.getSpanCount() : 1;
      }
    });
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int typeView) {
    if (typeView == SECTION_TYPE) {
      final View view = LayoutInflater.from(mContext).inflate(mSectionResourceId, parent, false);

      SectionViewHolder holder = new SectionViewHolder(view, mTextResourceId);

      if (holder.spinner != null && sortSpinnerAdapter != null) {
        holder.spinner.setAdapter(sortSpinnerAdapter);
        if (selectedSortPosition != -1) {
          holder.spinner.setSelection(selectedSortPosition, true);
        }

        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (sortListener != null) {
              sortListener.onSortSelected(sortSpinnerAdapter.getItem(position));
            }
          }

          @Override public void onNothingSelected(AdapterView<?> parent) {

          }
        });
      }

      if (holder.sort_all != null && onSortAllSelectedListener != null) {
        holder.sort_all.setOnClickListener(v -> onSortAllSelectedListener.onSortAllSelected());
      }
      return holder;
    } else {
      return mBaseAdapter.onCreateViewHolder(parent, typeView - 1);
    }
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder sectionViewHolder, int position) {
    if (isSectionHeaderPosition(position)) {
      ((SectionViewHolder) sectionViewHolder).title.setText(mSections.get(position).title);
    } else {
      mBaseAdapter.onBindViewHolder(sectionViewHolder, sectionedPositionToPosition(position));
    }
  }

  @Override public int getItemViewType(int position) {
    return isSectionHeaderPosition(position) ? SECTION_TYPE
        : mBaseAdapter.getItemViewType(sectionedPositionToPosition(position)) + 1;
  }

  public void setSections(Section[] sections) {
    mSections.clear();

    Arrays.sort(
        sections, (o, o1) -> (o.firstPosition == o1.firstPosition) ? 0
            : ((o.firstPosition < o1.firstPosition) ? -1 : 1));

    int offset = 0; // offset positions for the headers we're adding
    for (Section section : sections) {
      section.sectionedPosition = section.firstPosition + offset;
      mSections.append(section.sectionedPosition, section);
      ++offset;
    }

    notifyDataSetChanged();
  }

  public int positionToSectionedPosition(int position) {
    int offset = 0;
    for (int i = 0; i < mSections.size(); i++) {
      if (mSections.valueAt(i).firstPosition > position) {
        break;
      }
      ++offset;
    }
    return position + offset;
  }

  public int sectionedPositionToPosition(int sectionedPosition) {
    if (isSectionHeaderPosition(sectionedPosition)) {
      return RecyclerView.NO_POSITION;
    }

    int offset = 0;
    for (int i = 0; i < mSections.size(); i++) {
      if (mSections.valueAt(i).sectionedPosition > sectionedPosition) {
        break;
      }
      --offset;
    }
    return sectionedPosition + offset;
  }

  public boolean isSectionHeaderPosition(int position) {
    return mSections.get(position) != null;
  }

  @Override public long getItemId(int position) {
    return isSectionHeaderPosition(position) ? Integer.MAX_VALUE - mSections.indexOfKey(position)
        : mBaseAdapter.getItemId(sectionedPositionToPosition(position));
  }

  @Override public int getItemCount() {
    return (mValid ? mBaseAdapter.getItemCount() + mSections.size() : 0);
  }

  public static class SectionViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView title;
    public AppCompatTextView sort_all;
    public AppCompatSpinner spinner;

    public SectionViewHolder(View view, int mTextResourceid) {
      super(view);
      title = (AppCompatTextView) view.findViewById(mTextResourceid);

      if (view.findViewById(R.id.sort_spinner) != null) {
        spinner = (AppCompatSpinner) view.findViewById(R.id.sort_spinner);
      }

      if (view.findViewById(R.id.section_all) != null) {
        sort_all = (AppCompatTextView) view.findViewById(R.id.section_all);
      }
    }
  }

  public static class Section {
    int firstPosition;
    int sectionedPosition;
    CharSequence title;

    public Section(int firstPosition, CharSequence title) {
      this.firstPosition = firstPosition;
      this.title = title;
    }

    public CharSequence getTitle() {
      return title;
    }
  }
}