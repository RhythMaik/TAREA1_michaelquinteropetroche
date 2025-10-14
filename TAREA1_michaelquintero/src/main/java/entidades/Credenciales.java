package entidades;

public class Credenciales {
	private long id;
	private String nombreUsuario;
	private String contraseña;
	private Perfiles perfil;
	
	public Credenciales(long id, String nombreUsuario, String contraseña, Perfiles perfil) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.contraseña = contraseña;
		this.perfil = perfil;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public Perfiles getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfiles perfil) {
		this.perfil = perfil;
	}
	
	 @Override
	    public String toString() {
	        return "Credenciales: \n" +"Id=" + id +"\nNombreUsuario='" + nombreUsuario + "\nPerfil=" + perfil;
	    }
	
}
