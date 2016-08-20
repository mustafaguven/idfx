package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 30/09/15.
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class BestSellerEntity extends BaseItemEntity {

    @SerializedName("Categories")
    @Expose
    private BestSellerCategoriesEntity Categories;
}
