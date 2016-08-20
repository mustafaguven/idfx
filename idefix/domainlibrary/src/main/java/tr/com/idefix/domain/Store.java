package tr.com.idefix.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 09/09/15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class Store {
  private String cityName;
  private String storeName;
  private String description;
  private String coordinates;
}
