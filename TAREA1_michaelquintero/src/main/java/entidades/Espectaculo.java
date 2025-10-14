package entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Espectaculo {
	private long id;                          
    private String nombre;                   
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<Coordinacion> coordinadores; 
    private List<Numero> numeros;              

    public Espectaculo(long id, String nombre, LocalDate fechaInicio, LocalDate fechaFin,
                       List<Coordinacion> coordinadores, List<Numero> numeros) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.coordinadores = new ArrayList<>(); 
        this.numeros = new ArrayList<>();  
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

	public List<Coordinacion> getCoordinadores() {
		return coordinadores;
	}

	public void setCoordinadores(List<Coordinacion> coordinadores) {
		this.coordinadores = coordinadores;
	}

	public List<Numero> getNumeros() {
		return numeros;
	}

	public void setNumeros(List<Numero> numeros) {
		this.numeros = numeros;
	}

	@Override
	public String toString() {
		return "Espectaculo:\nId: " + id + "\nNombre: " + nombre + "/nFechaInicio=" + fechaInicio + "/nFechaFin: "
				+ fechaFin;
	}
    
    
}
