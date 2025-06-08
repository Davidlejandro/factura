package repository;

import model.Factura;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaRepositoryImple implements FacturaRepository {

    @Override
    public List<Factura> listarTodas() {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM factura";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Factura f = new Factura();
                // Mapear campos de ResultSet a objeto Factura
                f.setId(rs.getInt("id_factura"));
                f.setEstab(rs.getString("estab"));
                f.setPtoEmi(rs.getString("pto_emi"));
                f.setSecuencial(rs.getString("secuencial"));
                // Completar con demás campos...
                lista.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Factura obtenerPorId(int id) {
        String sql = "SELECT * FROM factura WHERE id_factura = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Factura f = new Factura();
                    f.setId(rs.getInt("id_factura"));
                    f.setEstab(rs.getString("estab"));
                    f.setPtoEmi(rs.getString("pto_emi"));
                    f.setSecuencial(rs.getString("secuencial"));
                    // Completar con demás campos...
                    return f;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertar(Factura factura) {
        String sql = "INSERT INTO factura (id_empresa, id_cliente, fecha_emision, clave_acceso, cod_doc, estab, pto_emi, secuencial, tipo_emision, ambiente, moneda, total_sin_impuestos, total_descuento, importe_total) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, factura.getIdEmpresa());
            ps.setInt(2, factura.getIdCliente());
            ps.setDate(3, new java.sql.Date(factura.getFechaEmision().getTime()));
            ps.setString(4, factura.getClaveAcceso());
            ps.setString(5, factura.getCodDoc());
            ps.setString(6, factura.getEstab());
            ps.setString(7, factura.getPtoEmi());
            ps.setString(8, factura.getSecuencial());
            ps.setString(9, factura.getTipoEmision());
            ps.setString(10, factura.getAmbiente());
            ps.setString(11, factura.getMoneda());
            ps.setBigDecimal(12, factura.getTotalSinImpuestos());
            ps.setBigDecimal(13, factura.getTotalDescuento());
            ps.setBigDecimal(14, factura.getImporteTotal());

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean actualizar(Factura factura) {
        // Implementa si necesitas actualizar factura
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        // Implementa si necesitas eliminar factura
        return false;
    }

    @Override
    public String obtenerSiguienteSecuencial(String estab, String ptoEmi) {
        String sql = "SELECT MAX(secuencial) AS max_secuencial FROM factura WHERE estab = ? AND pto_emi = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estab);
            ps.setString(2, ptoEmi);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String maxSecuencial = rs.getString("max_secuencial");
                    if (maxSecuencial == null) {
                        return "000000001";
                    }
                    int siguiente = Integer.parseInt(maxSecuencial) + 1;
                    return String.format("%09d", siguiente);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "000000001"; // valor por defecto si falla
    }
}