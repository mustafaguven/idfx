package tr.com.idefix.android.presenter;

import java.util.List;
import tr.com.idefix.android.model.StoreModel;

/**
 * Created by utkan on 01/09/15.
 */
public interface IStoresFragmentPresenter extends Presenter {

  void getRetailStores();

  List<StoreModel> getAddresses();

  void onCitySelected(String city);
}
