package entidades;

public class Sesion {
	private String nombrePersona; 
	private Perfiles perfil;
	
	//Se inicia a null ya que queremos ir de primeras al menu INVITADO
	public Sesion() {
	    this.nombrePersona = null;
	    this.perfil = null;
	}

	 public String getNombrePersona() {
	    return nombrePersona;
	    }

	 public void setNombrePersona(String nombrePersona) {
	    this.nombrePersona = nombrePersona;
	    }

	 public Perfiles getPerfil() {
	    return perfil;
	    }

	 public void setPerfil(Perfiles perfil) {
	    this.perfil = perfil;
	    }
	}

}
