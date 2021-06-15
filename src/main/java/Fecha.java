/**
 * Esta clase define objetos que contienen la fecha de la cita (hora/dia/mes/anio
 * @author: Pineapple
 * @version: 10/06/2021
 */
public class Fecha {
    //Campos de la clase
    private int dia;
    private int mes;
    private int año;
    private String hora;

    /**
     * Constructor para la clase fecha
     * @param dia El parámetro dia define el dia de la cita
     * @param mes El parámetro mes define el mes de la cita
     * @param año El parámetro anio define el anio de la cita
     * @param hora El parámetro hora define el hora de la cita
     */
     public Fecha(int dia, int mes, int año, String hora) {
             this.dia = dia;
             this.mes = mes;
             this.año = año;
             this.hora= hora;
    }

    public int get_año(){
        return this.año;
    }

    public int get_mes(){
        return this.mes;
    }

    public int get_dia(){
        return this.dia;
    }

    public String get_hora(){
        return this.hora;
    }
}
