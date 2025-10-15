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

    
    //Constructor sin lista inicializada
    public Espectaculo(long id, String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.coordinadores = new ArrayList<>(); 
        this.numeros = new ArrayList<>();  
        }
    
    //Constructor con listas ya creadas con anterioridad
    public Espectaculo(long id, String nombre, LocalDate fechaInicio, LocalDate fechaFin,
            List<Coordinacion> coordinadores, List<Numero> numeros) {
    	this.id = id;
    	this.nombre = nombre;
    	this.fechaInicio = fechaInicio;
    	this.fechaFin = fechaFin;
    	this.coordinadores = coordinadores;
    	this.numeros = numeros;
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
		return "Espectaculo:\nId: " + id + "\nNombre: " + nombre + "\nFechaInicio=" + fechaInicio + "\nFechaFin: "
				+ fechaFin;
	}
    
	
	//Son metodos para añadir Coordinadores o numeros a la lista de los objetos una vez ya estan creados
	public void addCoordinador(Coordinacion c) {
	    if (!coordinadores.contains(c)) coordinadores.add(c);
	}

	public void addNumero(Numero n) {
	    if (!numeros.contains(n)) numeros.add(n);
	}

    
}
