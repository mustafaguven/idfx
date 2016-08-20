package tr.com.idefix.android.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 9.11.15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class StoreModel {
  private String cityName;
  private String storeName;
  private String description;
  //    private String coordinates;
}
