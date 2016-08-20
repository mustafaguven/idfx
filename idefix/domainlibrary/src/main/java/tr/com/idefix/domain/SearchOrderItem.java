package tr.com.idefix.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by mustafaguven on 25.10.2015.
 */

@Getter @Setter public class SearchOrderItem {

  String name;
  String sortField;
  String sortOrder;

  public SearchOrderItem(String name, String sortField, String sortOrder) {
    this.name = name;
    this.sortField = sortField;
    this.sortOrder = sortOrder;
  }
}
