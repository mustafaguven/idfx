package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by mustafaguven on 15.10.2015.
 */

@Getter
@Setter
public class ChangePasswordRequestEntity {
    @Expose
    @SerializedName("OldPassword")
    private String oldpassword;
    @Expose
    @SerializedName("NewPassword")
    private String newpassword;
    @Expose
    @SerializedName("ConfirmNewPassword")
    private String confirmpassword;

}
