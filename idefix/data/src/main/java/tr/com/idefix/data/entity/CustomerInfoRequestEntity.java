package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 01/09/15.
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class CustomerInfoRequestEntity {
    // @Expose
    @SerializedName("FirstName")
    private String firstName;
    // @Expose
    @SerializedName("LastName")
    private String lastName;
    // @Expose
    @Expose
    @SerializedName("Gender")
    private String gender;
    // @Expose
    @SerializedName("birthday_month")
    private String birthday_month;
    // @Expose
    @SerializedName("birthday_day")
    private String birthday_day;
    // @Expose
    @SerializedName("birthday_year")
    private String birthday_year;
    // @Expose
    @SerializedName("Email")
    private String eMail;
    @SerializedName("Announcements") // "on" veya "off" olarak"
    private String announcements;
    @SerializedName("PhoneNoHome")
    private String phoneNoHome;
    @SerializedName("PhoneNoCell")
    private String phoneNoCell;
    @SerializedName("CityFieldID")
    private String cityFieldID;
    @SerializedName("CountryFieldID")
    private String countryFieldID;

}
