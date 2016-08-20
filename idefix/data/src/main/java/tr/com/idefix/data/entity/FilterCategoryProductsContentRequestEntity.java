package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 10.29.15.
 */
@Setter
@Accessors(fluent = true, chain = true)
public class FilterCategoryProductsContentRequestEntity {

    @SerializedName("content")
    @Expose
    public FilterCategoryProductsRequestEntity content;
    @SerializedName("reponseType")
    @Expose
    public Integer reponseType;
}
