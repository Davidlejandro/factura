package model;

import java.math.BigDecimal;
import java.util.Date;

public class Factura {
    private int id;
    private int idEmpresa;  // <-- agregado
    private Date fechaEmision;
    private int idCliente;  // <-- agregado (si usas idCliente)
    private String clienteNombre;
    private String clienteCedula;
    private BigDecimal total;

    // Campos para clave de acceso y factura electrónica
    private String claveAcceso;   // agregado
    private String estab;
    private String ptoEmi;
    private String secuencial;
    private String tipoEmision;
    private String ambiente;
    private String ruc;
    private String codDoc;

    private String moneda;             // agregado
    private BigDecimal totalSinImpuestos;  // agregado
    private BigDecimal totalDescuento;      // agregado
    private BigDecimal importeTotal;        // agregado

    public Factura() { }

    // Constructor completo (puedes generar con tu IDE para no olvidar campos)
    public Factura(int id, int idEmpresa, Date fechaEmision, int idCliente, String clienteNombre, String clienteCedula,
                   BigDecimal total, String claveAcceso, String estab, String ptoEmi, String secuencial,
                   String tipoEmision, String ambiente, String ruc, String codDoc, String moneda,
                   BigDecimal totalSinImpuestos, BigDecimal totalDescuento, BigDecimal importeTotal) {
        this.id = id;
        this.idEmpresa = idEmpresa;
        this.fechaEmision = fechaEmision;
        this.idCliente = idCliente;
        this.clienteNombre = clienteNombre;
        this.clienteCedula = clienteCedula;
        this.total = total;
        this.claveAcceso = claveAcceso;
        this.estab = estab;
        this.ptoEmi = ptoEmi;
        this.secuencial = secuencial;
        this.tipoEmision = tipoEmision;
        this.ambiente = ambiente;
        this.ruc = ruc;
        this.codDoc = codDoc;
        this.moneda = moneda;
        this.totalSinImpuestos = totalSinImpuestos;
        this.totalDescuento = totalDescuento;
        this.importeTotal = importeTotal;
    }

    // Getters y setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdEmpresa() { return idEmpresa; }
    public void setIdEmpresa(int idEmpresa) { this.idEmpresa = idEmpresa; }

    public Date getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(Date fechaEmision) { this.fechaEmision = fechaEmision; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public String getClienteCedula() { return clienteCedula; }
    public void setClienteCedula(String clienteCedula) { this.clienteCedula = clienteCedula; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getClaveAcceso() { return claveAcceso; }
    public void setClaveAcceso(String claveAcceso) { this.claveAcceso = claveAcceso; }

    public String getEstab() { return estab; }
    public void setEstab(String estab) { this.estab = estab; }

    public String getPtoEmi() { return ptoEmi; }
    public void setPtoEmi(String ptoEmi) { this.ptoEmi = ptoEmi; }

    public String getSecuencial() { return secuencial; }
    public void setSecuencial(String secuencial) { this.secuencial = secuencial; }

    public String getTipoEmision() { return tipoEmision; }
    public void setTipoEmision(String tipoEmision) { this.tipoEmision = tipoEmision; }

    public String getAmbiente() { return ambiente; }
    public void setAmbiente(String ambiente) { this.ambiente = ambiente; }

    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }

    public String getCodDoc() { return codDoc; }
    public void setCodDoc(String codDoc) { this.codDoc = codDoc; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }

    public BigDecimal getTotalSinImpuestos() { return totalSinImpuestos; }
    public void setTotalSinImpuestos(BigDecimal totalSinImpuestos) { this.totalSinImpuestos = totalSinImpuestos; }

    public BigDecimal getTotalDescuento() { return totalDescuento; }
    public void setTotalDescuento(BigDecimal totalDescuento) { this.totalDescuento = totalDescuento; }

    public BigDecimal getImporteTotal() { return importeTotal; }
    public void setImporteTotal(BigDecimal importeTotal) { this.importeTotal = importeTotal; }
}