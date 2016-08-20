package tr.com.idefix.android.azure;

/**
 * Created by mustafaguven on 07.01.2016.
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GcmTemplateRegistration extends TemplateRegistration {
  static final String GCM_TEMPLATE_REGISTRATION_CUSTOM_NODE = "GcmTemplateRegistrationDescription";
  private static final String GCM_HANDLE_NODE = "GcmRegistrationId";

  GcmTemplateRegistration(String notificationHubPath) {
    super(notificationHubPath);
    this.mRegistrationType = RegistrationType.gcm;
  }

  protected String getSpecificPayloadNodeName() {
    return "GcmTemplateRegistrationDescription";
  }

  protected void appendCustomPayload(Document doc, Element templateRegistrationDescription) {
    this.appendNodeWithValue(
        doc, templateRegistrationDescription, "GcmRegistrationId", this.getPNSHandle());
    super.appendCustomPayload(doc, templateRegistrationDescription);
  }

  protected void loadCustomXmlData(Element payloadNode) {
    this.setPNSHandle(getNodeValue(payloadNode, "GcmRegistrationId"));
    super.loadCustomXmlData(payloadNode);
  }
}
