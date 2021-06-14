import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Agenda implements Interfaz_Agenda{

    private static final String NOMBRE_KEY              =               "nombre";
    private static final String DNI_KEY                 =                  "dni";
    private static final String NUMERO_TARJETA_KEY      =         "n de Tarjeta";
    private static final String MONTO_KEY               =                "monto";
    private static final String COSTO_KEY               = "costo de la consulta";
    private static final String METODO_DE_PAGO_KEY      =       "metodo de pago";
    private static final String HORA_KEY                =                 "hora";
    private static final String NOMBRE_MEDICX_KEY       =    "nombre del medico";
    private static final String N_CUOTAS_KEY            =          "n de cuotas";
    private static final String EFECTIVO                =             "efectivo";
    private static final String TARJETA_DEBITO          =    "tarjeta de debito";
    private static final String TARJETA_CREDITO         =   "tarjeta de credito";
    private static final String VENCIMIENTO_TARJETA_KEY =          "vencimiento";
    private static final String CODSEG_TARJETA_KEY      =  "codigo de seguridad";
    private static final String ID_MEDICX_KEY           =            "id medico";

    private static final int    N_TARJETA_LENGHT   =                          20;
    private static final int    N_CODSEG_LENGHT    =                           6;




    //Campos
    private List<Turno>   turnos;
    private JSONObject respuesta;

    //Constructor
    public Agenda(){
        turnos    = new ArrayList<Turno>();
        respuesta = new JSONObject();
    }

    //Metodos
    /*
    public ArrayList<Turno> consultar_turno_paciente(int dni){
        List turnos_del_paciente = new ArrayList<>();
        for (Turno turno : turnos){
            if(turno.get_paciente().get_dni() == dni){
                turnos_del_paciente.add(turno);
            }
        }
        return turnos_del_paciente;
    }
*/
    //TODO
    private boolean turno_disponible(String hora, int id_medico){
        for(Turno turno : turnos){
            if(turno.getFecha().get_hora().equals(hora) && turno.getIdMedico() == id_medico){
                return false;
            }
        }
        return true;
    }

    @Override
    public JSONObject crearTurno(JSONObject js) {
        String hora = js.getString(HORA_KEY);
        int id_medico = Integer.parseInt(js.getString(ID_MEDICX_KEY));

        if(turno_disponible(hora,id_medico)){
            agregarTurno(js,hora,id_medico);

            respuesta.put("valido","si");
            return respuesta;
        }
        respuesta.put("valido","no");
        respuesta.put("error","turno no disponible");
        return null;
    }

    private void agregarTurno(JSONObject js,String hora, int id_medico){
        LocalDate fecha_actual = java.time.LocalDate.now();
        Paciente paciente = new Paciente(js.getString(NOMBRE_KEY),js.getString(DNI_KEY));

        int dia = fecha_actual.getDayOfMonth();
        int mes = fecha_actual.getMonthValue();
        int anio= fecha_actual.getYear();
        Fecha fecha = new Fecha(dia, mes, anio, hora);

        int costo = Integer.parseInt(js.getString(COSTO_KEY));
        Turno turno = new Turno(paciente,fecha,id_medico,crearMetodoDePago(js),costo);

        turnos.add(turno);
    }

    private Metodo_de_pago crearMetodoDePago(JSONObject js){
        Metodo_de_pago metodo_de_pago;
        switch (js.getString(METODO_DE_PAGO_KEY)){
            case EFECTIVO:
                int monto = Integer.parseInt(js.getString(MONTO_KEY));
                metodo_de_pago = new Efectivo(monto);
                break;

            case TARJETA_DEBITO:
                long numeroD        = Long.parseLong(js.getString(NUMERO_TARJETA_KEY));
                String vencimientoD = js.getString(VENCIMIENTO_TARJETA_KEY);
                int codigoD         = Integer.parseInt(js.getString(CODSEG_TARJETA_KEY));
                metodo_de_pago      = new Tarjeta_debito(numeroD,vencimientoD,codigoD);
                break;
            case TARJETA_CREDITO:
                long numeroC        = Long.parseLong(js.getString(NUMERO_TARJETA_KEY));
                String vencimientoC = js.getString(VENCIMIENTO_TARJETA_KEY);
                int codigoC         = Integer.parseInt(js.getString(CODSEG_TARJETA_KEY));
                int n_cuotas        = Integer.parseInt(js.getString(N_CUOTAS_KEY));
                metodo_de_pago      = new Tarjeta_credito(numeroC,vencimientoC,codigoC,n_cuotas);
                break;
            default:
                metodo_de_pago = null;
                break;
        }
        return metodo_de_pago;
    }

    @Override
    public JSONObject consultarTurno(JSONObject json_object) {
        return null;
    }

    @Override
    public JSONObject consultarTurnosDiponibles() {
        return null;
    }

    @Override
    public JSONObject consultarTurnosDisponiblesMedico(JSONObject json_object) {
        return null;
    }

    @Override
    public JSONObject consultarTurnosReservadosMedico(JSONObject json_object) {
        return null;
    }
}