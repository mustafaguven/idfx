package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by mustafaguven on 19.10.2015.
 */
@Getter
@Accessors(fluent = true)
public class AlarmListResponseEntity {

    @Expose
    @SerializedName("success")
    private Boolean success;

    @SerializedName("AlarmList")
    @Expose
    private AlarmListEntity alarmList;
}