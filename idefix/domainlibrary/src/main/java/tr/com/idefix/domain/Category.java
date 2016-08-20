package tr.com.idefix.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 9.12.15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class Category {

  private int level;

  private int parentId;

  private String seo;

  private boolean bottom;

  private String parentPath;

  private String name;

  private int id;

  private int documentCount;
}
