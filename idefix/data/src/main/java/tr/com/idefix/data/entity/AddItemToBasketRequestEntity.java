package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by mustafaguven on 18.10.2015.
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class AddItemToBasketRequestEntity {
    @Expose
    @SerializedName("Code")
    private String code;
    @Expose
    @SerializedName("quantity")
    private int quantity;


}
