package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 14/10/15.
 */
@Getter
@Accessors(fluent = true)
public class ProductAttributeEntity {

    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("ProductAttributeId")
    @Expose
    private Integer productAttributeId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Description")
    @Expose
    private Object description;
    @SerializedName("TextPrompt")
    @Expose
    private String textPrompt;
    @SerializedName("IsRequired")
    @Expose
    private Boolean isRequired;
    @SerializedName("DefaultValue")
    @Expose
    private Object defaultValue;
    @SerializedName("SelectedDay")
    @Expose
    private Object selectedDay;
    @SerializedName("SelectedMonth")
    @Expose
    private Object selectedMonth;
    @SerializedName("SelectedYear")
    @Expose
    private Object selectedYear;
    @SerializedName("AllowedFileExtensions")
    @Expose
    private List<Object> allowedFileExtensions = new ArrayList<Object>();
    @SerializedName("AttributeControlType")
    @Expose
    private Integer attributeControlType;
    @SerializedName("Values")
    @Expose
    private List<Object> values = new ArrayList<Object>();
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("CustomProperties")
    @Expose
    private CustomPropertiesEntity customProperties;
}
