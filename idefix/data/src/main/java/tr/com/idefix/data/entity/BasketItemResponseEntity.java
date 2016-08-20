package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by mustafaguven on 18.10.2015.
 */

@Getter
@Accessors(fluent = true)
public class BasketItemResponseEntity {
    @Expose
    @SerializedName("status")
    private Boolean status;

    @Expose
    @SerializedName("success")
    private Boolean success;

    @Expose
    @SerializedName("Type")
    private Integer type;
    @Expose
    @SerializedName("message")
    private String message;


}