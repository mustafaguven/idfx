package tr.com.idefix.android.view;

import java.util.List;
import tr.com.idefix.domain.FilterItem;

/**
 * Created by utkan on 28/10/15.
 */
public interface FilterActivityView extends IView {

  void setFilterList(List<FilterItem> filterList);

  void addSection(String title, int pos);
}
