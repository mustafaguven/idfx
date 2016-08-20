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
public class PasswordRecoveryEntity {
    @Expose
    @SerializedName("Email")
    private String Email;
    @Expose
    @SerializedName("Result")
    private String Result;

}
