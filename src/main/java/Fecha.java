
public class Fecha {
    private int dia;
    private int mes;
    private int año;
    private String hora;


    public Fecha(int dia, int mes, int año, String hora){
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
