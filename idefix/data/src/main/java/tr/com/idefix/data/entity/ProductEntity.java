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
public class ProductEntity extends ProductBaseEntity {

    @SerializedName("Price")
    @Expose
    private Double price;
    
    @SerializedName("OldPrice")
    @Expose
    private Double oldPrice;

    @SerializedName("Discount")
    @Expose
    private Double discount;

    @SerializedName("StockCode")
    @Expose
    private String stockCode;

    @SerializedName("Persons")
    @Expose
    private PersonsEntity persons;

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

    @SerializedName("BrandName")
    @Expose
    private String brandName;
    @SerializedName("BrandId")
    @Expose
    private String brandId;
    @SerializedName("Rating")
    @Expose
    private Integer rating;

    @SerializedName("Url")
    @Expose
    private String url;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("PictureModels")
    @Expose
    private List<PictureModelEntity> pictureModels = new ArrayList<>();

    @SerializedName("ShortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("FullDescription")
    @Expose
    private String fullDescription;
    @SerializedName("ProductTemplateViewPath")
    @Expose
    private String productTemplateViewPath;
    @SerializedName("SeName")
    @Expose
    private String seName;
    @SerializedName("Sku")
    @Expose
    private String sku;
    @SerializedName("ManufacturerPartNumber")
    @Expose
    private Object manufacturerPartNumber;
    @SerializedName("Gtin")
    @Expose
    private String gtin;
    @SerializedName("VendorModel")
    @Expose
    private VendorModelEntity vendorModel;
    @SerializedName("IsShipEnabled")
    @Expose
    private Boolean isShipEnabled;
    @SerializedName("IsFreeShipping")
    @Expose
    private Boolean isFreeShipping;
    @SerializedName("FreeShippingNotificationEnabled")
    @Expose
    private Boolean freeShippingNotificationEnabled;
    @SerializedName("DeliveryDate")
    @Expose
    private Object deliveryDate;
    @SerializedName("IsRental")
    @Expose
    private Boolean isRental;
    @SerializedName("RentalStartDate")
    @Expose
    private Object rentalStartDate;
    @SerializedName("RentalEndDate")
    @Expose
    private Object rentalEndDate;
    @SerializedName("StockAvailability")
    @Expose
    private String stockAvailability;

    @SerializedName("ProductPrice")
    @Expose
    private ProductPriceEntity productPrice;
    @SerializedName("Breadcrumb")
    @Expose
    private BreadcrumbEntity breadcrumb;
    @SerializedName("ProductAttributes")
    @Expose
    private List<ProductAttributeEntity> productAttributes = new ArrayList<>();
    @SerializedName("GroupedProductAttributes")
    @Expose
    private List<GroupedProductAttributeEntity> groupedProductAttributes = new ArrayList<>();
    @SerializedName("ProductPersons")
    @Expose
    private List<ProductPersonEntity> productPersons = new ArrayList<>();
    @SerializedName("ProductBrands")
    @Expose
    private List<ProductBrandEntity> productBrands = new ArrayList<>();
    @SerializedName("ProductSpecifications")
    @Expose
    private List<ProductSpecificationEntity> productSpecifications = new ArrayList<>();
    @SerializedName("ProductManufacturers")
    @Expose
    private List<Object> productManufacturers = new ArrayList<>();
    @SerializedName("ProductReviewOverview")
    @Expose
    private ProductReviewOverviewEntity productReviewOverview;

    @SerializedName("CustomProperties")
    @Expose
    private CustomPropertiesEntity customProperties;
}
