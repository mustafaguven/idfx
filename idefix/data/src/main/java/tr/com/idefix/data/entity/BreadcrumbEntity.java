package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 14/10/15.
 */
@Getter
@Accessors(fluent = true)
public class BreadcrumbEntity {

    @SerializedName("Enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("ProductSeName")
    @Expose
    private String productSeName;
    @SerializedName("CategoryBreadcrumb")
    @Expose
    private List<Object> categoryBreadcrumb = new ArrayList<Object>();
    @SerializedName("CustomProperties")
    @Expose
    private CustomPropertiesEntity customProperties;
}
