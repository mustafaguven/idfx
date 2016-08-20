package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * Created by utkan on 09/10/15.
 */
@Getter
public class CustomerInfoResponseEntity {

    @Expose
    Boolean success;
    @Expose
    String message;
    @SerializedName("CustomerInfo")
    @Expose
    private CustomerInfoEntity customerInfo;
    @SerializedName("SessionObject")
    @Expose
    private String SessionObject;
    @SerializedName("CustomProperties")
    @Expose
    private CustomPropertiesEntity CustomProperties;
}
