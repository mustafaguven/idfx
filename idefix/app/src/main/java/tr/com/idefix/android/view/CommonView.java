package tr.com.idefix.android.view;

import java.util.List;
import tr.com.idefix.domain.Country;

/**
 * Created by utkan on 01/09/15.
 */
public interface CommonView extends IView {
  void setCountries(List<Country> countries);
}
