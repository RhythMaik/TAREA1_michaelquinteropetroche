package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 *@author Michael Quintero Petroche 
 */
public class Espectaculo implements Serializable {
	private long id;
	private String nombre;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;

	// Nuevo modelo con coordinador “único”
	private long idCoordinador;         // id de persona que coordina
	private String nombreCoordinador;   // nombre de la persona coordinadora

	private List<Numero> numeros;

	// Lista antigua de nombres de coordinadores (ya casi no se usa, pero la dejamos por compatibilidad)
	private List<String> coordinadoresNombres;

	// ==== CONSTRUCTORES ====

	// Constructor “nuevo” completo
	public Espectaculo(long id, String nombre, LocalDate fechaInicio, LocalDate fechaFin, long idCoordinador) {
		this.id = id;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.idCoordinador = idCoordinador;
		this.nombreCoordinador = null;
		this.numeros = new ArrayList<>();
		this.coordinadoresNombres = new ArrayList<>();
	}

	// Constructor antiguo (4 parámetros): lo siguen usando Main, ServicioEspectaculos y EspectaculoDAO
	public Espectaculo(long id, String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
		this(id, nombre, fechaInicio, fechaFin, 0L); // sin coordinador asignado aún
	}

	// ==== GETTERS / SETTERS ====

	public long getIdCoordinador() {
		return idCoordinador;
	}

	public void setIdCoordinador(long idCoordinador) {
		this.idCoordinador = idCoordinador;
	}

	public String getNombreCoordinador() {
		return nombreCoordinador;
	}

	public void setNombreCoordinador(String nombreCoordinador) {
		this.nombreCoordinador = nombreCoordinador;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public List<String> getCoordinadoresNombres() {
		return coordinadoresNombres;
	}

	public void setCoordinadoresNombres(List<String> coordinadoresNombres) {
		this.coordinadoresNombres = coordinadoresNombres;
	}

	public List<Numero> getNumeros() {
		return numeros;
	}

	public void setNumeros(List<Numero> numeros) {
		this.numeros = numeros;
	}

	// Método antiguo para añadir nombres a la lista (por compatibilidad)
	public boolean addCoordinadorNombre(String nombre) {
		if (nombre == null)
			return false;
		String nom = nombre.trim();
		if (nom.isEmpty())
			return false;
		for (String existente : coordinadoresNombres) {
			if (existente.equalsIgnoreCase(nom))
				return false;
		}
		coordinadoresNombres.add(nom);
		return true;
	}

	@Override
	public String toString() {
		String mostrar = "Espectaculo:\nId: " + id +
				"\nNombre: " + nombre +
				"\nFechaInicio: " + fechaInicio +
				"\nFechaFin: " + fechaFin;

		if (nombreCoordinador != null) {
			mostrar += "\nCoordinador: " + nombreCoordinador;
		}

		return mostrar;
	}
}
