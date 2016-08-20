package tr.com.idefix.android.mapper;

import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.model.StoreModel;
import tr.com.idefix.domain.Store;

/**
 * Created by utkan on 01/09/15.
 */
@PerActivity public class CommonDataMapper {

  @Inject public CommonDataMapper() {
  }

  public StoreModel transform(Store store) {

    if (store != null) {
      return new StoreModel().cityName(store.cityName())
          .description(store.description())
          .storeName(store.storeName());
    }
    return null;
  }

  public SparseArray<List<StoreModel>> transform(List<Store> stores, Collection<String> cities) {

    SparseArray<List<StoreModel>> sparseArray = new SparseArray<>();

    String cityName;
    int hashCode;
    List<StoreModel> list;

    for (Store store : stores) {
      cityName = store.cityName();
      cities.add(cityName);

      hashCode = cityName.hashCode();

      list = sparseArray.get(hashCode);

      if (list == null) {
        list = new ArrayList<>();
      }

      list.add(transform(store));

      sparseArray.put(hashCode, list);
    }

    return sparseArray;
  }
}
