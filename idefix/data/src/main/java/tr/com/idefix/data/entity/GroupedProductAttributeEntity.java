package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 14/10/15.
 */
@Getter
@Accessors(fluent = true)
public class GroupedProductAttributeEntity {

    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("AttributeName")
    @Expose
    private String attributeName;
    @SerializedName("Price")
    @Expose
    private Double price;
    @SerializedName("OldPrice")
    @Expose
    private Double oldPrice;
    @SerializedName("Discount")
    @Expose
    private Integer discount;
    @SerializedName("PriceString")
    @Expose
    private String priceString;
    @Setter
    @SerializedName("OldPriceString")
    @Expose
    private String oldPriceString;
    @SerializedName("Sku")
    @Expose
    private String sku;
    @SerializedName("CustomProperties")
    @Expose
    private CustomPropertiesEntity customProperties;
}
