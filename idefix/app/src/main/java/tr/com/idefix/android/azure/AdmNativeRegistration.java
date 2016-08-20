package tr.com.idefix.android.azure;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by mustafaguven on 07.01.2016.
 */
public class AdmNativeRegistration extends Registration {
  static final String ADM_HANDLE_NODE = "AdmRegistrationId";
  private static final String ADM_NATIVE_REGISTRATION_CUSTOM_NODE = "AdmRegistrationDescription";

  AdmNativeRegistration(String notificationHubPath) {
    super(notificationHubPath);
    this.mRegistrationType = RegistrationType.adm;
  }

  protected String getSpecificPayloadNodeName() {
    return "AdmRegistrationDescription";
  }

  protected void appendCustomPayload(Document doc, Element registrationDescription) {
    this.appendNodeWithValue(
        doc, registrationDescription, "AdmRegistrationId", this.getPNSHandle());
  }

  protected void loadCustomXmlData(Element payloadNode) {
    this.setPNSHandle(getNodeValue(payloadNode, "AdmRegistrationId"));
    this.setName("$Default");
  }
}
