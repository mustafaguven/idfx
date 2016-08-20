package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 01/09/15.
 */
@Setter
@Accessors(chain = true, fluent = true)
public class LoginRequestEntity {
    // @Expose
    @SerializedName("CheckoutAsGuest")
    private Boolean checkoutAsGuest;
    // @Expose
    @SerializedName("Email")
    private String email;
    // @Expose
    @SerializedName("UsernamesEnabled")
    private Boolean userNamesEnabled;
     @Expose
    @SerializedName("Username")
    private String username;
    // @Expose
    @SerializedName("Password")
    private String password;
    // @Expose
    @SerializedName("RememberMe")
    private Boolean rememberMe;
    // @Expose
    @SerializedName("DisplayCaptcha")
    private Boolean displayCaptcha;
    // @Expose
    @SerializedName("CustomProperties")
    private CustomPropertiesEntity customProperties;
}
