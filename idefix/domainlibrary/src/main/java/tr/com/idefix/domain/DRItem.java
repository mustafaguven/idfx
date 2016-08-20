package tr.com.idefix.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 03/10/15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class DRItem {

  private int id;
  private String name;
  private String imageUrl;
  private Double price;
  private Double oldPrice;
  private String sku;
  private int productID;
  private String unitPrice;
  private String categoryName;
  private String endDate;
  private String customerEnteredPrice;
  private int sale_status;
  private String brandName;
}
