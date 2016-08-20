package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 14/10/15.
 */
@Getter
@Accessors(fluent = true)
public class ProductBrandEntity {

    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("BrandId")
    @Expose
    private Integer brandId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("CustomProperties")
    @Expose
    private CustomPropertiesEntity customProperties;
}
