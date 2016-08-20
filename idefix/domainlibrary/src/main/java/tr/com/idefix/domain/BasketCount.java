package tr.com.idefix.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 9.13.15.
 */
@Getter @Setter @Accessors(fluent = true, chain = true) public class BasketCount {
  private int cartItemCount;
}
