package services;

import org.w3c.dom.*;

import repository.FacturaRepository;
import repository.FacturaRepositoryImple;
import util.ClaveAccesoUtil;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class GeneradorFacturaXML {
    private static FacturaRepository facturaRepository = new FacturaRepositoryImple();

    // Namespace correcto para factura electrónica SRI versión 2.1.0
    private static final String NAMESPACE = "http://www.sri.gob.ec/comprobanteElectronico";

    public static String generarFacturaXML() throws Exception {
        String claveAcceso = null;
        try {
            // Parámetros para clave de acceso
            String fechaEmisionClave = "02062025"; // ddMMyyyy
            String tipoComprobante = "01";
            String ruc = "1719284752001";
            String ambiente = "1";
            String establecimiento = "001";
            String puntoEmision = "001";
            String secuencial = facturaRepository.obtenerSiguienteSecuencial(establecimiento, puntoEmision);
            String tipoEmision = "1";
            String codigoNumerico = ClaveAccesoUtil.generarCodigoNumerico();

            claveAcceso = ClaveAccesoUtil.generarClaveAcceso(
                    fechaEmisionClave, tipoComprobante, ruc,
                    ambiente, establecimiento, puntoEmision,
                    secuencial, codigoNumerico, tipoEmision
            );

            // Crear xml
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true); // Muy importante para XML con namespace
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element factura = doc.createElementNS(NAMESPACE, "factura");
            factura.setAttribute("xmlns", NAMESPACE);
            factura.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            factura.setAttribute("xsi:schemaLocation", NAMESPACE + " http://www.sri.gob.ec/comprobantes-electronicos/documentos/factura_V2.1.0.xsd");
            factura.setAttribute("id", "comprobante");
            factura.setAttribute("version", "2.1.0");
            doc.appendChild(factura);

            // infoTributaria
            Element infoTributaria = doc.createElementNS(NAMESPACE, "infoTributaria");
            factura.appendChild(infoTributaria);

            crearElemento(doc, infoTributaria, "ambiente", "1");
            crearElemento(doc, infoTributaria, "tipoEmision", "1");
            crearElemento(doc, infoTributaria, "razonSocial", "Mi Empresa S.A.");
            crearElemento(doc, infoTributaria, "nombreComercial", "Mi Empresa");
            crearElemento(doc, infoTributaria, "ruc", "1790012345001");
            crearElemento(doc, infoTributaria, "claveAcceso", claveAcceso);
            crearElemento(doc, infoTributaria, "codDoc", "01"); // Factura
            crearElemento(doc, infoTributaria, "estab", "001");
            crearElemento(doc, infoTributaria, "ptoEmi", "001");
            crearElemento(doc, infoTributaria, "secuencial", secuencial);
            crearElemento(doc, infoTributaria, "dirMatriz", "Av. Principal 123");

            // infoFactura
            Element infoFactura = doc.createElementNS(NAMESPACE, "infoFactura");
            factura.appendChild(infoFactura);

            crearElemento(doc, infoFactura, "fechaEmision", "02/06/2025");
            crearElemento(doc, infoFactura, "dirEstablecimiento", "Sucursal 1");
            crearElemento(doc, infoFactura, "tipoIdentificacionComprador", "05");
            crearElemento(doc, infoFactura, "razonSocialComprador", "Juan Pérez");
            crearElemento(doc, infoFactura, "identificacionComprador", "0912345678");

            // Totales
            Element totalSinImpuestos = doc.createElementNS(NAMESPACE, "totalSinImpuestos");
            totalSinImpuestos.setTextContent("100.00");
            infoFactura.appendChild(totalSinImpuestos);

            Element totalDescuento = doc.createElementNS(NAMESPACE, "totalDescuento");
            totalDescuento.setTextContent("0.00");
            infoFactura.appendChild(totalDescuento);

            // totalConImpuestos
            Element totalConImpuestos = doc.createElementNS(NAMESPACE, "totalConImpuestos");
            infoFactura.appendChild(totalConImpuestos);

            Element totalImpuesto = doc.createElementNS(NAMESPACE, "totalImpuesto");
            totalConImpuestos.appendChild(totalImpuesto);

            crearElemento(doc, totalImpuesto, "codigo", "2");           // IVA
            crearElemento(doc, totalImpuesto, "codigoPorcentaje", "2"); // Tarifa 12%
            crearElemento(doc, totalImpuesto, "baseImponible", "100.00");
            crearElemento(doc, totalImpuesto, "valor", "12.00");

            crearElemento(doc, infoFactura, "importeTotal", "112.00");
            crearElemento(doc, infoFactura, "moneda", "DOLAR");

            // pagos
            Element pagos = doc.createElementNS(NAMESPACE, "pagos");
            infoFactura.appendChild(pagos);

            Element pago = doc.createElementNS(NAMESPACE, "pago");
            pagos.appendChild(pago);

            crearElemento(doc, pago, "formaPago", "01"); // Efectivo
            crearElemento(doc, pago, "total", "112.00");

            // detalles
            Element detalles = doc.createElementNS(NAMESPACE, "detalles");
            factura.appendChild(detalles);

            Element detalle = doc.createElementNS(NAMESPACE, "detalle");
            detalles.appendChild(detalle);

            crearElemento(doc, detalle, "codigoPrincipal", "PROD001");
            crearElemento(doc, detalle, "descripcion", "Producto de prueba");
            crearElemento(doc, detalle, "cantidad", "2");
            crearElemento(doc, detalle, "precioUnitario", "50.00");
            crearElemento(doc, detalle, "descuento", "0.00");
            crearElemento(doc, detalle, "precioTotalSinImpuesto", "100.00");

            Element impuestos = doc.createElementNS(NAMESPACE, "impuestos");
            detalle.appendChild(impuestos);

            Element impuesto = doc.createElementNS(NAMESPACE, "impuesto");
            impuestos.appendChild(impuesto);

            crearElemento(doc, impuesto, "codigo", "2");
            crearElemento(doc, impuesto, "codigoPorcentaje", "2");
            crearElemento(doc, impuesto, "tarifa", "12.00");
            crearElemento(doc, impuesto, "baseImponible", "100.00");
            crearElemento(doc, impuesto, "valor", "12.00");

            // infoAdicional
            Element infoAdicional = doc.createElementNS(NAMESPACE, "infoAdicional");
            factura.appendChild(infoAdicional);

            Element campoAdicional = doc.createElementNS(NAMESPACE, "campoAdicional");
            campoAdicional.setAttribute("nombre", "Email");
            campoAdicional.setTextContent("cliente@correo.com");
            infoAdicional.appendChild(campoAdicional);

            // Guardar XML sin firmar
            String rutaNoFirmado = "src/main/resources/XML/NoFirmados/factura.xml";
            guardarXML(doc, rutaNoFirmado);

            // Parámetros para firmar el XML
            String rutaFirmado = "src/main/resources/XML/Firmados/factura.xml";
            String keystorePath = "src/main/resources/certs/14045426_identity_1719284752.p12";
            String keystorePass = "Elvis2103";
            String alias = "1";
            String keyPass = "Elvis2103";

            // Firmar XML
            FirmarXml.firmarXML(rutaNoFirmado, rutaFirmado, keystorePath, keystorePass, alias, keyPass);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al generar la factura electrónica", e);
        }
        return claveAcceso;
    }

    private static void crearElemento(Document doc, Element padre, String nombre, String valor) {
        Element elem = doc.createElementNS(NAMESPACE, nombre);
        elem.setTextContent(valor);
        padre.appendChild(elem);
    }

    private static void guardarXML(Document doc, String ruta) throws Exception {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        File archivo = new File(ruta);
        archivo.getParentFile().mkdirs(); // Crea los directorios si no existen
        transformer.transform(new DOMSource(doc), new StreamResult(archivo));
    }
}