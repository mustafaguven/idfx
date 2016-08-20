package tr.com.idefix.android.azure;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Created by mustafaguven on 07.01.2016.
 */

public class NotificationHub {
  private static final String STORAGE_PREFIX = "__NH_";
  private static final String REGISTRATION_NAME_STORAGE_KEY = "REG_NAME_";
  private static final String XML_CONTENT_TYPE = "application/atom+xml";
  private static final String STORAGE_VERSION_KEY = "STORAGE_VERSION";
  private static final String STORAGE_VERSION = "1.0.0";
  private static final String PNS_HANDLE_KEY = "PNS_HANDLE";
  private static final String NEW_REGISTRATION_LOCATION_HEADER = "Location";
  private String mNotificationHubPath;
  private String mConnectionString;
  private SharedPreferences mSharedPreferences;
  private boolean mIsRefreshNeeded = false;

  public NotificationHub(String notificationHubPath, String connectionString, Context context) {
    this.setConnectionString(connectionString);
    this.setNotificationHubPath(notificationHubPath);
    if (context == null) {
      throw new IllegalArgumentException("context");
    } else {
      this.mSharedPreferences =
          PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
      this.verifyStorageVersion();
    }
  }

  public Registration register(String pnsHandle, String... tags) throws Exception {
    if (Utils.isNullOrWhiteSpace(pnsHandle)) {
      throw new IllegalArgumentException("pnsHandle");
    } else {
      Registration registration = PnsSpecificRegistrationFactory.getInstance()
          .createNativeRegistration(this.mNotificationHubPath);
      registration.setPNSHandle(pnsHandle);
      registration.setName("$Default");
      registration.addTags(tags);
      return this.registerInternal(registration);
    }
  }

  public Registration registerBaidu(String userId, String channelId, String... tags)
      throws Exception {
    if (Utils.isNullOrWhiteSpace(userId)) {
      throw new IllegalArgumentException("userId");
    } else if (Utils.isNullOrWhiteSpace(channelId)) {
      throw new IllegalArgumentException("channelId");
    } else {
      PnsSpecificRegistrationFactory.getInstance()
          .setRegistrationType(Registration.RegistrationType.baidu);
      return this.register(userId + "-" + channelId, tags);
    }
  }

  public TemplateRegistration registerTemplate(
      String pnsHandle, String templateName, String template, String... tags
  ) throws Exception {
    if (Utils.isNullOrWhiteSpace(pnsHandle)) {
      throw new IllegalArgumentException("pnsHandle");
    } else if (Utils.isNullOrWhiteSpace(templateName)) {
      throw new IllegalArgumentException("templateName");
    } else if (Utils.isNullOrWhiteSpace(template)) {
      throw new IllegalArgumentException("template");
    } else {
      TemplateRegistration registration = PnsSpecificRegistrationFactory.getInstance()
          .createTemplateRegistration(this.mNotificationHubPath);
      registration.setPNSHandle(pnsHandle);
      registration.setName(templateName);
      registration.setBodyTemplate(template);
      registration.addTags(tags);
      return (TemplateRegistration) this.registerInternal(registration);
    }
  }

  public TemplateRegistration registerBaiduTemplate(
      String userId, String channelId, String templateName, String template, String... tags
  ) throws Exception {
    if (Utils.isNullOrWhiteSpace(userId)) {
      throw new IllegalArgumentException("userId");
    } else if (Utils.isNullOrWhiteSpace(channelId)) {
      throw new IllegalArgumentException("channelId");
    } else if (Utils.isNullOrWhiteSpace(templateName)) {
      throw new IllegalArgumentException("templateName");
    } else if (Utils.isNullOrWhiteSpace(template)) {
      throw new IllegalArgumentException("template");
    } else {
      PnsSpecificRegistrationFactory.getInstance()
          .setRegistrationType(Registration.RegistrationType.baidu);
      return this.registerTemplate(userId + "-" + channelId, templateName, template, tags);
    }
  }

  public void unregister() throws Exception {
    this.unregisterInternal("$Default");
  }

  public void unregisterTemplate(String templateName) throws Exception {
    if (Utils.isNullOrWhiteSpace(templateName)) {
      throw new IllegalArgumentException("templateName");
    } else {
      this.unregisterInternal(templateName);
    }
  }

