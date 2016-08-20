package tr.com.idefix.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by mustafaguven on 25.10.2015.
 */

@Getter @Setter public class SearchFilterItem {

  String parent;
  String name;
  String id;

  public SearchFilterItem(String parent, String name) {
    this(parent, name, "-1");
  }

  public SearchFilterItem(String parent, String name, String id) {
    this.parent = parent;
    this.name = name;
    this.id = id;
  }
}
