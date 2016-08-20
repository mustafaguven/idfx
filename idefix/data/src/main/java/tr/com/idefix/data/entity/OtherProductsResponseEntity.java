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
public class OtherProductsResponseEntity {

    @SerializedName("Success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Products")
    @Expose
    private ProductsEntity products;
}
