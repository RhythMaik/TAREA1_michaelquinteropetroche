package servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import dao.ConexionBD;
import dao.EspectaculoDAO;
import modelo.Espectaculo;
import modelo.Perfiles;
import modelo.Sesion;

public class ServicioEspectaculos {

    private EspectaculoDAO dao = new EspectaculoDAO();

    // ======== MÉTODOS PÚBLICOS QUE LLAMA EL MAIN ========

    // Ver todos los espectáculos
    public void mostrarEspectaculos() {
        try {
            List<Espectaculo> lista = dao.findAll();
            if (lista == null || lista.isEmpty()) {
                System.out.println("No hay espectaculos.");
                return;
            }
            for (Espectaculo e : lista) {
                System.out.println(e);
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar espectaculos desde BD: " + e.getMessage());
        }
    }

    // Crear espectáculo (desde menú Coordinacion / Admin)
    public void crearEspectaculo(Scanner leer, Sesion sesion) {
        try {
            System.out.println("=== Crear espectaculo ===");
            System.out.print("Nombre (<=25, unico): ");
            String nombre = leer.nextLine().trim();

            if (!nombreEspectaculoValido(nombre)) {
                System.out.println("Nombre inválido (vacío, demasiado largo o con '|').");
                return;
            }

            if (dao.existeNombre(nombre)) {
                System.out.println("Ya existe un espectaculo con ese nombre.");
                return;
            }

            System.out.print("Fecha inicio (YYYY-MM-DD): ");
            String fechaIni = leer.nextLine().trim();
            System.out.print("Fecha fin (YYYY-MM-DD): ");
            String fechaFin = leer.nextLine().trim();

            LocalDate inicio, fin;
            try {
                inicio = LocalDate.parse(fechaIni);
                fin = LocalDate.parse(fechaFin);
            } catch (Exception ex) {
                System.out.println("Formato de fecha inválido.");
                return;
            }

            if (!periodoVigenciaValido(inicio, fin)) {
                System.out.println("Periodo inválido: la fecha fin es anterior al inicio o dura más de 1 año.");
                return;
            }

            // Determinar coordinador
            long idCoord = -1;
            String nombreCoord = null;

            if (sesion.getPerfil() == Perfiles.COORDINACION) {
                idCoord = buscarIdPersonaPorNombre(sesion.getNombrePersona());
                if (idCoord <= 0) {
                    System.out.println("No se encontró tu persona en la BD, se creará sin coordinador.");
                } else {
                    nombreCoord = sesion.getNombrePersona();
                }
            } else if (sesion.getPerfil() == Perfiles.ADMIN) {
                List<CoordinadorDTO> coords = cargarCoordinadores();
                if (coords.isEmpty()) {
                    System.out.println("No hay usuarios de Coordinacion en la BD. Se crea sin coordinador.");
                } else {
                    System.out.println("Elige coordinador (número):");
                    for (int i = 0; i < coords.size(); i++) {
                        CoordinadorDTO c = coords.get(i);
                        System.out.printf("%d) %s (%s)%n", i + 1, c.nombre, c.nombreUsuario);
                    }
                    System.out.print("> ");
                    int seleccion = -1;
                    try {
                        seleccion = leer.nextInt();
                        leer.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada no numérica. Se crea sin coordinador.");
                        leer.nextLine();
                    }
                    if (seleccion >= 1 && seleccion <= coords.size()) {
                        CoordinadorDTO elegido = coords.get(seleccion - 1);
                        idCoord = elegido.idPersona;
                        nombreCoord = elegido.nombre;
                        System.out.println("Coordinador seleccionado: " + nombreCoord);
                    } else if (seleccion != -1) {
                        System.out.println("Selección inválida. Se crea sin coordinador.");
                    }
                }
            } else {
                System.out.println("No tienes permisos para crear espectáculos.");
                return;
            }

            Espectaculo nuevo = new Espectaculo(0, nombre, inicio, fin);
            nuevo.setIdCoordinador(idCoord);
            nuevo.setNombreCoordinador(nombreCoord);

            if (dao.insert(nuevo)) {
                System.out.println("Espectáculo creado correctamente en BD.");
                System.out.println("Id asignado: " + nuevo.getId());
                System.out.println(nuevo);
            } else {
                System.out.println("No se pudo insertar el espectáculo en la BD.");
            }

        } catch (SQLException e) {
            System.out.println("Error en BD al crear espectáculo: " + e.getMessage());
        }
    }

    // Modificar espectáculo (nombre, fechas y coordinador para admin)
    public void modificarEspectaculo(Scanner leer, Sesion sesion) {
        try {
            List<Espectaculo> lista = dao.findAll();
            if (lista.isEmpty()) {
                System.out.println("No hay espectáculos para modificar.");
                return;
            }

            // Mostrar lista
            for (Espectaculo e : lista) {
                System.out.println(e);
                System.out.println();
            }

            System.out.print("ID del espectáculo a modificar: ");
            long id;
            try {
                id = leer.nextLong();
                leer.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("No es un número.");
                leer.nextLine();
                return;
            }

            Espectaculo espectaculo = dao.findById(id);
            if (espectaculo == null) {
                System.out.println("No existe un espectáculo con id " + id + ".");
                return;
            }

            int op = -1;
            do {
                System.out.println("\n=== Modificar espectaculo ===");
                System.out.println(espectaculo);
                System.out.println("1) Cambiar nombre");
                System.out.println("2) Cambiar fechas");
                if (sesion.getPerfil() == Perfiles.ADMIN) {
                    System.out.println("3) Cambiar coordinador");
                }
                System.out.println("5) Ver espectaculo");
                System.out.println("0) Guardar y salir");
                System.out.print("> ");

                try {
                    op = leer.nextInt();
                    leer.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("No introduciste un número.");
                    leer.nextLine();
                    continue;
                }

                switch (op) {
                    case 1: { // Cambiar nombre
                        System.out.print("Nuevo nombre (<=25, único): ");
                        String nuevo = leer.nextLine().trim();
                        if (!nombreEspectaculoValido(nuevo)) {
                            System.out.println("Nombre inválido (vacío o >25 caracteres o contiene '|').");
                            break;
                        }
                        if (dao.existeNombreParaOtro(nuevo, espectaculo.getId())) {
                            System.out.println("Ya existe un espectáculo con ese nombre.");
                            break;
                        }
                        espectaculo.setNombre(nuevo);
                        System.out.println("Nombre actualizado.");
                        break;
                    }
                    case 2: { // Cambiar fechas
                        System.out.print("Nueva fecha de inicio (YYYY-MM-DD): ");
                        String fechaInicio = leer.nextLine().trim();
                        System.out.print("Nueva fecha de fin (YYYY-MM-DD): ");
                        String fechaFin = leer.nextLine().trim();
                        LocalDate ini, fin;
                        try {
                            ini = LocalDate.parse(fechaInicio);
                            fin = LocalDate.parse(fechaFin);
                        } catch (Exception ex) {
                            System.out.println("Formato de fecha inválido.");
                            break;
                        }
                        if (!periodoVigenciaValido(ini, fin)) {
                            System.out.println("Periodo inválido: la fecha de fin es anterior al inicio o dura más de un año.");
                            break;
                        }
                        espectaculo.setFechaInicio(ini);
                        espectaculo.setFechaFin(fin);
                        System.out.println("Fechas actualizadas.");
                        break;
                    }
                    case 3: { // Cambiar coordinador (solo admin)
                        if (sesion.getPerfil() != Perfiles.ADMIN) {
                            System.out.println("Solo ADMIN puede cambiar el coordinador.");
                            break;
                        }
                        List<CoordinadorDTO> coords = cargarCoordinadores();
                        if (coords.isEmpty()) {
                            System.out.println("No hay usuarios de Coordinacion en la BD.");
                            break;
                        }
                        System.out.println("Coordinadores disponibles:");
                        for (int i = 0; i < coords.size(); i++) {
                            CoordinadorDTO c = coords.get(i);
                            System.out.printf("%d) %s (%s)%n", i + 1, c.nombre, c.nombreUsuario);
                        }
                        System.out.print("Nº a asignar como coordinador: ");
                        try {
                            int idx = leer.nextInt() - 1;
                            leer.nextLine();
                            if (idx >= 0 && idx < coords.size()) {
                                CoordinadorDTO elegido = coords.get(idx);
                                espectaculo.setIdCoordinador(elegido.idPersona);
                                espectaculo.setNombreCoordinador(elegido.nombre);
                                System.out.println("Nuevo coordinador: " + elegido.nombre);
                            } else {
                                System.out.println("Selección inválida.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("No introduciste un número.");
                            leer.nextLine();
                        }
                        break;
                    }
                    case 5:
                        System.out.println(espectaculo);
                        break;
                    case 0:
                        if (dao.update(espectaculo)) {
                            System.out.println("Cambios guardados en BD.");
                        } else {
                            System.out.println("No se pudieron guardar los cambios en BD.");
                        }
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }

            } while (op != 0);

        } catch (SQLException e) {
            System.out.println("Error en BD al modificar espectáculo: " + e.getMessage());
        }
    }

    // ======== MÉTODOS PRIVADOS DE APOYO ========

    private boolean nombreEspectaculoValido(String nombre) {
        return nombre != null &&
               !nombre.isEmpty() &&
               nombre.length() <= 25 &&
               !nombre.contains("|");
    }

    private boolean periodoVigenciaValido(LocalDate inicio, LocalDate fin) {
        if (fin.isBefore(inicio)) return false;
        return !fin.isAfter(inicio.plusYears(1));
    }

    // Busca el id de persona por nombre exacto
    private long buscarIdPersonaPorNombre(String nombre) throws SQLException {
        String sql = "SELECT id FROM persona WHERE nombre = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id");
                }
            }
        }
        return -1;
    }

    // DTO interno para mostrar la lista de coordinadores
    private static class CoordinadorDTO {
        long idPersona;
        String nombre;
        String nombreUsuario;

        CoordinadorDTO(long idPersona, String nombre, String nombreUsuario) {
            this.idPersona = idPersona;
            this.nombre = nombre;
            this.nombreUsuario = nombreUsuario;
        }
    }

    // Carga coordinadores desde persona + credenciales
    private List<CoordinadorDTO> cargarCoordinadores() throws SQLException {
        List<CoordinadorDTO> lista = new ArrayList<>();

        String sql =
            "SELECT p.id, p.nombre, c.nombre_usuario " +
            "FROM persona p " +
            "JOIN credenciales c ON p.id = c.id_persona " +
            "WHERE UPPER(c.perfil) = 'COORDINACION' " +
            "ORDER BY p.nombre";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                long id = rs.getLong("id");
                String nombre = rs.getString("nombre");
                String usuario = rs.getString("nombre_usuario");
                lista.add(new CoordinadorDTO(id, nombre, usuario));
            }
        }
        return lista;
    }
}
