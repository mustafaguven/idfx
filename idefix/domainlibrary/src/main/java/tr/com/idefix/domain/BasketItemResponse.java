package tr.com.idefix.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 22/10/15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class BasketItemResponse {

  private boolean success;
  private String message;
}
