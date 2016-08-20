package tr.com.idefix.domain;

import com.squareup.otto.Bus;
import javax.inject.Singleton;

/**
 * Created on 11.3.15.
 */
@Singleton public final class BusProvider {

  private final Bus BUS;

  public BusProvider() {
    BUS = new Bus();
  }

  public Bus getBus() {
    return BUS;
  }
}
