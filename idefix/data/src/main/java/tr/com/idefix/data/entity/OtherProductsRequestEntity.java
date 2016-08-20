package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 22/10/15.
 */
@Setter
@Accessors(chain = true, fluent = true)
public class OtherProductsRequestEntity {

    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("currentProduct")
    @Expose
    private Integer currentProduct;

}
