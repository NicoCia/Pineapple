/** Esta clase define objetos que contienen los datos del medico
 * @author: Pineapple
 * @version: 10/06/2021
 */
public class Medico {
    //Campos de la clase
    private String nombre;
    private String matricula;
    private String dni;
    private String contrasenia;
    private int id;

    /**
     * Constructor para la clase Medico
     * @param nombre Nombre del medico
     * @param matricula Numero de matricula
     * @param dni Numero de dni del medico
     * @param contrasenia contrasenia que debe introducir el medico para identificarse
     * @param id numero de identificacion en el sistema
     */
    public Medico(String nombre, String matricula, String dni,
                  String contrasenia, int id) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.dni = dni;
        this.contrasenia=contrasenia;
        this.id=id;
    }

    /**
     * Método que devuelve el nombre del medico
     * @return el nombre
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Método que devuelve la matricula del medico
     * @return la matricula
     */
    public String getMatricula() {
        return this.matricula;
    }

    /**
     * Método que devuelve el dni del medico
     * @return el dni
     */
    public String getDni() {
        return this.dni;
    }

    /**
     * Método que devuelve la contrasenia del medico
     * @return la contrasenia
     */
    public String getContrasenia() {
        return this.contrasenia;
    }

    /**
     * Método que devuelve el numero de identificacion del medico
     * @return el numero de identificacion
     */
    public int getId() {
        return this.id+1;
    }
}