public class Medico {
	private String nombre;
	private String matricula;
	private String dni;
	private String contraseña;
	private int id;

	public Medico(String nombre, String matricula, String dni, String contraseña, int id) {
		this.nombre = nombre;
		this.matricula = matricula;
		this.dni = dni;
		this.contraseña = contraseña;
		this.id = id;
	}

	public String getNombre(){
		return nombre;
	}

	public String getPass(){
		return contraseña;
	}
	
	public int getId(){
		return id;
	}

	public String toString(){
		return "Doctor: " + nombre + "\nDni: " + dni + "\nMatricula: " + matricula; 
	}
}
