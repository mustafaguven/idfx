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
public class ProductPersonEntity {

    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("PersonId")
    @Expose
    private Integer personId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("GroupNameCode")
    @Expose
    private String groupNameCode;
    @SerializedName("GroupName")
    @Expose
    private String groupName;
    @SerializedName("CustomProperties")
    @Expose
    private CustomPropertiesEntity customProperties;
}
