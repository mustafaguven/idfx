package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by utkan on 03/09/15.
 */
@Getter
@Setter
public class CountryEntity {
    @Expose
    @SerializedName("Disabled")
    private Boolean disabled;
    @Expose
    @SerializedName("Group")
    private Object group;
    @Expose
    @SerializedName("Selected")
    private Boolean selected;
    @Expose
    @SerializedName("Text")
    private String text;
    @Expose
    @SerializedName("Value")
    private String value;
}
