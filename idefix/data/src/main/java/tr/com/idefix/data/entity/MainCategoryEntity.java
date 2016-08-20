package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created on 9.5.15.
 */
@Getter
@Accessors(fluent = true)
public class MainCategoryEntity {
    @Expose
    @SerializedName("Id")
    private Integer id;
    @Expose
    @SerializedName("Name")
    private String name;
    @Expose
    @SerializedName("DisplayOrder")
    private Integer displayOrder;
}
