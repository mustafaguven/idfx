package tr.com.idefix.android.azure;

/**
 * Created by mustafaguven on 07.01.2016.
 */

import java.io.ByteArrayOutputStream;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;

class Utils {
  public static boolean isNullOrWhiteSpace(String str) {
    return (str == null) || (str.trim().equals(""));
  }

  public static String getXmlString(Element element) throws Exception {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    StreamResult result = new StreamResult(buffer);

    DOMSource source = new DOMSource(element);
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    transformer.setOutputProperty("omit-xml-declaration", "yes");
    transformer.transform(source, result);

    return new String(buffer.toByteArray(), "UTF-8");
  }
}
