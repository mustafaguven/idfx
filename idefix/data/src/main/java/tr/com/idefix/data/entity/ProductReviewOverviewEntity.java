package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 14/10/15.
 */
@Getter
@Accessors(fluent = true)
public class ProductReviewOverviewEntity {

    @SerializedName("ProductId")
    @Expose
    private int productId;
    @SerializedName("RatingSum")
    @Expose
    private Integer ratingSum;
    @SerializedName("TotalReviews")
    @Expose
    private Integer totalReviews;
    @SerializedName("TotalRatingCount")
    @Expose
    private Integer totalRatingCount;
    @SerializedName("AllowCustomerReviews")
    @Expose
    private Boolean allowCustomerReviews;
    @SerializedName("SelectedVariationId")
    @Expose
    private Integer selectedVariationId;
    @SerializedName("CustomProperties")
    @Expose
    private CustomPropertiesEntity customProperties;
}
