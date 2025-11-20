package vista;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import modelo.Perfiles;
import modelo.Sesion;
import servicios.ServicioEspectaculos;
import servicios.ServicioLogin;

/*
 *@author Michael Quintero Petroche 
 */
public class Main {

    // RUTA DE LOS FICHEROS DEL QUE VAMOS A LEER O ESCRIBIR
    private static String rutaCredendiales;
    private static String rutaEspectaculos;   // ya no se usa para BD, pero lo dejamos cargado
    private static String rutaPaises;

    private static String adminUser;
    private static String adminContra;
    private static String adminNombre;

    // Servicio de espectáculos (BD)
    private static ServicioEspectaculos servicioEspectaculos = new ServicioEspectaculos();

    // METODO PARA LA CARGA DEL FICHERO aplication.properties en el programa
    private static void cargarConfi() {
        Properties propiedades = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/resources/aplication.properties")) {
            propiedades.load(fis);
            rutaCredendiales = propiedades.getProperty("ruta.credenciales");
            rutaEspectaculos = propiedades.getProperty("ruta.espectaculos");
            rutaPaises = propiedades.getProperty("ruta.paises");

            adminUser = propiedades.getProperty("admin.user");
            adminContra = propiedades.getProperty("admin.password");
            adminNombre = propiedades.getProperty("admin.nombre");
        } catch (FileNotFoundException e) {
            System.out.println("Fichero no encontrado");
        } catch (IOException e) {
            System.out.println("ERROR AL LEER EL FICHERO");
        }
    }

    // IMPRESION DE MENUS
    private static void mostrarMensajeBienvenida() {
        System.out.println("================================================================");
        System.out.println("==========Bienvenido al programa de gestion del circo===========");
        System.out.println("================================================================");
    }

    private static void mostrarMenuInvitado() {
        System.out.println("===SESION INICIADA COMO INVITADO===");
        System.out.println("1. Ver espectaculos");
        System.out.println("2. Iniciar sesion");
        System.out.println("0. Salir del programa");
    }

    private static void mostrarMenuArtista() {
        System.out.println("===SESION INICIADA COMO ARTISTA===");
        System.out.println("1. Ver ficha");
        System.out.println("2. Ver espectaculos");
        System.out.println("0. Cerrar sesion");
    }

    private static void mostrarmenuCoordinacion() {
        System.out.println("===SESION INICIADA COMO COORDINADOR===");
        System.out.println("1. Crear espectaculo");
        System.out.println("2. Modificar espectaculo");
        System.out.println("3. Ver espectaculos");
        System.out.println("0. Cerrar sesion");
    }

    private static void mostrarMenuAdmin() {
        System.out.println("===SESION INICIADA COMO ADMIN===");
        System.out.println("1. Registrar persona");
        System.out.println("2. Crear espectaculo");
        System.out.println("3. Ver espectaculos");
        System.out.println("4. Modificar espectaculos");
        System.out.println("0. Cerrar sesion");
    }

    // Este metodo sirve para cerrar sesion, haciendo que la sesion quede vacia
    private static void cerrarSesion(Sesion sesion) {
        sesion.setNombrePersona(null);
        sesion.setPerfil(null);
        System.out.println("Volviendo a Invitado");
    }

    // FUNCIONALIDAD DE LOS MENUS, LOOPEABLES QUE SI NO EL MAIN SE HACE ENORME
    private static void funcMenuCoordinacion(Scanner leer, Sesion sesion) {
        int op = -1;
        do {
            op = -1;
            while (op != 0) {
                mostrarmenuCoordinacion();
                try {
                    op = leer.nextInt();
                    leer.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Opcion invalida. Introduce un numero.");
                    leer.nextLine();
                    continue;
                }

                switch (op) {
                    case 1:
                        servicioEspectaculos.crearEspectaculo(leer, sesion);
                        break;
                    case 2:
                        servicioEspectaculos.modificarEspectaculo(leer, sesion);
                        break;
                    case 3:
                        servicioEspectaculos.mostrarEspectaculos();
                        break;
                    case 0:
                        cerrarSesion(sesion);
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }
            }
        } while (sesion.getPerfil() != null);
    }

    private static void funcMenuArtista(Scanner leer, Sesion sesion) {
        int op = -1;
        do {
            op = -1;
            while (op != 0) {
                mostrarMenuArtista();
                try {
                    op = leer.nextInt();
                    leer.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Opcion invalida. Introduce un numero.");
                    leer.nextLine();
                    continue;
                }

                switch (op) {
                    case 1:
                        System.out.println("(No terminado) Ver ficha");
                        break;
                    case 2:
                        servicioEspectaculos.mostrarEspectaculos();
                        break;
                    case 0:
                        cerrarSesion(sesion);
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }
            }
        } while (sesion.getPerfil() != null);
    }

    private static void funcMenuAdmin(Scanner leer, Sesion sesion) {
        int op = -1;
        do {
            op = -1;

            while (op != 0) {
                mostrarMenuAdmin();
                try {
                    op = leer.nextInt();
                    leer.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Opcion invalida. Introduce un numero.");
                    leer.nextLine();
                    continue;
                }

                switch (op) {
                    case 1:
                        registrarPersona();
                        break;
                    case 2:
                        servicioEspectaculos.crearEspectaculo(leer, sesion);
                        break;
                    case 3:
                        servicioEspectaculos.mostrarEspectaculos();
                        break;
                    case 4:
                        servicioEspectaculos.modificarEspectaculo(leer, sesion);
                        break;
                    case 0:
                        cerrarSesion(sesion);
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }
            }
        } while (sesion.getPerfil() != null);
    }

    // Validaciones muy basicas de usuario, contrasenia y email
    private static boolean esUsuarioValido(String usuario) {
        // Solo letras sin tildes, minimo 3 letras y en minusculas
        return usuario != null && usuario.matches("^[a-z]{3,}$");
    }

    private static boolean esContraValida(String contra) {
        return contra != null && contra.length() > 2 && !contra.contains(" ");
    }

    private static boolean esEmailValido(String email) {
        return email != null && email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    }

    // Carga de los paises en el programa
    private static List<String> cargarPaisIdsDesdeXml() {
        List<String> ids = new ArrayList<>();
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream("resources/paises.xml")) {
            if (is == null) {
                System.out.println("No se encontro resources/paises.xml");
                return ids;
            }
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = db.parse(is);
            NodeList idsNodes = doc.getElementsByTagName("id");
            for (int i = 0; i < idsNodes.getLength(); i++) {
                String id = idsNodes.item(i).getTextContent();
                if (id != null && !id.trim().isEmpty()) {
                    ids.add(id.trim().toUpperCase());
                }
            }
        } catch (ParserConfigurationException e) {
            System.out.println("Creo que es un error de la configuracion del parser " + e.getMessage());
        } catch (SAXException e) {
            System.out.println("XML de paises mal formado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al leer el fichero paises: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("Permiso denegado al leer paises.xml");
        }
        return ids;
    }

    // Validacion para paises para poder validar nuevas credenciales
    private static boolean esPaisIdValido(String idPais) {
        if (idPais == null)
            return false;
        return cargarPaisIdsDesdeXml().contains(idPais.trim().toUpperCase());
    }

    // Viendo si se encuentra el usuario y el correo dentro de credenciales
    private static boolean existeUsuarioEnFichero(String usuario) {
        try (FileInputStream fis = new FileInputStream(rutaCredendiales);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;
                String[] p = line.split("\\|", -1);
                if (p.length < 7)
                    continue;
                if (p[1].trim().equalsIgnoreCase(usuario))
                    return true;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichero no encontrado");
            return false;
        } catch (SecurityException e) {
            System.out.println("Permiso denegado para leer: " + rutaCredendiales);
            return false;
        } catch (IOException e) {
            System.out.println("Error al leer el fichero credenciales: " + e.getMessage());
            return false;
        }
        return false;
    }

    private static boolean existeEmailEnFichero(String email) {
        try (FileInputStream fis = new FileInputStream(rutaCredendiales);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;
                String[] p = line.split("\\|", -1);
                if (p.length < 7)
                    continue;
                if (p[3].trim().equalsIgnoreCase(email))
                    return true;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichero no encontrado");
            return false;
        } catch (SecurityException e) {
            System.out.println("Permiso denegado para leer: " + rutaCredendiales);
            return false;
        } catch (IOException e) {
            System.out.println("Error al leer el fichero credenciales: " + e.getMessage());
            return false;
        }
        return false;
    }

    // Encontrar el ultimo id para colocarlo y no se duplique el id
    private static long siguienteIdPersonaPorUltimaLinea() {
        String ultimaLinea = null;
        try (FileInputStream fis = new FileInputStream(rutaCredendiales);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String l;
            while ((l = br.readLine()) != null) {
                l = l.trim();
                if (l.isEmpty())
                    continue;
                ultimaLinea = l;
            }
            if (ultimaLinea == null)
                return 1L;
            String[] p = ultimaLinea.split("\\|", -1);
            return Long.parseLong(p[0].trim()) + 1;
        } catch (FileNotFoundException e) {
            System.out.println("Fichero no encontrado");
            return -1;
        } catch (SecurityException e) {
            System.out.println("Permiso denegado para leer: " + rutaCredendiales);
            return -1;
        } catch (IOException e) {
            System.out.println("Error al leer el fichero credenciales: " + e.getMessage());
            return -1;
        }
    }

    private static boolean aniadirLineaCredenciales(String linea) {
        try (FileOutputStream fos = new FileOutputStream(rutaCredendiales, true);
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter bw = new BufferedWriter(osw)) {

            bw.newLine();
            bw.write(linea);
            return true;

        } catch (java.io.FileNotFoundException e) {
            System.out.println("No se pudo abrir " + rutaCredendiales + " para escritura.");
        } catch (java.io.IOException e) {
            System.out.println("Error al escribir las credenciales: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("Permiso denegado al escribir en " + rutaCredendiales);
        }
        return false;
    }

    // Listas de países en memoria
    private static final List<String> idsPais = new ArrayList<>();
    private static final List<String> nombresPais = new ArrayList<>();

    // Metodo para leer y cargar los paises
    private static void cargarPaisesEnListas() {
        try (InputStream is = new FileInputStream(rutaPaises)) {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = db.parse(is);
            NodeList paises = doc.getElementsByTagName("pais");

            for (int i = 0; i < paises.getLength(); i++) {
                Element elemento = (Element) paises.item(i);
                NodeList nid = elemento.getElementsByTagName("id");
                NodeList nnombre = elemento.getElementsByTagName("nombre");
                if (nid.getLength() > 0 && nnombre.getLength() > 0) {
                    String id = nid.item(0).getTextContent();
                    String nombre = nnombre.item(0).getTextContent();
                    if (id != null && !id.trim().isEmpty() && nombre != null && !nombre.trim().isEmpty()) {
                        idsPais.add(id.trim().toUpperCase());
                        nombresPais.add(nombre.trim());
                    }
                }
            }
        } catch (SAXException e) {
            System.out.println("Error del parser o de la aplicacion");
        } catch (ParserConfigurationException e) {
            System.out.println("Fallo en la configuracion de parser");
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo abrir " + rutaPaises + " para lectura.");
        } catch (IOException e) {
            System.out.println("Error al leer paises: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("Permiso denegado al leer " + rutaPaises);
        }
    }

    // Muestra de paises que ira al metodo de registrar persona
    private static void mostrarPaises() {
        if (idsPais.isEmpty() || nombresPais.isEmpty()) {
            cargarPaisesEnListas();
        }
        if (idsPais.isEmpty()) {
            System.out.println(" No hay paises cargados.");
            return;
        }

        System.out.println("IDs de pais disponibles (ID - Pais):");
        int limite = idsPais.size();
        for (int i = 0; i < limite; i++) {
            System.out.println(idsPais.get(i) + " - " + nombresPais.get(i));
        }
    }

    private static void registrarPersona() {
        Scanner leer = new Scanner(System.in);
        System.out.println("=== Registro de nueva persona ===");

        // Datos personales
        System.out.print("Nombre real: ");
        String nombre = leer.nextLine().trim();

        System.out.print("Email: ");
        String email = leer.nextLine().trim();
        if (!esEmailValido(email)) {
            System.out.println("Email invalido.");
            return;
        }
        if (existeEmailEnFichero(email)) {
            System.out.println("Ya existe una cuenta con ese email.");
            return;
        }

        mostrarPaises();
        System.out.print("ID de pais (segun paises.xml, ejemplo: ES): ");
        String idPais = leer.nextLine().trim();
        if (!esPaisIdValido(idPais)) {
            System.out.println("ID de pais no valido.");
            return;
        }
        String nacionalidad = idPais.toUpperCase();

        // Perfil
        System.out.print("Perfil (coordinacion/artista): ");
        String perfilTipo = leer.nextLine().trim().toLowerCase();
        if (!perfilTipo.equals("coordinacion") && !perfilTipo.equals("artista")) {
            System.out.println("Perfil invalido. Usa 'coordinacion' o 'artista'.");
            return;
        }

        // Coordinacion
        if (perfilTipo.equals("coordinacion")) {
            System.out.print("¿Es senior? (s/n): ");
            String senior = leer.nextLine().trim();
            if (senior.equalsIgnoreCase("s")) {
                System.out.print("Fecha senior desde (YYYY-MM-DD): ");
                String fechaSenior = leer.nextLine().trim();
                try {
                    LocalDate.parse(fechaSenior);
                } catch (DateTimeParseException e) {
                    System.out.println("Fecha invalida.");
                    return;
                }
            }
        } else { // Artista
            System.out.print("¿Tiene apodo? (s/n): ");
            String tApodo = leer.nextLine().trim();
            if (tApodo.equalsIgnoreCase("s")) {
                System.out.print("Apodo: ");
                String apodo = leer.nextLine().trim();
                // De momento no lo persistimos, solo lo validamos
            }
            System.out.println(
                    "Especialidades (si hay varios aniade comas por cada especialidad): ACROBACIA,HUMOR,MAGIA,EQUILIBRISMO,MALABARISMO");
            System.out.print("Especialidades: ");
            String esp = leer.nextLine().trim();
            if (!esp.isEmpty()) {
                String[] parts = esp.split(",");
                for (String s : parts) {
                    String v = s.trim().toUpperCase();
                    if (!(v.equals("ACROBACIA") || v.equals("HUMOR") || v.equals("MAGIA") || v.equals("EQUILIBRISMO")
                            || v.equals("MALABARISMO"))) {
                        System.out.println("Especialidad no valida: " + s.trim());
                        return;
                    }
                }
            }
        }
        // Credenciales, es decir nombre de usuario, contasenia
        System.out.print("Nombre de usuario (solo letras a-z, min 3): ");
        String usuario = leer.nextLine().trim().toLowerCase();
        if (!esUsuarioValido(usuario)) {
            System.out.println("Usuario invalido.");
            return;
        }
        if (usuario.equalsIgnoreCase(adminUser)) {
            System.out.println("Admin esta reservado.");
            return;
        }
        if (existeUsuarioEnFichero(usuario)) {
            System.out.println("Ya existe ese nombre de usuario.");
            return;
        }

        System.out.print("Contrasenia (minimo 3, sin espacios): ");
        String contra = leer.nextLine().trim();
        if (!esContraValida(contra)) {
            System.out.println("Contrasenia invalida.");
            return;
        }

        long id = siguienteIdPersonaPorUltimaLinea();
        if (id < 1)
            id = 1;

        String linea = id + "|" + usuario + "|" + contra + "|" + email + "|" + nombre + "|" + nacionalidad + "|"
                + perfilTipo;

        if (aniadirLineaCredenciales(linea)) {
            System.out.println("Persona registrada con id " + id + " (" + perfilTipo.toUpperCase() + ").");
        } else {
            System.out.println("No se pudo guardar el registro.");
        }
    }

    public static void main(String[] args) {

        cargarConfi();
        mostrarMensajeBienvenida();
        Scanner leer = new Scanner(System.in);
        Sesion sesion = new Sesion();
        int opcion = -1;

        do {
            mostrarMenuInvitado();
            try {
                opcion = leer.nextInt();
                leer.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Opcion invalida. Introduce un numero.");
                leer.nextLine();
                opcion = -1;
                continue;
            }

            switch (opcion) {
                case 1:
                    // Ver espectáculos (BD)
                    servicioEspectaculos.mostrarEspectaculos();
                    break;
                case 2:
                    // Login
                    ServicioLogin servicioLogin = new ServicioLogin();
                    System.out.print("Usuario: ");
                    String usuario = leer.nextLine().trim();
                    System.out.print("Contrasenia: ");
                    String contra = leer.nextLine().trim();
                    if (servicioLogin.login(usuario, contra, sesion)) {
                        System.out.println("Login correcto como " + sesion.getPerfil());
                        if (sesion.getPerfil() == Perfiles.COORDINACION) {
                            funcMenuCoordinacion(leer, sesion);
                        } else if (sesion.getPerfil() == Perfiles.ARTISTA) {
                            funcMenuArtista(leer, sesion);
                        } else if (sesion.getPerfil() == Perfiles.ADMIN) {
                            funcMenuAdmin(leer, sesion);
                        } else {
                            System.out.println("Perfil desconocido.");
                            cerrarSesion(sesion);
                        }
                    } else {
                        System.out.println("Usuario o contrasenia incorrectos.");
                    }
                    break;
                case 0:
                    System.out.println("Hasta luego!");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        } while (opcion != 0);

        leer.close();
    }
}
