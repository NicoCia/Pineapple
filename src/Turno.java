
/** Esta clase define objetos que contienen los datos del turno
 * @author: Pineapple
 * @version: 12/06/2021
 */
public class Turno {
    //Campos de la clase
    private Paciente paciente;
    private Fecha fecha;
    private int id_medico;
    private Metodo_de_pago forma_pago;
    private int precio;

    /**
     * Constructor para la clase Turno
     * @param paciente Paciente al que pertenece el turno
     * @param fecha Fecha a la cual esta asignado el turno
     * @param id_medico Medico que atendera el turno
     * @param forma_pago Como se realizara el pago
     * @param precio Cuanto cuesta la consulta
     */
    public Turno(Paciente paciente, Fecha fecha, int id_medico,
                 Metodo_de_pago forma_pago,int precio) {
        this.paciente=paciente;
        this.fecha=fecha;
        this.id_medico=id_medico;
        this.forma_pago=forma_pago;
        this.precio=precio;
    }

    /**
     * Método que configura el metodo de pago
     */
    public void setMetodoPago(Metodo_de_pago metodo_de_pago) {
        forma_pago = metodo_de_pago;
    }

    /**
     * Método que devuelve el metodo de pago que tiene asignado el turno
     * @return el id del medico
     */
    public Metodo_de_pago getMetodoDePago() {
        return forma_pago;
    }

    /**
     * Método que devuelve el numero de identificacion del medico
     * @return el id del medico
     */
    public int getIdMedico() {
        return id_medico;
    }

    /**
     * Método que devuelve el numero de identificacion del medico
     * @return el id del medico
     */
    public Fecha getFecha() {
        return fecha;
    }

    /**
     * Método que devuelve el paciente que tiene asignado el turno
     * @return el id del medico
     */
    public Paciente getPaciente() {
        return paciente;
    }
}