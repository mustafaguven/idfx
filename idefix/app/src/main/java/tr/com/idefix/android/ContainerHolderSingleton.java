package tr.com.idefix.android;

import com.google.android.gms.tagmanager.ContainerHolder;

/**
 * Created by utkan on 26/11/15.
 */

/**
 * Singleton to hold the GTM Container (since it should be only created once
 * per run of the app).
 */
public class ContainerHolderSingleton {
  private static ContainerHolder containerHolder;

  /**
   * Utility class; don't instantiate.
   */
  private ContainerHolderSingleton() {
  }

  public static ContainerHolder getContainerHolder() {
    return containerHolder;
  }

  public static void setContainerHolder(ContainerHolder c) {
    containerHolder = c;
  }
}