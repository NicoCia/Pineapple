
/** Esta clase define objetos que representa el metodo de pago efectivo
 * @author: Pineapple
 * @version: 12/06/2021
 */
public class Efectivo implements Metodo_de_pago{
    //Campos de la clase
    private int monto;

    /**
     * Constructor para la clase Efectivo
     * @param monto Monto a pagar
     */
    public Efectivo(int monto) {
        this.monto=monto;
    }

    @Override
    public boolean pagar(int monto) {
        return true;
    }
}