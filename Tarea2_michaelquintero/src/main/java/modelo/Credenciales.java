package modelo;
/*
 *@author Michael Quintero Petroche 
 */
public class Credenciales {
    private long idPersona;
    private String nombreUsuario;
    private String contraseña;      
    private String email;
    private String nombrePersona;   
    private String nacionalidad;
    private Perfiles perfil;       

    public Credenciales(long idPersona, String nombreUsuario, String contraseña,String email, String nombrePersona, String nacionalidad,Perfiles perfil) {
        this.idPersona = idPersona;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.email = email;
        this.nombrePersona = nombrePersona;
        this.nacionalidad = nacionalidad;
        this.perfil = perfil;
    }

    public long getIdPersona() {
    	return idPersona; 
    	}
    public void setIdPersona(long idPersona) {
    	this.idPersona = idPersona; 
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

    public String getEmail() {
    	return email; 
    	}
    public void setEmail(String email) { 
    	this.email = email;
    	}

    public String getNombrePersona() {
    	return nombrePersona; }
    public void setNombrePersona(String nombrePersona) { 
    	this.nombrePersona = nombrePersona; 
    	}

    public String getNacionalidad() { 
    	return nacionalidad; 
    }
    public void setNacionalidad(String nacionalidad) { 
    	this.nacionalidad = nacionalidad;
    }

    public Perfiles getPerfil() { 
    	return perfil;
    }
    public void setPerfil(Perfiles perfil) { 
    	this.perfil = perfil;
    }

    @Override
    public String toString() {
        return "Credenciales:\n"+"Id: "+ idPersona +"\nUsuario: "+nombreUsuario+"\nEmail: " + email +"\nNombre: "+nombrePersona+"\nNacionalidad: "+nacionalidad+"\nPerfil: "+perfil;

    }
}