  public void unregisterAll(String pnsHandle) throws Exception {
    this.refreshRegistrationInformation(pnsHandle);
    Set keys = this.mSharedPreferences.getAll().keySet();
    Iterator var4 = keys.iterator();

    while (var4.hasNext()) {
      String key = (String) var4.next();
      if (key.startsWith("__NH_REG_NAME_")) {
        String registrationName = key.substring("__NH_REG_NAME_".length());
        String registrationId = this.mSharedPreferences.getString(key, "");
        this.deleteRegistrationInternal(registrationName, registrationId);
      }
    }
  }

  private void refreshRegistrationInformation(String pnsHandle) throws Exception {
    if (Utils.isNullOrWhiteSpace(pnsHandle)) {
      throw new IllegalArgumentException("pnsHandle");
    } else {
      SharedPreferences.Editor editor = this.mSharedPreferences.edit();
      Set keys = this.mSharedPreferences.getAll().keySet();
      Iterator filter = keys.iterator();

      while (filter.hasNext()) {
        String conn = (String) filter.next();
        if (conn.startsWith("__NH_REG_NAME_")) {
          editor.remove(conn);
        }
      }

      editor.commit();
      Connection var17 = new Connection(this.mConnectionString);
      String var18 = PnsSpecificRegistrationFactory.getInstance().getPNSHandleFieldName()
          + " eq \'"
          + pnsHandle
          + "\'";
      String resource =
          this.mNotificationHubPath + "/Registrations/?$filter=" + URLEncoder.encode(var18,
              "UTF-8");
      Object content = null;
      String response =
          var17.executeRequest(resource, (String) content, "application/atom+xml", "GET",
              new Header[0]);
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      builder.setEntityResolver(new EntityResolver() {
        public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException, IOException {
          return null;
        }
      });
      Document doc = builder.parse(new InputSource(new StringReader(response)));
      doc.getDocumentElement().normalize();
      Element root = doc.getDocumentElement();
      NodeList entries = root.getElementsByTagName("entry");

      for (int i = 0; i < entries.getLength(); ++i) {
        Element entry = (Element) entries.item(i);
        String xml = Utils.getXmlString(entry);
        Object registration;
        if (PnsSpecificRegistrationFactory.getInstance().isTemplateRegistration(xml)) {
          registration = PnsSpecificRegistrationFactory.getInstance()
              .createTemplateRegistration(this.mNotificationHubPath);
        } else {
          registration = PnsSpecificRegistrationFactory.getInstance()
              .createNativeRegistration(this.mNotificationHubPath);
        }

        ((Registration) registration).loadXml(xml, this.mNotificationHubPath);
        this.storeRegistrationId(
            ((Registration) registration).getName(),
            ((Registration) registration).getRegistrationId(),
            ((Registration) registration).getPNSHandle());
      }

      this.mIsRefreshNeeded = false;
    }
  }

  public String getConnectionString() {
    return this.mConnectionString;
  }

  public void setConnectionString(String connectionString) {
    if (Utils.isNullOrWhiteSpace(connectionString)) {
      throw new IllegalArgumentException("connectionString");
    } else {
      try {
        ConnectionStringParser.parse(connectionString);
      } catch (Exception var3) {
        throw new IllegalArgumentException("connectionString", var3);
      }

      this.mConnectionString = connectionString;
    }
  }

  public String getNotificationHubPath() {
    return this.mNotificationHubPath;
  }

  public void setNotificationHubPath(String notificationHubPath) {
    if (Utils.isNullOrWhiteSpace(notificationHubPath)) {
      throw new IllegalArgumentException("notificationHubPath");
    } else {
      this.mNotificationHubPath = notificationHubPath;
    }
  }

