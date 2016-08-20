package tr.com.idefix.android.view;

import java.util.List;
import tr.com.idefix.android.model.TopCategoryInfoItemModel;
import tr.com.idefix.domain.FilterItem;

/**
 * Created by utkan on 30/09/15.
 */
public interface TopCategoryInfoFragmentView extends IView {

  void addBestSellerSection(List<TopCategoryInfoItemModel> bestSellers);

  void addTheNewestsSection(List<TopCategoryInfoItemModel> theNewestItems);

  void updateFilterItems(List<FilterItem> filterItems);

  void updateFilterView(List<FilterItem> filterItems);

  void bindFilteredProducts(List<TopCategoryInfoItemModel> filteredProducts);

  void notifyDataSetChanged();

  void onMoreRecordsRetrieved(List<TopCategoryInfoItemModel> filteredProduct);
}
