package servicios;

import dao.CredencialesDAO;
import modelo.Perfiles;
import modelo.Sesion;

public class ServicioLogin {

    private CredencialesDAO credDAO = new CredencialesDAO();

    public boolean login(String usuario, String contrasenia, Sesion sesion) {
        try {
            // Caso admin.properties (igual que ahora)
            // (si quieres, puedes mantener admin.user/admin.password aquí)
            // Sino, que el admin esté también en la BD.

            Perfiles perfil = credDAO.buscarPerfil(usuario, contrasenia);
            if (perfil == null) {
                return false;
            }

            String nombre = credDAO.buscarNombrePersona(usuario);
            sesion.setNombrePersona(nombre);
            sesion.setPerfil(perfil);
            return true;

        } catch (Exception e) {
            System.out.println("Error en login: " + e.getMessage());
            return false;
        }
    }
}
