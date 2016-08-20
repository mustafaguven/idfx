package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Created on 9.6.15.
 */
@Getter
public class BannerResponseEntity {
    @Expose
    private Boolean success;
    @Expose
    @SerializedName("Banners")
    private List<BannerEntity> bannerEntityList = new ArrayList<BannerEntity>();
    @Expose
    @SerializedName("Count")
    private Integer count;
}
