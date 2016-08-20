package tr.com.idefix.android.presenter;

import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import rx.Observable;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.view.FilterActivityView;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.domain.FilterItem;
import tr.com.idefix.domain.constants.FilterMap;

/**
 * Created by utkan on 28/10/15.
 */
public class FilterActivityPresenter implements IFilterActivityPresenter {

  HashMap<Integer, List<FilterItem>> filter;
  List<String> filterSection;
  FilterActivityView view;
  int pos = 0;

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  @Override public void setView(IView iView) {
    view = (FilterActivityView) iView;
  }

  @Override public void processArguments(Intent intent) {
    if (intent == null) return;

    Bundle extras = intent.getExtras();
    if (extras == null || !extras.containsKey(Keys.FILTER)) return;

    filter = (HashMap<Integer, List<FilterItem>>) extras.getSerializable(Keys.FILTER);

    if (filter == null) return;

    filterSection = new ArrayList<>(filter.keySet().size());

    List<FilterItem> filterList = new ArrayList<>();

    Observable.from(filter.entrySet())
        .doOnCompleted(() -> view.setFilterList(filterList))
        .filter(map -> map.getValue() != null && map.getValue().size() > 1)
        .forEach(map -> {

          List<FilterItem> filterItems = map.getValue();

          String sectionName = null;

          switch (map.getKey()) {
            case FilterMap.FILTER_TYPE_BRAND:
              sectionName = "MARKALAR";
              break;

            case FilterMap.FILTER_TYPE_CATEGORY:
              sectionName = "KATEGORİLER";
              break;

            case FilterMap.FILTER_TYPE_MEDIA_TYPE:
              sectionName = "MEDYA CİNSİ";
              break;

            case FilterMap.FILTER_TYPE_PRICE:
              sectionName = "FİYAT";
              break;
          }

          filterSection.add(sectionName);
          filterList.addAll(filterItems);
          view.addSection(sectionName, pos);
          pos += filterList.size();
        });
  }
}
