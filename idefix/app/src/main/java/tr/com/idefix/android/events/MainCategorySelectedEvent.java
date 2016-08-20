package tr.com.idefix.android.events;

import lombok.Getter;
import lombok.experimental.Accessors;
import tr.com.idefix.android.model.MainCategoryModel;

/**
 * Created on 11.28.15.
 */
@Getter @Accessors(fluent = true) public class MainCategorySelectedEvent {

  MainCategoryModel mainCategoryModel;

  public MainCategorySelectedEvent(MainCategoryModel mainCategoryModel) {
    this.mainCategoryModel = mainCategoryModel;
  }
}
