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
@Getter
@Accessors(chain = true, fluent = true)
public class WishListResponseEntity {

    @Expose
    @SerializedName("success")
    private Boolean success;
    @Expose
    @SerializedName("message")
    private String message;

    @SerializedName("CustomerGuid")
    @Expose
    private String customerGuid;
    @SerializedName("CustomerFullname")
    @Expose
    private String customerFullname;
    @SerializedName("EmailWishlistEnabled")
    @Expose
    private Boolean emailWishlistEnabled;
    @SerializedName("ShowSku")
    @Expose
    private Boolean showSku;
    @SerializedName("ShowProductImages")
    @Expose
    private Boolean showProductImages;
    @SerializedName("IsEditable")
    @Expose
    private Boolean isEditable;
    @SerializedName("DisplayAddToCart")
    @Expose
    private Boolean displayAddToCart;
    @SerializedName("DisplayTaxShippingInfo")
    @Expose
    private Boolean displayTaxShippingInfo;
    @SerializedName("Items")
    @Expose
    private List<ItemEntity> itemList = new ArrayList<>();
    @SerializedName("Warnings")
    @Expose
    private List<Object> warnings = new ArrayList<>();
    @SerializedName("CustomProperties")
    @Expose
    private CustomPropertiesEntity customProperties;
}
