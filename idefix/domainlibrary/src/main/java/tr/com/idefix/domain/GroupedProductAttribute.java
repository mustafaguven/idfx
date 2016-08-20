package tr.com.idefix.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 11.25.15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class GroupedProductAttribute {

  private Integer productId;
  private String attributeName;
  private String priceString;
  private String oldPriceString;
  private String sku;
}
