package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 13/10/15.
 */
@Getter
@Accessors(chain = true, fluent = true)
public class WishListEntity extends BaseInformProductEntity{

    @SerializedName("EmailWishlistEnabled")
    @Expose
    private Boolean emailWishlistEnabled;

    @SerializedName("Items")
    @Expose
    private List<ItemEntity> items = new ArrayList<>();

}
