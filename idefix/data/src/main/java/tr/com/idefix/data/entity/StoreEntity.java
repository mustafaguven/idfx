package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * Created by utkan on 09/09/15.
 */
@Getter
public class StoreEntity {
    @Expose
    @SerializedName("CityName")
    private String cityName;
    @Expose
    @SerializedName("StoreName")
    private String storeName;
    @Expose
    @SerializedName("Description")
    private String description;
    @Expose
    @SerializedName("Coordinates")
    private String coordinates;
}
