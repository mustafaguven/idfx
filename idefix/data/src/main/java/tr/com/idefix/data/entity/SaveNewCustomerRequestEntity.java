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
public class SaveNewCustomerRequestEntity {
    @Expose
    @SerializedName("password")
    private String password;
    @Expose
    @SerializedName("password2")
    private String password2;

    @Expose
    @SerializedName("firstName")
    private String firstName;

    @Expose
    @SerializedName("lastName")
    private String lastName;

    @Expose
    @SerializedName("gender") //M: Erkek, F:Kadın
    private String gender;

    @Expose
    @SerializedName("eMail")
    private String eMail;

    @Expose
    @SerializedName("announcements") //on: Bildirim maili istiyor
    private String announcements;

    @Expose
    @SerializedName("ccs")
    private String ccs;

    @Expose
    @SerializedName("FacebookID")
    private String FacebookID;
}
