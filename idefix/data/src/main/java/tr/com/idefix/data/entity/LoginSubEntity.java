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
public class LoginSubEntity {

    @Expose
    @SerializedName("CheckoutAsGuest")
    private Boolean checkoutAsGuest;
    @Expose
    @SerializedName("Email")
    private String email;
    @Expose
    @SerializedName("UsernamesEnabled")
    private Boolean usernamesEnabled;
    @Expose
    @SerializedName("Username")
    private String username;
    @Expose
    @SerializedName("Password")
    private String password;
    @Expose
    @SerializedName("RememberMe")
    private Boolean rememberMe;
    @Expose
    @SerializedName("DisplayCaptcha")
    private Boolean displayCaptcha;
    @Expose
    @SerializedName("Success")
    private Boolean success;
    @Expose
    @SerializedName("ErrorMessage")
    private String errorMessage;
    @Expose
    @SerializedName("CustomProperties")
    private CustomPropertiesEntity customProperties;
}
