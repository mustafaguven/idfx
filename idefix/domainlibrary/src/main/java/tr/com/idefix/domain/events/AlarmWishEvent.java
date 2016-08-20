package tr.com.idefix.domain.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 11.5.15.
 */
@Getter @Setter @Accessors(fluent = true, chain = true) public class AlarmWishEvent {

  private boolean isChecked;
  private String sku;
  private boolean bell;
  private boolean favorite;

  public AlarmWishEvent() {
  }

  public AlarmWishEvent(boolean b, String sku) {
    isChecked = b;
    this.sku = sku;
  }
}
