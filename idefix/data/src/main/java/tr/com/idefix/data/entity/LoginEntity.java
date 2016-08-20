package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 01/09/15.
 */

@Getter
@Accessors(fluent = true)
public class LoginEntity {
    @Expose
    @SerializedName("loginModel")
    private LoginSubEntity loginSubEntity;
    @Expose
    @SerializedName("Success")
    private Boolean success;
    @Expose
    @SerializedName("ErrorMessage")
    private String errorMessage;
    @Expose
    @SerializedName("SessionObject")
    private String sessionObject;
    @Expose
    @SerializedName("CustomProperties")
    private CustomPropertiesEntity customProperties;
}
