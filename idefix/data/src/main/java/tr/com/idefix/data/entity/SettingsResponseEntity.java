package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class SettingsResponseEntity {

    @SerializedName("ImageServer")
    @Expose
    private String ImageServer;

}
