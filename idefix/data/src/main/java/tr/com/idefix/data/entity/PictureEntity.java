package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 10.11.15.
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class PictureEntity {

    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("FullSizeImageUrl")
    @Expose
    private String fullSizeImageUrl;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("AlternateText")
    @Expose
    private String alternateText;
    @SerializedName("CustomProperties")
    @Expose
    private CustomPropertiesEntity customProperties;
}
