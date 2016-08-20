package tr.com.idefix.domain;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 14/09/15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class CategoryTree {

  private Category category;
  private ArrayList<Category> categoryTree = new ArrayList<>();
}
