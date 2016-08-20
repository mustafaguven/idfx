package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 10.4.15.
 */
@Getter
@Setter
@Accessors(chain = true, fluent = false)
public class CustomerInfoEntity {

    @SerializedName("Email")
    @Expose
    private String Email;
    @SerializedName("PasswordUpdateDate")
    @Expose
    private Object PasswordUpdateDate;
    @SerializedName("CheckUsernameAvailabilityEnabled")
    @Expose
    private Boolean CheckUsernameAvailabilityEnabled;
    @SerializedName("AllowUsersToChangeUsernames")
    @Expose
    private Boolean AllowUsersToChangeUsernames;
    @SerializedName("UsernamesEnabled")
    @Expose
    private Boolean UsernamesEnabled;
    @SerializedName("Username")
    @Expose
    private String Username;
    @SerializedName("GenderEnabled")
    @Expose
    private Boolean GenderEnabled;
    @SerializedName("Gender")
    @Expose
    private String Gender;
    @SerializedName("FirstName")
    @Expose
    private String FirstName;
    @SerializedName("LastName")
    @Expose
    private String LastName;
    @SerializedName("DateOfBirthEnabled")
    @Expose
    private Boolean DateOfBirthEnabled;
    @SerializedName("DateOfBirthDay")
    @Expose
    private Integer DateOfBirthDay;
    @SerializedName("DateOfBirthMonth")
    @Expose
    private Integer DateOfBirthMonth;
    @SerializedName("DateOfBirthYear")
    @Expose
    private Integer DateOfBirthYear;
    @SerializedName("CompanyEnabled")
    @Expose
    private Boolean CompanyEnabled;
    @SerializedName("CompanyRequired")
    @Expose
    private Boolean CompanyRequired;
    @SerializedName("Company")
    @Expose
    private Object Company;
    @SerializedName("StreetAddressEnabled")
    @Expose
    private Boolean StreetAddressEnabled;
    @SerializedName("StreetAddressRequired")
    @Expose
    private Boolean StreetAddressRequired;
    @SerializedName("StreetAddress")
    @Expose
    private Object StreetAddress;
    @SerializedName("StreetAddress2Enabled")
    @Expose
    private Boolean StreetAddress2Enabled;
    @SerializedName("StreetAddress2Required")
    @Expose
    private Boolean StreetAddress2Required;
    @SerializedName("StreetAddress2")
    @Expose
    private Object StreetAddress2;
    @SerializedName("ZipPostalCodeEnabled")
    @Expose
    private Boolean ZipPostalCodeEnabled;
    @SerializedName("ZipPostalCodeRequired")
    @Expose
    private Boolean ZipPostalCodeRequired;
    @SerializedName("ZipPostalCode")
    @Expose
    private Object ZipPostalCode;
    @SerializedName("CityEnabled")
    @Expose
    private Boolean CityEnabled;
    @SerializedName("CityRequired")
    @Expose
    private Boolean CityRequired;
    @SerializedName("City")
    @Expose
    private String City;
    @SerializedName("CityId")
    @Expose
    private Integer CityId;
    @SerializedName("CountryEnabled")
    @Expose
    private Boolean CountryEnabled;
    @SerializedName("CountryRequired")
    @Expose
    private Boolean CountryRequired;
    @SerializedName("CountryId")
    @Expose
    private Integer CountryId;
    @SerializedName("AvailableCountries")
    @Expose
    private List<Object> AvailableCountries = new ArrayList<Object>();
    @SerializedName("StateProvinceEnabled")
    @Expose
    private Boolean StateProvinceEnabled;
    @SerializedName("StateProvinceRequired")
    @Expose
    private Boolean StateProvinceRequired;
    @SerializedName("StateProvinceId")
    @Expose
    private Integer StateProvinceId;
    @SerializedName("PhoneEnabled")
    @Expose
    private Boolean PhoneEnabled;
    @SerializedName("PhoneRequired")
    @Expose
    private Boolean PhoneRequired;
    @SerializedName("Phone")
    @Expose
    private String Phone;
    @SerializedName("FaxEnabled")
    @Expose
    private Boolean FaxEnabled;
    @SerializedName("FaxRequired")
    @Expose
    private Boolean FaxRequired;
    @SerializedName("Fax")
    @Expose
    private Object Fax;
    @SerializedName("GSMEnabled")
    @Expose
    private Boolean GSMEnabled;
    @SerializedName("GSMRequired")
    @Expose
    private Boolean GSMRequired;
    @SerializedName("GSM")
    @Expose
    private String GSM;
    @SerializedName("Jobs")
    @Expose
    private String Jobs;
    @SerializedName("JobsId")
    @Expose
    private Integer JobsId;
    @SerializedName("Education")
    @Expose
    private String Education;
    @SerializedName("EducationId")
    @Expose
    private Integer EducationId;
    @SerializedName("NewsletterEnabled")
    @Expose
    private Boolean NewsletterEnabled;
    @SerializedName("Newsletter")
    @Expose
    private Boolean Newsletter;
    @SerializedName("FacebookId")
    @Expose
    private Object FacebookId;
    @SerializedName("SignatureEnabled")
    @Expose
    private Boolean SignatureEnabled;
    @SerializedName("Signature")
    @Expose
    private Object Signature;
    @SerializedName("TimeZoneId")
    @Expose
    private Object TimeZoneId;
    @SerializedName("AllowCustomersToSetTimeZone")
    @Expose
    private Boolean AllowCustomersToSetTimeZone;
    @SerializedName("VatNumber")
    @Expose
    private Object VatNumber;
    @SerializedName("VatNumberStatusNote")
    @Expose
    private Object VatNumberStatusNote;
    @SerializedName("DisplayVatNumber")
    @Expose
    private Boolean DisplayVatNumber;
    @SerializedName("AssociatedExternalAuthRecords")
    @Expose
    private List<Object> AssociatedExternalAuthRecords = new ArrayList<Object>();
    @SerializedName("NumberOfExternalAuthenticationProviders")
    @Expose
    private Integer NumberOfExternalAuthenticationProviders;
    @SerializedName("CustomerMiniAddresses")
    @Expose
    private Object CustomerMiniAddresses;
    @SerializedName("CustomerAttributes")
    @Expose
    private List<Object> CustomerAttributes = new ArrayList<Object>();
    @SerializedName("CustomProperties")
    @Expose
    private CustomPropertiesEntity CustomProperties;
}
