package repository;

import model.Factura;
import java.util.List;

public interface FacturaRepository {
    List<Factura> listarTodas();
    Factura obtenerPorId(int id);
    boolean insertar(Factura factura);
    boolean actualizar(Factura factura);
    boolean eliminar(int id);

    // Método especial para obtener el siguiente secuencial para un establecimiento y punto de emisión
    String obtenerSiguienteSecuencial(String estab, String ptoEmi);
}