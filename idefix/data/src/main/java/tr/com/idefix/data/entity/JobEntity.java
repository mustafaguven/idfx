package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 9.12.15.
 */
@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class JobEntity {
    @Expose
    @SerializedName("Disabled")
    private boolean Disabled;
    @Expose
    @SerializedName("Group")
    private String Group;
    @Expose
    @SerializedName("Selected")
    private boolean Selected;
    @Expose
    @SerializedName("Text")
    private String Text;
    @Expose
    @SerializedName("Value")
    private String Value;
}
