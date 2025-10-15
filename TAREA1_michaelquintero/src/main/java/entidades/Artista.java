package entidades;

import java.util.ArrayList;
import java.util.List;

public class Artista extends Persona{
	private long idArtista;
	private String apodo;
	private List<Especialidad> especialidades;
	
	//Constructor con lista vacía
    public Artista(long id, String nombre, String email, String nacionalidad, long idArtista, String apodo) {
        super(id, nombre, email, nacionalidad);
        this.idArtista = idArtista;
        this.apodo = apodo;
        this.especialidades = new ArrayList<>(); 
    }

    //Constructor con lista ya creada
    public Artista(long id, String nombre, String email, String nacionalidad, long idArtista,
                   String apodo, List<Especialidad> especialidades) {
        super(id, nombre, email, nacionalidad);
        this.idArtista = idArtista;
        this.apodo = apodo;
        this.especialidades = new ArrayList<>();
    }

	public long getIdArtista() {
		return idArtista;
	}

	public void setIdArtista(long idArtista) {
		this.idArtista = idArtista;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public List<Especialidad> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidad> especialidades) {
		this.especialidades = especialidades;
	}

	@Override
	public String toString() {
		return super.toString()+"\nArtista\nIdArtista: " + idArtista + "\nApodo: " + apodo + "\nEspecialidades: " + especialidades;
	}
	
	//Añadir especialidad a posteriori
    public void addEspecialidad(Especialidad especialidad) {
        if (!especialidades.contains(especialidad)) {
            especialidades.add(especialidad);
        }
    }

	
	
	
}
