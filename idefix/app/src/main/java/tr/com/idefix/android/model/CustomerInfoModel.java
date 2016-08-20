package tr.com.idefix.android.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 06/10/15.
 */
@Getter @Setter @Accessors(fluent = true, chain = true) public class CustomerInfoModel {
  private String username;
  private String lastName;
  private String firstName;
  private String fullName;
}
