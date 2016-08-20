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
public class VendorModelEntity {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("SeName")
    @Expose
    private String seName;
    @SerializedName("VendorCode")
    @Expose
    private String vendorCode;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("CustomProperties")
    @Expose
    private CustomPropertiesEntity customProperties;
}
