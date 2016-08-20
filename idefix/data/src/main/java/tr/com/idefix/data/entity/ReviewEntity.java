package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created on 10.18.15.
 */
@Getter
@Accessors(fluent = true)
public class ReviewEntity {

    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("AllowViewingProfiles")
    @Expose
    private Boolean allowViewingProfiles;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("ReviewText")
    @Expose
    private String reviewText;
    @SerializedName("Rating")
    @Expose
    private Integer rating;
    @SerializedName("Helpfulness")
    @Expose
    private HelpfulnessEntity helpfulness;
    @SerializedName("WrittenOnStr")
    @Expose
    private String writtenOnStr;
    @SerializedName("WrittenOn")
    @Expose
    private String writtenOn;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("CustomProperties")
    @Expose
    private CustomPropertiesEntity customProperties;
}
