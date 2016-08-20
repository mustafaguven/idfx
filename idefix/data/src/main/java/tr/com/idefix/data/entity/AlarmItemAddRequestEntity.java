package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 13/10/15.
 */
@Setter
@Accessors(chain = true, fluent = true)
public class AlarmItemAddRequestEntity {
    @Expose
    @SerializedName("Code")
    String code;
    @Expose
    @SerializedName("warningDate")
    String date;
    @Expose
    @SerializedName("warningPrice")
    String price;
}
