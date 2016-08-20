package tr.com.idefix.android.azure;

/**
 * Created by mustafaguven on 07.01.2016.
 */

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class Registration {
  static final String DEFAULT_REGISTRATION_NAME = "$Default";
  static final String REGISTRATIONID_JSON_PROPERTY = "registrationid";
  static final String REGISTRATION_NAME_JSON_PROPERTY = "registrationName";
  public Registration.RegistrationType mRegistrationType;
  protected String mRegistrationId;
  protected String mNotificationHubPath;
  protected String mExpirationTime;
  protected String mPNSHandle;
  protected String mName;
  protected List<String> mTags = new ArrayList();
  protected String mURI;
  protected String mUpdated;
  protected String mETag;

  Registration(String notificationHubPath) {
    this.mNotificationHubPath = notificationHubPath;
  }

  protected static String getNodeValue(Element element, String node) {
    NodeList nodes = element.getElementsByTagName(node);
    return nodes.getLength() > 0 ? nodes.item(0).getTextContent() : null;
  }

  private static Date UTCDateStringToDate(String dateString) throws ParseException {
    String s = dateString.replace("Z", "+00:00");

    try {
      s = s.substring(0, 26) + s.substring(27);
    } catch (IndexOutOfBoundsException var4) {
      throw new ParseException("The \'updated\' value has an invalid format", 26);
    }

    SimpleDateFormat dateFormat =
        new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss\'.\'SSSZ", Locale.getDefault());
    dateFormat.setTimeZone(TimeZone.getDefault());
    Date date = dateFormat.parse(s);
    return date;
  }

  String toXml() throws Exception {
    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    builder.setEntityResolver(new EntityResolver() {
      public InputSource resolveEntity(String publicId, String systemId)
          throws SAXException, IOException {
        return null;
      }
    });
    Document doc = builder.newDocument();
    Element entry = doc.createElement("entry");
    entry.setAttribute("xmlns", "http://www.w3.org/2005/Atom");
    doc.appendChild(entry);
    this.appendNodeWithValue(doc, entry, "id", this.getURI());
    this.appendNodeWithValue(doc, entry, "updated", this.getUpdatedString());
    this.appendContentNode(doc, entry);
    return Utils.getXmlString(doc.getDocumentElement());
  }

  private void appendContentNode(Document doc, Element entry) {
    Element content = doc.createElement("content");
    content.setAttribute("type", "application/xml");
    entry.appendChild(content);
    Element registrationDescription = doc.createElement(this.getSpecificPayloadNodeName());
    registrationDescription.setAttribute("xmlns:i", "http://www.w3.org/2001/XMLSchema-instance");
    registrationDescription.setAttribute(
        "xmlns", "http://schemas.microsoft.com/netservices/2010/10/servicebus/connect");
    content.appendChild(registrationDescription);
    this.appendNodeWithValue(doc, registrationDescription, "ETag", this.getETag());
    this.appendNodeWithValue(
        doc, registrationDescription, "ExpirationTime", this.getExpirationTimeString());
    this.appendNodeWithValue(
        doc, registrationDescription, "RegistrationId", this.getRegistrationId());
    this.appendTagsNode(doc, registrationDescription);
    this.appendCustomPayload(doc, registrationDescription);
  }

  protected abstract void appendCustomPayload(Document var1, Element var2);

  protected void appendTagsNode(Document doc, Element registrationDescription) {
    List tagList = this.getTags();
    if (tagList != null && tagList.size() > 0) {
      String tagsNodeValue = (String) tagList.get(0);

      for (int tags = 1; tags < tagList.size(); ++tags) {
        tagsNodeValue = tagsNodeValue + "," + (String) tagList.get(tags);
      }

      Element var6 = doc.createElement("Tags");
      var6.appendChild(doc.createTextNode(tagsNodeValue));
      registrationDescription.appendChild(var6);
    }
  }

  protected void appendNodeWithValue(
      Document doc, Element targetElement, String nodeName, String value
  ) {
    if (!Utils.isNullOrWhiteSpace(value)) {
      Element newElement = doc.createElement(nodeName);
      newElement.appendChild(doc.createTextNode(value));
      targetElement.appendChild(newElement);
    }
  }

  void loadXml(String xml, String notificationHubPath) throws Exception {
    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document doc = builder.parse(new InputSource(new StringReader(xml)));
    doc.getDocumentElement().normalize();
    Element root = doc.getDocumentElement();
    this.mNotificationHubPath = notificationHubPath;
    this.mUpdated = getNodeValue(root, "updated");
    NodeList payloadNodes = doc.getElementsByTagName(this.getSpecificPayloadNodeName());
    if (payloadNodes.getLength() > 0) {
      Element payloadNode = (Element) payloadNodes.item(0);
      this.mETag = getNodeValue(payloadNode, "ETag");
      this.mExpirationTime = getNodeValue(payloadNode, "ExpirationTime");
      this.mRegistrationId = getNodeValue(payloadNode, "RegistrationId");
      this.mURI = notificationHubPath + "/Registrations/" + this.mRegistrationId;
      String tags = getNodeValue(payloadNode, "Tags");
      if (!Utils.isNullOrWhiteSpace(tags)) {
        tags = tags.trim();
        String[] tagList = tags.split(",");
        String[] var13 = tagList;
        int var12 = tagList.length;

        for (int var11 = 0; var11 < var12; ++var11) {
          String tag = var13[var11];
          this.mTags.add(tag);
        }
      }

      this.loadCustomXmlData(payloadNode);
    }
  }

  protected abstract void loadCustomXmlData(Element var1);

  protected abstract String getSpecificPayloadNodeName();

  public Registration.RegistrationType getRegistrationType() {
    return this.mRegistrationType;
  }

  void setRegistrationType(Registration.RegistrationType type) {
    this.mRegistrationType = type;
  }

  public String getRegistrationId() {
    return this.mRegistrationId;
  }

  void setRegistrationId(String registrationId) {
    this.mRegistrationId = registrationId;
  }

  public String getNotificationHubPath() {
    return this.mNotificationHubPath;
  }

  void setNotificationHubPath(String notificationHubPath) {
    this.mNotificationHubPath = notificationHubPath;
  }

  String getName() {
    return this.mName;
  }

  void setName(String name) {
    this.mName = name;
  }

  public List<String> getTags() {
    return new ArrayList(this.mTags);
  }

  public String getURI() {
    return this.getNotificationHubPath() + "/Registrations/" + this.mRegistrationId;
  }

  String getETag() {
    return this.mETag;
  }

  void setETag(String eTag) {
    this.mETag = eTag;
  }

  Date getUpdated() throws ParseException {
    return UTCDateStringToDate(this.mUpdated);
  }

  String getUpdatedString() {
    return this.mUpdated;
  }

  void setUpdatedString(String updatedDateString) {
    this.mUpdated = updatedDateString;
  }

  public String getPNSHandle() {
    return this.mPNSHandle;
  }

  void setPNSHandle(String pNSHandle) {
    this.mPNSHandle = pNSHandle;
  }

  public Date getExpirationTime() throws ParseException {
    return UTCDateStringToDate(this.mExpirationTime);
  }

  String getExpirationTimeString() {
    return this.mExpirationTime;
  }

  void setExpirationTimeString(String expirationTimeString) {
    this.mExpirationTime = expirationTimeString;
  }

  void addTags(String[] tags) {
    if (tags != null) {
      String[] var5 = tags;
      int var4 = tags.length;

      for (int var3 = 0; var3 < var4; ++var3) {
        String tag = var5[var3];
        if (!Utils.isNullOrWhiteSpace(tag)) {
          this.mTags.add(tag);
        }
      }
    }
  }

  JSONObject getRegistrationInformation() throws JSONException {
    JSONObject regInfo = new JSONObject();
    regInfo.put("registrationid", this.getRegistrationId());
    regInfo.put("registrationName", this.getName());
    return regInfo;
  }

  public static enum RegistrationType {
    unknown,
    gcm,
    adm,
    baidu;

    private RegistrationType() {
    }
  }
}
