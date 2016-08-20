package tr.com.idefix.android.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 10.3.15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class TopCategoryInfoItemModel
    extends BaseModel {

  private int id;
  private String name;
  private String imageUrl;
  private Double price;
  private Double oldPrice;
  private String sku;
}
