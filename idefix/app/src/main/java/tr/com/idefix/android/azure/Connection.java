package tr.com.idefix.android.azure;

import android.net.http.AndroidHttpClient;
import android.os.Build.VERSION;
import android.util.Base64;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.EntityEnclosingRequestWrapper;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

/**
 * Created by mustafaguven on 07.01.2016.
 */
class Connection {
  private static final String SHARED_ACCESS_KEY_NAME = "SharedAccessKeyName";
  private static final String SHARED_ACCESS_KEY = "SharedAccessKey";
  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String UTC_TIME_ZONE = "UTC";
  private static final String UTF8_ENCODING = "UTF-8";
  private static final String ENDPOINT_KEY = "Endpoint";
  private static final int EXPIRE_MINUTES = 5;
  private static final String SDK_VERSION = "2014-09";
  private static final String API_VERSION_KEY = "api-version";
  private static final String API_VERSION = "2014-09";
  private Map<String, String> mConnectionData;

  public Connection(String connectionString) {
    this.mConnectionData = ConnectionStringParser.parse(connectionString);
  }

  public String executeRequest(
      String resource, String content, String contentType, String method, Header... extraHeaders
  ) throws Exception {
    return this.executeRequest(resource, content, contentType, method, (String) null, extraHeaders);
  }

  public String executeRequest(
      String resource, String content, String contentType, String method, String targetHeaderName,
      Header... extraHeaders
  ) throws Exception {
    URI endpointURI = URI.create((String) this.mConnectionData.get("Endpoint"));
    String scheme = endpointURI.getScheme();
    String url = "https" + endpointURI.toString().substring(scheme.length());
    if (!url.endsWith("/")) {
      url = url + "/";
    }

    url = url + resource;
    url = this.AddApiVersionToUrl(url);
    BasicHttpEntityEnclosingRequest request = new BasicHttpEntityEnclosingRequest(method, url);
    if (!Utils.isNullOrWhiteSpace(content)) {
      request.setEntity(new StringEntity(content, "UTF-8"));
    }

    request.addHeader("Content-Type", contentType);
    EntityEnclosingRequestWrapper wrapper = new EntityEnclosingRequestWrapper(request);
    if (extraHeaders != null) {
      Header[] var15 = extraHeaders;
      int var14 = extraHeaders.length;

      for (int var13 = 0; var13 < var14; ++var13) {
        Header header = var15[var13];
        wrapper.addHeader(header);
      }
    }

    return this.executeRequest(wrapper, targetHeaderName);
  }

  private String AddApiVersionToUrl(String url) {
    URI uri = URI.create(url);
    if (uri.getQuery() == null) {
      url = url + "?";
    } else {
      url = url + "&";
    }

    url = url + "api-version" + "=" + "2014-09";
    return url;
  }

  private String executeRequest(HttpUriRequest request, String targetHeaderName) throws Exception {
    this.addAuthorizationHeader(request);
    String headerValue = null;
    AndroidHttpClient client = null;
    boolean noHeaderButExpected = false;

    int status;
    String content;
    try {
      client = AndroidHttpClient.newInstance(this.getUserAgent());
      HttpResponse response = client.execute(request);
      status = response.getStatusLine().getStatusCode();
      content = this.getResponseContent(response);
      if (targetHeaderName != null) {
        if (!response.containsHeader(targetHeaderName)) {
          noHeaderButExpected = true;
        } else {
          headerValue = response.getFirstHeader(targetHeaderName).getValue();
        }
      }
    } finally {
      if (client != null) {
        client.close();
      }
    }

    if (status >= 200 && status < 300) {
      if (noHeaderButExpected) {
        throw new NotificationHubException(
            "The \'" + targetHeaderName + "\' header does not present in collection", status);
      } else {
        return targetHeaderName == null ? content : headerValue;
      }
    } else if (status == 404) {
      throw new NotificationHubResourceNotFoundException();
    } else if (status == 401) {
      throw new NotificationHubUnauthorizedException();
    } else if (status == 410) {
      throw new RegistrationGoneException();
    } else {
      throw new NotificationHubException(content, status);
    }
  }

  private String getResponseContent(HttpResponse response) throws IOException {
    HttpEntity entity = response.getEntity();
    if (entity == null) {
      return null;
    } else {
      InputStream instream = entity.getContent();
      BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
      StringBuilder sb = new StringBuilder();

      for (String content = reader.readLine(); content != null; content = reader.readLine()) {
        sb.append(content);
        sb.append('\n');
      }

      return sb.toString();
    }
  }

  private void addAuthorizationHeader(HttpUriRequest request) throws InvalidKeyException {
    String token = this.generateAuthToken(request.getURI().toString());
    request.addHeader("Authorization", token);
  }

  private String generateAuthToken(String url) throws InvalidKeyException {
    String keyName = (String) this.mConnectionData.get("SharedAccessKeyName");
    if (Utils.isNullOrWhiteSpace(keyName)) {
      throw new AssertionError("SharedAccessKeyName");
    } else {
      String key = (String) this.mConnectionData.get("SharedAccessKey");
      if (Utils.isNullOrWhiteSpace(key)) {
        throw new AssertionError("SharedAccessKey");
      } else {
        try {
          url = URLEncoder.encode(url, "UTF-8").toLowerCase(Locale.ENGLISH);
        } catch (UnsupportedEncodingException var16) {
          ;
        }

        Calendar expireDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        expireDate.add(Calendar.MINUTE, 5);
        long expires = expireDate.getTimeInMillis() / 1000L;
        String toSign = url + '\n' + expires;
        byte[] bytesToSign = toSign.getBytes();
        Mac mac = null;

        try {
          mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException var15) {
          ;
        }

        SecretKeySpec secret = new SecretKeySpec(key.getBytes(), mac.getAlgorithm());
        mac.init(secret);
        byte[] signedHash = mac.doFinal(bytesToSign);
        String base64Signature = Base64.encodeToString(signedHash, 0);
        base64Signature = base64Signature.trim();

        try {
          base64Signature = URLEncoder.encode(base64Signature, "UTF-8");
        } catch (UnsupportedEncodingException var14) {
          ;
        }

        String token = "SharedAccessSignature sr="
            + url
            + "&sig="
            + base64Signature
            + "&se="
            + expires
            + "&skn="
            + keyName;
        return token;
      }
    }
  }

  private String getUserAgent() {
    String userAgent = String.format("NOTIFICATIONHUBS/%s (api-origin=%s; os=%s; os_version=%s;)",
        new Object[] {
            "2014-09", PnsSpecificRegistrationFactory.getInstance().getAPIOrigin(), "Android",
            VERSION.RELEASE
        });
    return userAgent;
  }
}
