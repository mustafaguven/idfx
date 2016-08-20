package tr.com.idefix.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 9.5.15.
 */
@Getter @Setter @Accessors(chain = true, fluent = false) public class MainCategory {
  private Integer Id;
  private String Name;
  private Integer DisplayOrder;
}
