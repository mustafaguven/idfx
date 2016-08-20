package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 10.27.15.
 */
@Setter
@Accessors(fluent = true, chain = true)
public class FilterCategoryProductsRequestEntity {

    @SerializedName("page")
    @Expose
    public Integer page;
    @SerializedName("sortfield")
    @Expose
    public String sortfield;
    @SerializedName("sortorder")
    @Expose
    public String sortorder;
    @SerializedName("size")
    @Expose
    public Integer size;
    @SerializedName("categoryid")
    @Expose
    public String categoryid;
    @SerializedName("parentId")
    @Expose
    public Integer parentId;
    @SerializedName("brandIds")
    @Expose
    public String brandIds;
    @SerializedName("mediatypes")
    @Expose
    public String mediatypes;
    @SerializedName("minPrice")
    @Expose
    public Integer minPrice = -1;
    @SerializedName("maxPrice")
    @Expose
    public Integer maxPrice = -1;
    @SerializedName("propVal")
    @Expose
    public String propVal;
}
