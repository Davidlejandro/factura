import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class TestConection {

    public static boolean validarXML(String xmlPath, String xsdPath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
            System.out.println("XML válido contra el XSD.");
            return true;
        } catch (Exception e) {
            System.out.println("Error de validación: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        String xmlFile = "C:\\Users\\David Ruiz\\OneDrive\\Imágenes\\Escritorio\\Factura\\Proyecto (2) (1)\\Proyecto\\src\\main\\resources\\XML\\Firmados\\factura.xml";
        String xsdFile = "C:\\Users\\David Ruiz\\OneDrive\\Imágenes\\Escritorio\\Factura\\Proyecto (2) (1)\\Proyecto\\src\\main\\resources\\XSD\\factura_V2.1.0.xsd";

        validarXML(xmlFile, xsdFile);
    }
}