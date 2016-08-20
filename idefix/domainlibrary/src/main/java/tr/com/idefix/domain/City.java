package tr.com.idefix.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by mustafaguven on 15.10.2015.
 */

@Getter @Setter @Accessors(chain = true, fluent = true) public class City {

  private String id;
  private String text;
}
