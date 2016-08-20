package tr.com.idefix.android.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 01/09/15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class LoggedinUserModel
    extends BaseModel {

  String email;
  String password;
  Boolean rememberMe;
  String username;
}
