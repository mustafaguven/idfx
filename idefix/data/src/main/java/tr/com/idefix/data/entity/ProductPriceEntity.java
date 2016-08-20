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
public class ProductPriceEntity {

    @SerializedName("CurrencyCode")
    @Expose
    private String currencyCode;
    @Setter
    @SerializedName("OldPrice")
    @Expose
    private String oldPrice;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("PriceWithDiscount")
    @Expose
    private Object priceWithDiscount;
    @SerializedName("PriceValue")
    @Expose
    private Double priceValue;
    @SerializedName("PriceWithDiscountValue")
    @Expose
    private Double priceWithDiscountValue;
    @SerializedName("CustomerEntersPrice")
    @Expose
    private Boolean customerEntersPrice;
    @SerializedName("CallForPrice")
    @Expose
    private Boolean callForPrice;
    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("HidePrices")
    @Expose
    private Boolean hidePrices;
    @SerializedName("IsRental")
    @Expose
    private Boolean isRental;
    @SerializedName("RentalPrice")
    @Expose
    private Object rentalPrice;
    @SerializedName("DisplayTaxShippingInfo")
    @Expose
    private Boolean displayTaxShippingInfo;
    @SerializedName("CustomProperties")
    @Expose
    private CustomPropertiesEntity customProperties;
}
