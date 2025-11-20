package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {

    private static Connection conexion;

    private ConexionBD() {}

    public static Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            inicializarConexion();
        }
        return conexion;
    }

    private static void inicializarConexion() throws SQLException {
        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream("src/main/resources/resources/aplication.properties")) {
            props.load(fis);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");

            if (url == null || user == null) {
                throw new SQLException("Faltan propiedades db.url o db.user en aplication.properties");
            }

            // Cargar driver de MariaDB
            try {
                Class.forName("org.mariadb.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("No se ha encontrado el driver de MariaDB en el classpath", e);
            }

            conexion = DriverManager.getConnection(url, user, pass);

        } catch (IOException e) {
            throw new SQLException("Error leyendo aplication.properties: " + e.getMessage(), e);
        }
    }
}
