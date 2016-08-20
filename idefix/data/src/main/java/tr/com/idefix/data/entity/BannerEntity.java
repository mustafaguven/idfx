package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * Created on 9.6.15.
 */
@Getter
public class BannerEntity {
    @Expose
    @SerializedName("CategoryId")
    private Integer categoryId;
    @Expose
    @SerializedName("WidgetzoneId")
    private Integer widgetzoneId;
    @Expose
    @SerializedName("BannerHtml")
    private String bannerHtml;
    @Expose
    @SerializedName("DisplayOrder")
    private Integer displayOrder;
    @Expose
    @SerializedName("Active")
    private Boolean active;
    @Expose
    @SerializedName("StartDate")
    private String startDate;
    @Expose
    @SerializedName("EndDate")
    private String endDate;
    @Expose
    @SerializedName("Topic")
    private String topic;
    @Expose
    @SerializedName("Size")
    private String size;
    @Expose
    @SerializedName("ImageUrl")
    private String imageUrl;
    @Expose
    @SerializedName("AltText")
    private String altText;
    @Expose
    @SerializedName("Description")
    private String description;
    @Expose
    @SerializedName("LinkUrl")
    private String linkUrl;
    @Expose
    @SerializedName("BannerType")
    private String BannerType;
    @Expose
    @SerializedName("CatalogOrProduct")
    private String CatalogOrProduct;





}