  private Registration registerInternal(Registration registration) throws Exception {
    String registrationId;
    if (this.mIsRefreshNeeded) {
      registrationId = this.mSharedPreferences.getString("__NH_PNS_HANDLE", "");
      if (Utils.isNullOrWhiteSpace(registrationId)) {
        registrationId = registration.getPNSHandle();
      }

      this.refreshRegistrationInformation(registrationId);
    }

    registrationId = this.retrieveRegistrationId(registration.getName());
    if (Utils.isNullOrWhiteSpace(registrationId)) {
      registrationId = this.createRegistrationId();
    }

    registration.setRegistrationId(registrationId);

    try {
      return this.upsertRegistrationInternal(registration);
    } catch (RegistrationGoneException var4) {
      registrationId = this.createRegistrationId();
      registration.setRegistrationId(registrationId);
      return this.upsertRegistrationInternal(registration);
    }
  }

  private void unregisterInternal(String registrationName) throws Exception {
    String registrationId = this.retrieveRegistrationId(registrationName);
    if (!Utils.isNullOrWhiteSpace(registrationId)) {
      this.deleteRegistrationInternal(registrationName, registrationId);
    }
  }

  private Registration upsertRegistrationInternal(Registration registration) throws Exception {
    Connection conn = new Connection(this.mConnectionString);
    String resource = registration.getURI();
    String content = registration.toXml();
    String response =
        conn.executeRequest(resource, content, "application/atom+xml", "PUT", new Header[0]);
    boolean isTemplateRegistration =
        PnsSpecificRegistrationFactory.getInstance().isTemplateRegistration(response);
    Object result;
    if (isTemplateRegistration) {
      result = PnsSpecificRegistrationFactory.getInstance()
          .createTemplateRegistration(this.mNotificationHubPath);
    } else {
      result = PnsSpecificRegistrationFactory.getInstance()
          .createNativeRegistration(this.mNotificationHubPath);
    }

    ((Registration) result).loadXml(response, this.mNotificationHubPath);
    this.storeRegistrationId(
        ((Registration) result).getName(), ((Registration) result).getRegistrationId(),
        registration.getPNSHandle());
    return (Registration) result;
  }

  private String createRegistrationId() throws Exception {
    Connection conn = new Connection(this.mConnectionString);
    String resource = this.mNotificationHubPath + "/registrationids/";
    String response =
        conn.executeRequest(resource, (String) null, "application/atom+xml", "POST", "Location",
            new Header[0]);
    URI regIdUri = new URI(response);
    String[] pathFragments = regIdUri.getPath().split("/");
    String result = pathFragments[pathFragments.length - 1];
    return result;
  }

  private void deleteRegistrationInternal(String registrationName, String registrationId)
      throws Exception {
    Connection conn = new Connection(this.mConnectionString);
    String resource = this.mNotificationHubPath + "/Registrations/" + registrationId;

    try {
      conn.executeRequest(
          resource, (String) null, "application/atom+xml", "DELETE",
          new Header[] { new BasicHeader("If-Match", "*") });
    } finally {
      this.removeRegistrationId(registrationName);
    }
  }

  private String retrieveRegistrationId(String registrationName) throws Exception {
    return this.mSharedPreferences.getString("__NH_REG_NAME_" + registrationName, (String) null);
  }

  private void storeRegistrationId(String registrationName, String registrationId, String pNSHandle)
      throws Exception {
    SharedPreferences.Editor editor = this.mSharedPreferences.edit();
    editor.putString("__NH_REG_NAME_" + registrationName, registrationId);
    editor.putString("__NH_PNS_HANDLE", pNSHandle);
    editor.putString("__NH_STORAGE_VERSION", "1.0.0");
    editor.commit();
  }

  private void removeRegistrationId(String registrationName) throws Exception {
    SharedPreferences.Editor editor = this.mSharedPreferences.edit();
    editor.remove("__NH_REG_NAME_" + registrationName);
    editor.commit();
  }

  private void verifyStorageVersion() {
    String currentStorageVersion = this.mSharedPreferences.getString("__NH_STORAGE_VERSION", "");
    SharedPreferences.Editor editor = this.mSharedPreferences.edit();
    if (!currentStorageVersion.equals("1.0.0")) {
      Set keys = this.mSharedPreferences.getAll().keySet();
      Iterator var5 = keys.iterator();

      while (var5.hasNext()) {
        String key = (String) var5.next();
        if (key.startsWith("__NH_")) {
          editor.remove(key);
        }
      }
    }

    editor.commit();
    this.mIsRefreshNeeded = true;
  }
}
