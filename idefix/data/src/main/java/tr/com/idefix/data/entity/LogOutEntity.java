package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 06/10/15.
 */
@Getter
@Accessors(fluent = true)
public class LogOutEntity {
    @Expose
    @SerializedName("Success")
    boolean success;
    @Expose
    @SerializedName("ErrorMessage")
    String errorMessage;
    @Expose
    @SerializedName("SessionObject")
    String sessionObject;
    @Expose
    @SerializedName("CustomProperties")
    CustomPropertiesEntity customProperties;
}
