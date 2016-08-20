package tr.com.idefix.android.azure;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by mustafaguven on 07.01.2016.
 */
public class AdmTemplateRegistration extends TemplateRegistration {
  static final String ADM_TEMPLATE_REGISTRATION_CUSTOM_NODE = "AdmTemplateRegistrationDescription";
  private static final String ADM_HANDLE_NODE = "AdmRegistrationId";

  AdmTemplateRegistration(String notificationHubPath) {
    super(notificationHubPath);
    this.mRegistrationType = RegistrationType.adm;
  }

  protected String getSpecificPayloadNodeName() {
    return "AdmTemplateRegistrationDescription";
  }

  protected void appendCustomPayload(Document doc, Element templateRegistrationDescription) {
    this.appendNodeWithValue(
        doc, templateRegistrationDescription, "AdmRegistrationId", this.getPNSHandle());
    super.appendCustomPayload(doc, templateRegistrationDescription);
  }

  protected void loadCustomXmlData(Element payloadNode) {
    this.setPNSHandle(getNodeValue(payloadNode, "AdmRegistrationId"));
    super.loadCustomXmlData(payloadNode);
  }
}
