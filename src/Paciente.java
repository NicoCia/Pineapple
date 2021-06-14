/** Esta clase define objetos que contienen los datos del paciente
 * @author: Pineapple
 * @version: 10/06/2021
 */
public class Paciente {
    //Campos de la clase
    private String nombre;
    private String dni;

    /**
     * Constructor para la clase Paciente
     * @param nombre Nombre del paciente
     * @param dni Numero de dni del paciente
     */
    public Paciente(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
    }

    /**
     * Método que devuelve el nombre del paciente
     * @return el nombre
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Método que devuelve el dni del paciente
     * @return el dni
     */
    public String getDni() {
        return this.dni;
    }

}