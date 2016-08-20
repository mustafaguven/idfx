package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created on 9.13.15.
 */
@Getter
@Accessors(fluent = true, chain = true)
public class BasketCountEntity {
    @Expose
    @SerializedName("success")
    private Boolean success;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("CartItemCount")
    private int cartItemCount;
}
