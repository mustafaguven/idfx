package tr.com.idefix.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 10.11.15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class Wish {
  List<DRItem> items;
}
