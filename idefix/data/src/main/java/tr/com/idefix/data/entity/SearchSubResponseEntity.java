package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by mustafaguven on 24.10.2015.
 */

@Getter
@Setter
@Accessors(fluent = true)
public class SearchSubResponseEntity {

    @SerializedName("IsFuzzyUsed")
    @Expose
    private Boolean isFuzzyUsed;

    @SerializedName("SearchTerm")
    @Expose
    private String searchTerm;

    @SerializedName("Products")
    @Expose
    private List<ProductOtherEntity> products = new ArrayList<>();

    @SerializedName("HitCount")
    @Expose
    private int hitCount;

    @SerializedName("Categories")
    @Expose
    private List<CategoryEntity> categories = new ArrayList<>();

    @SerializedName("Brands")
    @Expose
    private List<BrandEntity> brands = new ArrayList<>();

    @SerializedName("MediaTypes")
    @Expose
    private List<MediaTypeEntity> mediaTypes = new ArrayList<>();

    @SerializedName("Prices")
    @Expose
    private List<PriceEntity> prices = new ArrayList<>();

    @SerializedName("Writers")
    @Expose
    private String writers;
    @SerializedName("PropertyValues")
    @Expose
    public List<Object> propertyValues = new ArrayList<Object>();

}
