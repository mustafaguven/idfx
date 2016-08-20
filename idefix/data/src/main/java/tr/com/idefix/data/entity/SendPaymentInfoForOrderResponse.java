package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain = true, fluent = true)
public class SendPaymentInfoForOrderResponse {

  @Expose Boolean isSuccess;
  @Expose String message;
  @Expose Integer type;
}
