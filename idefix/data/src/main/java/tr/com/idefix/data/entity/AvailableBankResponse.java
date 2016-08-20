package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain = true, fluent = true) public class AvailableBankResponse {

  @Expose Boolean success;
  @Expose String message;
  @SerializedName("Banks") @Expose private List<BankEntity> bankEntityList = new ArrayList<>();
}
