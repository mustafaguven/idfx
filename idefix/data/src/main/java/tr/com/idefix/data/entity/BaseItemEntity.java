package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 03/10/15.
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class BaseItemEntity {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Url")
    @Expose
    private String url;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("Price")
    @Expose
    private Double price;
    @SerializedName("OldPrice")
    @Expose
    private Double oldPrice;
    @SerializedName("Discount")
    @Expose
    private Integer discount;
    @SerializedName("StockCode")
    @Expose
    private String stockCode;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Persons")
    @Expose
    private PersonsEntity Persons;

    @SerializedName("MediaType")
    @Expose
    private String mediaType;
    @SerializedName("MediaTypeText")
    @Expose
    private String mediaTypeText;
    @SerializedName("VariationId")
    @Expose
    private Integer variationId;
    @SerializedName("SoldCount")
    @Expose
    private Integer soldCount;
    @SerializedName("CommentCount")
    @Expose
    private Integer commentCount;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("ComingDate")
    @Expose
    private String comingDate;
    @SerializedName("BrandName")
    @Expose
    private String brandName;
    @SerializedName("BrandId")
    @Expose
    private String brandId;
    @SerializedName("Rating")
    @Expose
    private Integer rating;
}
