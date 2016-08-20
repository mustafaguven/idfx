package tr.com.idefix.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 10.4.15.
 */
@Getter @Setter @Accessors(fluent = true, chain = true)

public class CustomerInfo {
  private String username;
  private String lastName;
  private String firstName;
  private String email;
  private String gender;
  private int dateofbirthday;
  private int dateofbirthmonth;
  private int dateofbirthyear;
  private int countryId;
  private String cityId;
  private String phone;
  private String gsm;
  private boolean newsletter;
}
