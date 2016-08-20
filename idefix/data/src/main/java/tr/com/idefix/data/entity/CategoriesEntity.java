package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 22/10/15.
 */
@Getter
@Accessors(fluent = true)
public class CategoriesEntity {

    @SerializedName("Category")
    @Expose
    private List<CategoryEntity> categoryList = new ArrayList<>();
}
