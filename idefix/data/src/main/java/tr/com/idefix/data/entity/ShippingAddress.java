package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain = true, fluent = true) public class ShippingAddress {

  @SerializedName("AddressType") @Expose private int addressType;
  @SerializedName("Topic") @Expose private String topic;
  @SerializedName("FirstName") @Expose private String firstName;
  @SerializedName("LastName") @Expose private Object lastName;
  @SerializedName("Email") @Expose private String email;
  @SerializedName("Name") @Expose private Object name;
  @SerializedName("TaxOffice") @Expose private String taxOffice;
  @SerializedName("TaxNumber") @Expose private String taxNumber;
  @SerializedName("TCnumber") @Expose private String tCnumber;
  @SerializedName("CompanyEnabled") @Expose private boolean companyEnabled;
  @SerializedName("CompanyRequired") @Expose private boolean companyRequired;
  @SerializedName("Company") @Expose private Object company;
  @SerializedName("CountryEnabled") @Expose private boolean countryEnabled;
  @SerializedName("CountryId") @Expose private int countryId;
  @SerializedName("CountryName") @Expose private String countryName;
  @SerializedName("StateProvinceEnabled") @Expose private boolean stateProvinceEnabled;
  @SerializedName("StateProvinceId") @Expose private Object stateProvinceId;
  @SerializedName("StateProvinceName") @Expose private Object stateProvinceName;
  @SerializedName("CityEnabled") @Expose private boolean cityEnabled;
  @SerializedName("CityRequired") @Expose private boolean cityRequired;
  @SerializedName("City") @Expose private String city;
  @SerializedName("CityId") @Expose private Object cityId;
  @SerializedName("CountyEnabled") @Expose private boolean countyEnabled;
  @SerializedName("CountyRequired") @Expose private boolean countyRequired;
  @SerializedName("County") @Expose private String county;
  @SerializedName("CountyId") @Expose private Object countyId;
  @SerializedName("StreetAddressEnabled") @Expose private boolean streetAddressEnabled;
  @SerializedName("StreetAddressRequired") @Expose private boolean streetAddressRequired;
  @SerializedName("Address1") @Expose private String address1;
  @SerializedName("StreetAddress2Enabled") @Expose private boolean streetAddress2Enabled;
  @SerializedName("StreetAddress2Required") @Expose private boolean streetAddress2Required;
  @SerializedName("Address2") @Expose private Object address2;
  @SerializedName("ZipPostalCodeEnabled") @Expose private boolean zipPostalCodeEnabled;
  @SerializedName("ZipPostalCodeRequired") @Expose private boolean zipPostalCodeRequired;
  @SerializedName("ZipPostalCode") @Expose private String zipPostalCode;
  @SerializedName("PhoneEnabled") @Expose private boolean phoneEnabled;
  @SerializedName("PhoneRequired") @Expose private boolean phoneRequired;
  @SerializedName("PhoneNumber") @Expose private String phoneNumber;
  @SerializedName("FaxEnabled") @Expose private boolean faxEnabled;
  @SerializedName("FaxRequired") @Expose private boolean faxRequired;
  @SerializedName("FaxNumber") @Expose private Object faxNumber;
  @SerializedName("GSMEnabled") @Expose private boolean gSMEnabled;
  @SerializedName("GSMRequired") @Expose private boolean gSMRequired;
  @SerializedName("GSMNumber") @Expose private String gSMNumber;
  @SerializedName("AvailableCountries") @Expose private List<Object> availableCountries =
      new ArrayList<Object>();
  @SerializedName("AvailableStates") @Expose private List<Object> availableStates =
      new ArrayList<Object>();
  @SerializedName("AvailableCities") @Expose private List<Object> availableCities =
      new ArrayList<Object>();
  @SerializedName("AvailableCounties") @Expose private List<Object> availableCounties =
      new ArrayList<Object>();
  @SerializedName("FormattedCustomAddressAttributes") @Expose private String
      formattedCustomAddressAttributes;
  @SerializedName("CustomAddressAttributes") @Expose private List<Object> customAddressAttributes =
      new ArrayList<Object>();
  @SerializedName("Id") @Expose private int id;
}