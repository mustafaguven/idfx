package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by mustafaguven on 15.10.2015.
 */

@Getter
@Setter
@Accessors(fluent = true)
public class ChangePasswordResponseEntity {
/*
    @Expose
    @SerializedName("changePasswordResponseSubEntity")
    private List<ChangePasswordResponseSubEntity> changePasswordResponseSubEntity;*/



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
