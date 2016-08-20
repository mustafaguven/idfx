package tr.com.idefix.android.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 16/09/15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class CategoryModel {

  String seo;
  private String name;
  private int level;
  private int id;
  private boolean bottom;
  private String parentPath;
}
