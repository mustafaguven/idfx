package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Created by utkan on 09/09/15.
 */
@Getter
public class StoreResponseEntity {
    @Expose
    @SerializedName("success")
    private Boolean success;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("Stores")
    private List<StoreEntity> stores = new ArrayList<>();
}
