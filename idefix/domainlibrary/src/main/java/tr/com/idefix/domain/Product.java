package tr.com.idefix.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 14/10/15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class Product {

  private List<Picture> pictures;
  private List<ProductAttribute> productAttributes;
  private List<ProductPerson> productPersons;
  private String name;

  private String attributeName;

  private List<GroupedProductAttribute> groupedProductAttributes;

  private String priceString;

  private String oldPriceString;

  private String sku;

  private String brand;
  private Integer brandId;

  private String shortDescription;
  private String fullDescription;

  private String webLink;
  private int id;
}
