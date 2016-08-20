package tr.com.idefix.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 15/10/15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class ProductPerson {
  String name;
  String groupName;
  private Integer personId;
  private Integer productId;
}
