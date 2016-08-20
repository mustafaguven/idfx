package tr.com.idefix.android.azure;

import java.net.URI;

public class ConnectionString {
  public ConnectionString() {
  }

  public static String createUsingSharedAccessKey(
      URI endPoint, String keyName, String accessSecret
  ) {
    if (endPoint == null) {
      throw new IllegalArgumentException("endPoint");
    } else if (Utils.isNullOrWhiteSpace(keyName)) {
      throw new IllegalArgumentException("keyName");
    } else if (Utils.isNullOrWhiteSpace(accessSecret)) {
      throw new IllegalArgumentException("accessSecret");
    } else {
      return String.format(
          "Endpoint=%s;SharedAccessKeyName=%s;SharedAccessKey=%s",
          new Object[] { endPoint.toString(), keyName, accessSecret });
    }
  }

  public static String createUsingSharedAccessKeyWithFullAccess(
      URI endPoint, String fullAccessSecret
  ) {
    if (Utils.isNullOrWhiteSpace(fullAccessSecret)) {
      throw new IllegalArgumentException("fullAccessSecret");
    } else {
      return createUsingSharedAccessKey(
          endPoint, "DefaultFullSharedAccessSignature", fullAccessSecret);
    }
  }

  public static String createUsingSharedAccessKeyWithListenAccess(
      URI endPoint, String listenAccessSecret
  ) {
    if (Utils.isNullOrWhiteSpace(listenAccessSecret)) {
      throw new IllegalArgumentException("listenAccessSecret");
    } else {
      return createUsingSharedAccessKey(
          endPoint, "DefaultListenSharedAccessSignature", listenAccessSecret);
    }
  }
}
