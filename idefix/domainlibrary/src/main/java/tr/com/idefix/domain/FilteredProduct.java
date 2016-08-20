package tr.com.idefix.domain;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 10.28.15.
 */
@Getter @Setter @Accessors(fluent = true, chain = true) public class FilteredProduct {

  private List<DRItem> products;
  private int hitCount;
  private Map<Integer, List<FilterItem>> filters;
}
