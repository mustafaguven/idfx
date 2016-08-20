package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9.6.15.
 */
@Getter
@Setter
public class SaveNewCustomerResponseEntity {
    @Expose
    @SerializedName("isSuccess")
    private Boolean isSuccess;
    @Expose
    @SerializedName("type")
    private int type;

    @Expose
    @SerializedName("message")
    private String message;

}
