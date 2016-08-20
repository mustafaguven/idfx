package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created on 10.11.15.
 */
@Getter @Accessors(chain = true, fluent = true) public class ItemEntity {
  @SerializedName("Sku") @Expose private String sku;
  @SerializedName("Picture") @Expose private PictureEntity picture;
  @SerializedName("ProductId") @Expose private Integer productId;
  @SerializedName("ProductName") @Expose private String productName;
  @SerializedName("ProductSeName") @Expose private String productSeName;
  @SerializedName("UnitPrice") @Expose private String unitPrice;
  @SerializedName("SubTotal") @Expose private String subTotal;
  @SerializedName("Discount") @Expose private Object discount;
  @SerializedName("Quantity") @Expose private Integer quantity;
  @SerializedName("İtemImageUrl") @Expose private String itemImageUrl;
  @SerializedName("AllowedQuantities") @Expose private List<Object> allowedQuantities =
      new ArrayList<Object>();
  @SerializedName("AttributeInfo") @Expose private String attributeInfo;
  @SerializedName("RecurringInfo") @Expose private Object recurringInfo;
  @SerializedName("RentalInfo") @Expose private Object rentalInfo;
  @SerializedName("Warnings") @Expose private List<Object> warnings = new ArrayList<Object>();
  @SerializedName("CategoryName") @Expose private String categoryName;
  @SerializedName("DateAdded") @Expose private String dateAdded;
  @SerializedName("SaleStatus") @Expose private Integer saleStatus;
  @SerializedName("Id") @Expose private Integer id;
  @SerializedName("CustomProperties") @Expose private CustomPropertiesEntity customProperties;
}
