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
public class HelpfulnessEntity {

    @SerializedName("ProductReviewId")
    @Expose
    private Integer productReviewId;
    @SerializedName("HelpfulYesTotal")
    @Expose
    private Integer helpfulYesTotal;
    @SerializedName("HelpfulNoTotal")
    @Expose
    private Integer helpfulNoTotal;
    @SerializedName("CustomProperties")
    @Expose
    private CustomPropertiesEntity customProperties;
}
