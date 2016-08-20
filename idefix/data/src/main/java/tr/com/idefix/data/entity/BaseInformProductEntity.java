package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 13/10/15.
 */
@Getter
@Accessors(chain = true, fluent = true)
public class BaseInformProductEntity {

    @SerializedName("CustomerGuid")
    @Expose
    private String customerGuid;
    @SerializedName("CustomerFullname")
    @Expose
    private String customerFullname;
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

    @SerializedName("Warnings")
    @Expose
    private List<Object> warnings = new ArrayList<>();
    @SerializedName("CustomProperties")
    @Expose
    private CustomPropertiesEntity customProperties;
}
