package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by mustafaguven on 16.10.2015.
 */

@Getter
@Setter
public class ChangePasswordResponseSubEntity {

    @Expose
    @SerializedName("OldPassword")
    private String oldpassword;
    @Expose
    @SerializedName("NewPassword")
    private String newpassword;
    @Expose
    @SerializedName("ConfirmNewPassword")
    private String confirmpassword;
    @Expose
    @SerializedName("Result")
    private String result;
    @Expose
    @SerializedName("CustomProperties")
    private CustomProperties CustomProperties;
}
