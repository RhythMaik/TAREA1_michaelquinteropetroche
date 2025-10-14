package entidades;

public class Persona {
	private long id;
	private String nombre;
	private String email;
	private String nacionalidad;
	private Credenciales credencial;
	
	public Persona(long id, String nombre, String email, String nacionalidad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.nacionalidad = nacionalidad;
		this.credencial = null;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Credenciales getCredencial() {
		return credencial;
	}

	public void setCredencial(Credenciales credencial) {
		this.credencial = credencial;
	}
	
	 @Override
	    public String toString() {
	        return "Persona:\n" +
	                "Id: " + id +
	                "\nNombre: " + nombre + 
	                "\nEmail: " + email +
	                "\nNacionalidad='" + nacionalidad + 
	                "\nCredencial=" + (credencial != null ? credencial.getNombreUsuario() : "null") +
	                '}';
	    }
	
	// 
}
