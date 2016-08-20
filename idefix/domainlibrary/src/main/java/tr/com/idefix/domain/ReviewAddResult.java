package tr.com.idefix.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 12.5.15.
 */
@Getter @Setter @Accessors(fluent = true, chain = true) public class ReviewAddResult {

  private Boolean success;
  private String message;
}
