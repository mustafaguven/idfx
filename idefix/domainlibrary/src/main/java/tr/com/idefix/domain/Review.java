package tr.com.idefix.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 10.18.15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class Review {
  private String review;
  private String date;
}
