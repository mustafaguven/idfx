package tr.com.idefix.android.view;

import java.util.List;
import tr.com.idefix.android.model.CategoryModel;

/**
 * Created by utkan on 15/09/15.
 */
public interface ParentCategoryFragmentView extends IView {
  void renderCategories(List<CategoryModel> list);
}
