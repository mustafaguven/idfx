package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 10.18.15.
 */
@Setter
@Accessors(chain = true, fluent = true)
public class ReviewRequestEntity {
    @SerializedName("productId")
    @Expose
    private Integer productId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("message")
    @Expose
    private String message;
}
