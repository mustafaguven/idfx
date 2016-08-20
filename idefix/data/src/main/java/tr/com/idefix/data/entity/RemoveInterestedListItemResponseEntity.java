package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created on 10.17.15.
 */
@Getter
@Accessors(chain = true, fluent = true)
public class RemoveInterestedListItemResponseEntity {

    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Type")
    @Expose
    private Integer type;
    @SerializedName("Message")
    @Expose
    private String message;
}
