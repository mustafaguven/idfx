package tr.com.idefix.android.azure;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by mustafaguven on 07.01.2016.
 */

public class BaiduTemplateRegistration extends TemplateRegistration {
  static final String BAIDU_TEMPLATE_REGISTRATION_CUSTOM_NODE =
      "BaiduTemplateRegistrationDescription";
  private static final String BAIDU_USER_ID = "BaiduUserId";
  private static final String BAIDU_CHANNEL_ID = "BaiduChannelId";
  private static final String BAIDU_HANDLE_NODE = "BaiduUserId-BaiduChannelId";
  protected String mUserId;
  protected String mChannelId;

  BaiduTemplateRegistration(String notificationHubPath) {
    super(notificationHubPath);
    this.mRegistrationType = RegistrationType.baidu;
  }

  public String getUserId() {
    return this.mUserId;
  }

  void setUserId(String pUserId) {
    this.mUserId = pUserId;
  }

  public String getChannelId() {
    return this.mChannelId;
  }

  void setChannelId(String pChannelId) {
    this.mChannelId = pChannelId;
  }

  void setPNSHandle(String pNSHandle) {
    if (!Utils.isNullOrWhiteSpace(pNSHandle)) {
      this.mPNSHandle = pNSHandle;
      String[] baiduInfo = pNSHandle.split("-");
      String userId = baiduInfo[0];
      if (Utils.isNullOrWhiteSpace(userId)) {
        throw new AssertionError("Baidu userId is inalid!");
      } else {
        this.setUserId(userId);
        String channelId = baiduInfo[1];
        if (Utils.isNullOrWhiteSpace(userId)) {
          throw new AssertionError("Baidu channelId is inalid!");
        } else {
          this.setChannelId(channelId);
        }
      }
    }
  }

  protected String getSpecificPayloadNodeName() {
    return "BaiduTemplateRegistrationDescription";
  }

  protected void appendCustomPayload(Document doc, Element templateRegistrationDescription) {
    this.appendNodeWithValue(doc, templateRegistrationDescription, "BaiduUserId", this.getUserId());
    this.appendNodeWithValue(
        doc, templateRegistrationDescription, "BaiduChannelId", this.getChannelId());
    super.appendCustomPayload(doc, templateRegistrationDescription);
  }

  protected void loadCustomXmlData(Element payloadNode) {
    this.setPNSHandle(getNodeValue(payloadNode, "BaiduUserId-BaiduChannelId"));
    super.loadCustomXmlData(payloadNode);
  }
}
