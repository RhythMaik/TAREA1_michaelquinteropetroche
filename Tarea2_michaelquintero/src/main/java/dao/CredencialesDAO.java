package dao;

import java.sql.*;
import modelo.Perfiles;

public class CredencialesDAO {

    public Perfiles buscarPerfil(String usuario, String contrasenia) throws SQLException {
        String sql = "SELECT perfil FROM credenciales WHERE nombre_usuario = ? AND contrasenia = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, contrasenia);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String perfilStr = rs.getString("perfil");
                    return Perfiles.valueOf(perfilStr.toUpperCase());
                } else {
                    return null;
                }
            }
        }
    }

    public String buscarNombrePersona(String usuario) throws SQLException {
        String sql = "SELECT p.nombre FROM persona p JOIN credenciales c ON p.id = c.id_persona WHERE c.nombre_usuario = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre");
                } else {
                    return null;
                }
            }
        }
    }
}
