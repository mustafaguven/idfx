package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created on 10.18.15.
 */
@Getter
@Accessors(fluent = true)
public class ReviewListResponseEntity {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("Reviews")
    @Expose
    private List<ReviewEntity> reviews = new ArrayList<>();
}
