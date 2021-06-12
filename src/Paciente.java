public class Paciente {
	private String nombre;
	private String dni;

	public Paciente(String nombre, String dni) {
		this.nombre = nombre;
		this.dni = dni;
	}

	public String getDni() { 
		return dni;
	}
	
	public String toString(){
		return "Paciente: " + nombre + "\nDni: " + dni; 
	}
}
