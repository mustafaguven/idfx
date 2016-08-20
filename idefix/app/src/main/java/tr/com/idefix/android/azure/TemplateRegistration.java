package tr.com.idefix.android.azure;

/**
 * Created by mustafaguven on 07.01.2016.
 */

import org.w3c.dom.CDATASection;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public abstract class TemplateRegistration extends Registration {
  private String mBodyTemplate;

  TemplateRegistration(String notificationHubPath) {
    super(notificationHubPath);
  }

  protected void appendCustomPayload(Document doc, Element templateRegistrationDescription) {
    this.appendBodyTemplateNode(doc, templateRegistrationDescription);
    this.appendNodeWithValue(doc, templateRegistrationDescription, "TemplateName", this.getName());
  }

  private void appendBodyTemplateNode(Document doc, Element templateRegistrationDescription) {
    if (!Utils.isNullOrWhiteSpace(this.getBodyTemplate())) {
      Element bodyTemplate = doc.createElement("BodyTemplate");
      templateRegistrationDescription.appendChild(bodyTemplate);
      CDATASection bodyTemplateCDATA = doc.createCDATASection(this.getBodyTemplate());
      bodyTemplate.appendChild(bodyTemplateCDATA);
    }
  }

  protected void loadCustomXmlData(Element payloadNode) {
    NodeList bodyTemplateElements = payloadNode.getElementsByTagName("BodyTemplate");
    if (bodyTemplateElements.getLength() > 0) {
      NodeList bodyNodes = bodyTemplateElements.item(0).getChildNodes();

      for (int i = 0; i < bodyNodes.getLength(); ++i) {
        if (bodyNodes.item(i) instanceof CharacterData) {
          CharacterData data = (CharacterData) bodyNodes.item(i);
          this.mBodyTemplate = data.getData();
          break;
        }
      }
    }

    this.setName(getNodeValue(payloadNode, "TemplateName"));
  }

  public String getBodyTemplate() {
    return this.mBodyTemplate;
  }

  void setBodyTemplate(String bodyTemplate) {
    this.mBodyTemplate = bodyTemplate;
  }

  public String getTemplateName() {
    return this.getName();
  }
}
