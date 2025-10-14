package entidades;

import java.time.LocalDate;

public class Coordinacion extends Persona{
	private long idCoord;
	private boolean senior;
	private LocalDate seniorFecha;
	
	public Coordinacion(long id, String nombre, String email, String nacionalidad, long idCoord, boolean senior,
		LocalDate seniorFecha) {
		super(id, nombre, email, nacionalidad);
		this.idCoord = idCoord;
		this.senior = senior;
		this.seniorFecha = seniorFecha;
	}

	public long getIdCoord() {
		return idCoord;
	}

	public void setIdCoord(long idCoord) {
		this.idCoord = idCoord;
	}

	public boolean isSenior() {
		return senior;
	}

	public void setSenior(boolean senior) {
		this.senior = senior;
	}

	public LocalDate getSeniorFecha() {
		return seniorFecha;
	}

	public void setSeniorFecha(LocalDate seniorFecha) {
		this.seniorFecha = seniorFecha;
	}

	@Override
	public String toString() {
		
		return super.toString()+ "\nCoordinador:\nIdCoord: " + idCoord + "\nSenior: " + senior + "\nSeniorFecha: " + seniorFecha;
	}
	
	
	
	
}
