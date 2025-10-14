package entidades;

import java.util.ArrayList;
import java.util.List;

public class Numero {
		private long id;               
	    private int orden;
	    private String nombre;
	    private double duracion;
	    private List<Artista> artistas;
	    
		public Numero(long id, int orden, String nombre, double duracion, List<Artista> artistas) {
			super();
			this.id = id;
			this.orden = orden;
			this.nombre = nombre;
			this.duracion = duracion;
			this.artistas = new ArrayList<>();
		}
		
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public int getOrden() {
			return orden;
		}
		public void setOrden(int orden) {
			this.orden = orden;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public double getDuracion() {
			return duracion;
		}
		public void setDuracion(double duracion) {
			this.duracion = duracion;
		}
		public List<Artista> getArtistas() {
			return artistas;
		}
		public void setArtistas(List<Artista> artistas) {
			this.artistas = artistas;
		}

		@Override
		public String toString() {
			return "Numero: \nId: " + id + "\nOrden: " + orden + "\nNombre: " + nombre + "\nDuracion: " + duracion
					+ "\nArtistas=" + artistas;
		}
		
		
		
	    
	    
}
