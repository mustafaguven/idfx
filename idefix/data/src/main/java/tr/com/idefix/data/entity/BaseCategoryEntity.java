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
@Accessors(chain = true, fluent = true)
public class BaseCategoryEntity {

    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("Level")
    @Expose
    private int level;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Parent")
    @Expose
    private int parent;
}
