package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 14/09/15.
 */
@Getter
@Accessors(chain = true, fluent = true)
public class CategoryTreeResponseEntity {

    @Expose
    @SerializedName("category")
    private CategoryEntity category;
    @Expose
    @SerializedName("categoryTree")
    private List<CategoryEntity> categoryTree = new ArrayList<>();
}
