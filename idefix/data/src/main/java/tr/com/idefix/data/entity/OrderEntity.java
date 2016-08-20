package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by mustafaguven on 20.10.2015.
 */

@Getter
@Setter
public class OrderEntity {
    @SerializedName("OrderTotal")
    @Expose
    private String OrderTotal;
    @SerializedName("IsReturnRequestAllowed")
    @Expose
    private Boolean IsReturnRequestAllowed;
    @SerializedName("OrderStatus")
    @Expose
    private String OrderStatus;
    @SerializedName("PaymentStatus")
    @Expose
    private String PaymentStatus;
    @SerializedName("ShippingStatus")
    @Expose
    private String ShippingStatus;
    @SerializedName("CreatedOn")
    @Expose
    private String CreatedOn;
    @SerializedName("OrderCode")
    @Expose
    private String OrderCode;
    @SerializedName("IsTransfer")
    @Expose
    private Object IsTransfer;
    @SerializedName("TrackingNumber")
    @Expose
    private Object TrackingNumber;
    @SerializedName("IsPartial")
    @Expose
    private Boolean IsPartial;
    @SerializedName("Id")
    @Expose
    private Integer Id;
    @SerializedName("CustomProperties")
    @Expose
    private CustomProperties CustomProperties;
}
