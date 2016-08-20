package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 9.12.15.
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class CategoryEntity extends DictionaryEntity{
    @Expose
    @SerializedName("Level")
    private int level;
    @Expose
    @SerializedName("ParentId")
    private int parentId;
    @Expose
    @SerializedName("Seo")
    private String seo;
    @Expose
    @SerializedName("IsBottom")
    private boolean bottom;
    @Expose
    @SerializedName("ParentPath")
    private String parentPath;

    @SerializedName("Parent")
    @Expose
    public Integer parent;
}
