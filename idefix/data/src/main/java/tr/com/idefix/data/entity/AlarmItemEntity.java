package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 13/10/15.
 */
@Getter
@Accessors(chain = true, fluent = true)
public class AlarmItemEntity extends ItemEntity {

    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("CustomerEnteredPrice")
    @Expose
    private String customerEnteredPrice;
}
