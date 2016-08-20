package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 22/10/15.
 */
@Getter
@Accessors(fluent = true)
public class ProductOtherEntity extends ProductEntity {

    @SerializedName("Categories")
    @Expose
    private CategoriesEntity categories;
}
