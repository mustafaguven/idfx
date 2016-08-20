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
public class AlarmListEntity extends BaseInformProductEntity {

    @SerializedName("EmailAlarmlistEnabled")
    @Expose
    private Boolean EmailAlarmlistEnabled;

    @SerializedName("Items")
    @Expose
    private List<AlarmItemEntity> items = new ArrayList<>();
}
