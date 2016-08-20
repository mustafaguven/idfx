package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 22/10/15.
 */
@Getter
@Accessors(fluent = true)
public class ProductBaseEntity {

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("ComingDate")
    @Expose
    private String comingDate;

    @SerializedName("Id")
    @Expose
    private Integer id;
}
