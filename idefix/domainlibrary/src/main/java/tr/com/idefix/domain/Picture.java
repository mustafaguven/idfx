package tr.com.idefix.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 14/10/15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class Picture {

  String imageUrl;
  String fullSizeImageUrl;
}
