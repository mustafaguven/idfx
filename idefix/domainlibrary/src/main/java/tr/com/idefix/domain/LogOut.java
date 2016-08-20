package tr.com.idefix.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 06/10/15.
 */
@Getter @Setter @Accessors(fluent = true, chain = true) public class LogOut {
  boolean success;
  String errorMessage;
}
