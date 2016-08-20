package tr.com.idefix.android.view;

import java.util.List;
import tr.com.idefix.android.model.StoreModel;

/**
 * Created by utkan on 09/09/15.
 */
public interface StoresFragmentView extends IView {
  void renderCities(List<String> cities);

  void renderAddresses(List<StoreModel> storeModelList);
}
