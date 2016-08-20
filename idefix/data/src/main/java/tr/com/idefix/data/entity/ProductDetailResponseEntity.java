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
public class ProductDetailResponseEntity {

    @SerializedName("template")
    @Expose
    private String template;
    @SerializedName("product")
    @Expose
    private ProductEntity product;

}
