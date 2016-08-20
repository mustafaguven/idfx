package tr.com.idefix.android.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 9.5.15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class MainCategoryModel
    extends BaseModel {
  private Integer id;
  private String name;
  private String parentName;
  private boolean bottom;
  private int level;
  private String parentPath;
}
