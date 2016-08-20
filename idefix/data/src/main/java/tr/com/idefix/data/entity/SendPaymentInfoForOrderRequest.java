package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter public class SendPaymentInfoForOrderRequest {
  @Expose @SerializedName("signedRequest") private String signedRequest;
  @Expose @SerializedName("orderCode") private String orderCode;
  @Expose @SerializedName("date") private String date;
  @Expose @SerializedName("bankInformation") private String bankInformation;
  @Expose @SerializedName("customerName") private String customerName;
  @Expose @SerializedName("description") private String description;
}
