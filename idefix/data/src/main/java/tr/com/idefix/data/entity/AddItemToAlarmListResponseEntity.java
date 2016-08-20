package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created on 10.11.15.
 */
@Getter
@Accessors(chain = true, fluent = true)
public class AddItemToAlarmListResponseEntity {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("AlarmList")
    @Expose
    private AlarmListEntity alarmList;

}
