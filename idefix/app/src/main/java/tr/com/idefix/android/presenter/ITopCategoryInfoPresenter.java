package tr.com.idefix.android.presenter;

import android.os.Bundle;
import java.util.List;
import java.util.Map;
import tr.com.idefix.domain.FilterItem;
import tr.com.idefix.domain.enums.SortEnums;

/**
 * Created by utkan on 30/09/15.
 */
public interface ITopCategoryInfoPresenter extends Presenter {

  void processArguments(Bundle arguments);

  Map<Integer, List<FilterItem>> getFilter();

  void updateFilterItems(List<FilterItem> filterItems);

  void updateFilterItems(FilterItem filterItem);

  int getHitCount();

  SortEnums getSortEnum();

  void setSortEnum(SortEnums selectedSortEnum);

  void getMore(int page);
}
