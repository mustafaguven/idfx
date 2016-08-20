package tr.com.idefix.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain = true, fluent = true) public class LoggedInUser {

  private String email;
  private String password;
  private Boolean rememberMe;
  private String username;
  private String sessionObject;
}
