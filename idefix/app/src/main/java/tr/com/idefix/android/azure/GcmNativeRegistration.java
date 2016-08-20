package tr.com.idefix.android.azure;

/**
 * Created by mustafaguven on 07.01.2016.
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GcmNativeRegistration extends Registration {
  static final String GCM_HANDLE_NODE = "GcmRegistrationId";
  private static final String GCM_NATIVE_REGISTRATION_CUSTOM_NODE = "GcmRegistrationDescription";

  GcmNativeRegistration(String notificationHubPath) {
    super(notificationHubPath);
    this.mRegistrationType = RegistrationType.gcm;
  }

  protected String getSpecificPayloadNodeName() {
    return "GcmRegistrationDescription";
  }

  protected void appendCustomPayload(Document doc, Element registrationDescription) {
    this.appendNodeWithValue(
        doc, registrationDescription, "GcmRegistrationId", this.getPNSHandle());
  }

  protected void loadCustomXmlData(Element payloadNode) {
    this.setPNSHandle(getNodeValue(payloadNode, "GcmRegistrationId"));
    this.setName("$Default");
  }
}
