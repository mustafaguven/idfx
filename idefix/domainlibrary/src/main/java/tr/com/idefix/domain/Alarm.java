package tr.com.idefix.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain = true, fluent = true) public class Alarm {
  List<DRItem> items;
}
