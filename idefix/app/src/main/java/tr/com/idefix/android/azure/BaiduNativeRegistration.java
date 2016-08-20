package tr.com.idefix.android.azure;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by mustafaguven on 07.01.2016.
 */
public class BaiduNativeRegistration extends Registration {
  static final String BAIDU_HANDLE_NODE = "BaiduUserId-BaiduChannelId";
  private static final String BAIDU_NATIVE_REGISTRATION_CUSTOM_NODE =
      "BaiduRegistrationDescription";
  private static final String BAIDU_USER_ID = "BaiduUserId";
  private static final String BAIDU_CHANNEL_ID = "BaiduChannelId";
  protected String mUserId;
  protected String mChannelId;

  BaiduNativeRegistration(String notificationHubPath) {
    super(notificationHubPath);
    this.mRegistrationType = RegistrationType.baidu;
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

  protected String getSpecificPayloadNodeName() {
    return "BaiduRegistrationDescription";
  }

  protected void appendCustomPayload(Document doc, Element registrationDescription) {
    this.appendNodeWithValue(doc, registrationDescription, "BaiduUserId", this.getUserId());
    this.appendNodeWithValue(doc, registrationDescription, "BaiduChannelId", this.getChannelId());
  }

  protected void loadCustomXmlData(Element payloadNode) {
    this.setName("$Default");
  }
}
