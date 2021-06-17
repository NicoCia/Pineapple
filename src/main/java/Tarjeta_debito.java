/** Esta clase define objetos que contienen los datos de las tarjetas de debito asoiadas
 * @author: Pineapple
 * @version: 12/06/2021
 */
public class Tarjeta_debito implements Metodo_de_pago {
    //Campos de la clase
    private long numero;
    private String vencimiento;
    private int codigo;

    /**
     * Constructor para la clase Tarjeta_debito
     * @param numero Numero de la tarjeta de debito
     * @param vencimiento Fecha a partir de la cual la tarjeta ya no es valida
     * @param codigo Codigo de seguridad de la tarjeta de credito
     */
    public Tarjeta_debito(long numero, String vencimiento, int codigo) {
        this.numero=numero;
        this.vencimiento=vencimiento;
        this.codigo=codigo;
    }

    @Override
    public boolean pagar(int monto) {
        return true;
    }
}