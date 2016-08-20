package tr.com.idefix.data.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by mustafaguven on 24.10.2015.
 */

@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class SearchRequestEntity {
    // @Expose
    @SerializedName("content")
    private ContentEntity content;

    @SerializedName("reponseType")
    private int reponseType;

}