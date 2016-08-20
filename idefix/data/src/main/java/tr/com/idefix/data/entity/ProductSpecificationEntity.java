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
public class ProductSpecificationEntity {

    @SerializedName("SpecificationAttributeId")
    @Expose
    private Integer specificationAttributeId;
    @SerializedName("SpecificationAttributeName")
    @Expose
    private String specificationAttributeName;
    @SerializedName("ValueRaw")
    @Expose
    private String valueRaw;
    @SerializedName("CustomProperties")
    @Expose
    private CustomPropertiesEntity customProperties;

}
