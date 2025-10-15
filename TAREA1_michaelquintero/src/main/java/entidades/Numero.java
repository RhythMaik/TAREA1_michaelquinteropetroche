package entidades;

import java.util.ArrayList;
import java.util.List;

public class Numero {
		private long id;               
	    private int orden;
	    private String nombre;
	    private double duracion;
	    private List<Artista> artistas;
	    
	    //Constructor con la lista vacia
		public Numero(long id, int orden, String nombre, double duracion) {
			super();
			this.id = id;
			this.orden = orden;
			this.nombre = nombre;
			this.duracion = duracion;
			this.artistas = new ArrayList<>();
		}
		
		//Constructor con la lista ya creada con anterioridad
		public Numero(long id, int orden, String nombre, double duracion, List<Artista> artistas) {
	        this.id = id;
	        this.orden = orden;
	        this.nombre = nombre;
	        this.duracion = duracion;
	        this.artistas = artistas;
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
		
		//Metodo para añadir artistas despues de haber ya inicializado el constructor
		public void addArtista(Artista artista) {
		      if (!artistas.contains(artista)) {
		    	  artistas.add(artista);
		      }
		}
		
		
	    
	    
}
