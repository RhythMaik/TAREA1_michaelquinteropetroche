package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.Espectaculo;

public class EspectaculoDAO {

    // Devuelve todos los espectáculos, incluyendo el nombre del coordinador (si lo tiene)
    public List<Espectaculo> findAll() throws SQLException {
        List<Espectaculo> lista = new ArrayList<>();

        String sql = "SELECT e.id, e.nombre, e.fecha_inicio, e.fecha_fin, " +
                     "       e.id_coord, p.nombre AS nombre_coord " +
                     "FROM espectaculo e " +
                     "LEFT JOIN persona p ON e.id_coord = p.id " +
                     "ORDER BY e.id";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                long id = rs.getLong("id");
                String nombre = rs.getString("nombre");
                LocalDate fechaInicio = rs.getDate("fecha_inicio").toLocalDate();
                LocalDate fechaFin = rs.getDate("fecha_fin").toLocalDate();
                long idCoord = rs.getLong("id_coord");
                String nombreCoord = rs.getString("nombre_coord");

                Espectaculo e = new Espectaculo(id, nombre, fechaInicio, fechaFin);
                e.setIdCoordinador(idCoord);
                if (nombreCoord != null) {
                    e.setNombreCoordinador(nombreCoord);
                }
                lista.add(e);
            }
        }

        return lista;
    }

    // Busca un espectáculo por id
    public Espectaculo findById(long idBuscado) throws SQLException {
        String sql = "SELECT e.id, e.nombre, e.fecha_inicio, e.fecha_fin, " +
                     "       e.id_coord, p.nombre AS nombre_coord " +
                     "FROM espectaculo e " +
                     "LEFT JOIN persona p ON e.id_coord = p.id " +
                     "WHERE e.id = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idBuscado);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    long id = rs.getLong("id");
                    String nombre = rs.getString("nombre");
                    LocalDate fechaInicio = rs.getDate("fecha_inicio").toLocalDate();
                    LocalDate fechaFin = rs.getDate("fecha_fin").toLocalDate();
                    long idCoord = rs.getLong("id_coord");
                    String nombreCoord = rs.getString("nombre_coord");

                    Espectaculo e = new Espectaculo(id, nombre, fechaInicio, fechaFin);
                    e.setIdCoordinador(idCoord);
                    if (nombreCoord != null) {
                        e.setNombreCoordinador(nombreCoord);
                    }
                    return e;
                }
            }
        }
        return null;
    }

    // Inserta un nuevo espectáculo en BD (id autoincremental)
    public boolean insert(Espectaculo e) throws SQLException {
        String sql = "INSERT INTO espectaculo (nombre, fecha_inicio, fecha_fin, id_coord) " +
                     "VALUES (?,?,?,?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, e.getNombre());
            ps.setDate(2, Date.valueOf(e.getFechaInicio()));
            ps.setDate(3, Date.valueOf(e.getFechaFin()));
            ps.setLong(4, e.getIdCoordinador());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                return false;
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    e.setId(rs.getLong(1));
                }
            }
            return true;
        }
    }

    // Actualiza un espectáculo existente
    public boolean update(Espectaculo e) throws SQLException {
        String sql = "UPDATE espectaculo " +
                     "SET nombre = ?, fecha_inicio = ?, fecha_fin = ?, id_coord = ? " +
                     "WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getNombre());
            ps.setDate(2, Date.valueOf(e.getFechaInicio()));
            ps.setDate(3, Date.valueOf(e.getFechaFin()));
            ps.setLong(4, e.getIdCoordinador());
            ps.setLong(5, e.getId());

            int filas = ps.executeUpdate();
            return filas > 0;
        }
    }

    // ¿Existe un espectáculo con ese nombre?
    public boolean existeNombre(String nombre) throws SQLException {
        String sql = "SELECT COUNT(*) FROM espectaculo WHERE LOWER(nombre) = LOWER(?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // ¿Existe un espectáculo con ese nombre y distinto id?
    public boolean existeNombreParaOtro(String nombre, long idActual) throws SQLException {
        String sql = "SELECT COUNT(*) FROM espectaculo " +
                     "WHERE LOWER(nombre) = LOWER(?) AND id <> ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setLong(2, idActual);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
