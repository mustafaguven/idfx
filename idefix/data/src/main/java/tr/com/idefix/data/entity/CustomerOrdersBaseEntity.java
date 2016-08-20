package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Created by mustafaguven on 20.10.2015.
 */

@Getter
public class CustomerOrdersBaseEntity {

    @SerializedName("Orders")
    @Expose
    private List<OrderEntity> Orders = new ArrayList<>();
    @SerializedName("RecurringOrders")
    @Expose
    private List<Object> RecurringOrders = new ArrayList<>();
    @SerializedName("CancelRecurringPaymentErrors")
    @Expose
    private List<Object> CancelRecurringPaymentErrors = new ArrayList<>();
    @SerializedName("CustomProperties")
    @Expose
    private CustomPropertiesEntity CustomProperties;

}
