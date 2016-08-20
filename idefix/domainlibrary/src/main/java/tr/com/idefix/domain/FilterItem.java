package tr.com.idefix.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 10.28.15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class FilterItem
    implements Serializable {

  private boolean selected;
  private String name;
  private int id;
  private int filterType;
}
