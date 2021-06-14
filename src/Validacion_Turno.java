import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class Validacion_Turno implements Interfaz_Validacion_Turno{
    //Constantes
    private static final String NOMBRE_KEY         =               "nombre";
    private static final String DNI_KEY            =                  "dni";
    private static final String NUMERO_TARJETA_KEY =         "n de Tarjeta";
    private static final String MONTO_KEY          =                "monto";
    private static final String COSTO_KEY          = "costo de la consulta";
    private static final String METODO_DE_PAGO_KEY =       "metodo de pago";
    private static final int    N_TARJETA_LENGHT   =                     20;
    private static final int    N_CODSEG_LENGHT    =                      6;


    private JSONObject      respuesta;
    private Interfaz_Agenda    agenda;

    public Validacion_Turno(){
        agenda    = new Agenda();
        respuesta = new JSONObject();
        respuesta.put("valido","");
    }

    @Override//Devolver valido si o no
    public JSONObject crearTurno(JSONObject json_object) {
        if(!validarNombre       (json_object))   {setResErrNombre();return respuesta;}
        if(!validarDNI          (json_object))   {setResErrDNI()   ;return respuesta;}
        if(!validadMetodoDePago (json_object))   {setResErrMDP()   ;return respuesta;}
        return agenda.crearTurno(json_object);
    }

    private void setResErrNombre(){
        respuesta.put("valido","no");
        respuesta.put("error","Nombre Invalido");
    }

    private void setResErrDNI(){
        respuesta.put("valido","no");
        respuesta.put("error","DNI Invalido");
    }
    private void setResErrMDP(){
        respuesta.put("valido","no");
        respuesta.put("error","Error en metodo de pago");
    }

    private boolean validarNombre(@NotNull JSONObject js){
        String nombre = js.getString(NOMBRE_KEY);
        return stringSoloCompuestoPorletras(nombre);
    }

    private boolean stringSoloCompuestoPorletras(String s){
        if (s == null) {return false;}
        if (Pattern.matches("[a-zA-Z]+",s)) {
            return true;
        }
        return false;
    }

    private boolean validarDNI(JSONObject json_object) {
        String dni = json_object.getString(DNI_KEY);
        return stringSoloCompuestoPorNumeros(dni);
    }

    private boolean stringSoloCompuestoPorNumeros(String s){
        if (s == null) {return false;}
        return Pattern.matches("[0-9]+", s);
    }

    private boolean validadMetodoDePago(@NotNull JSONObject json_object) {
        switch (json_object.getString(METODO_DE_PAGO_KEY)){
            case "Efectivo":
                return validarEfectivo(json_object);
            case "Tarjeta de Debito":
                return validarDebito(json_object);
            case "Tarjeta de Credito":
                return validarCredito(json_object);
            default:
                return false;
        }
    }

    private boolean validarCredito(JSONObject json_object) {
        // validarVencimiento();
        return validarNumeroTarjeta(json_object) && validarCodigoDeSeguridad(json_object);
    }

    private boolean validarDebito(JSONObject json_object) {
        // validarVencimiento();
        return validarNumeroTarjeta(json_object) && validarCodigoDeSeguridad(json_object);
    }

    private boolean validarNumeroTarjeta(@NotNull JSONObject json_object) {
        String n_tarjeta = json_object.getString(NUMERO_TARJETA_KEY);
        return stringSoloCompuestoPorNumeros(n_tarjeta)&&(n_tarjeta.length()==N_TARJETA_LENGHT);
    }

    private boolean validarCodigoDeSeguridad(@NotNull JSONObject json_object){
        String n_tarjeta = json_object.getString(NUMERO_TARJETA_KEY);
        return stringSoloCompuestoPorNumeros(n_tarjeta)&&(n_tarjeta.length()==N_CODSEG_LENGHT);
    }

    private boolean validarEfectivo(@NotNull JSONObject json_object) {
        String s = json_object.getString(MONTO_KEY);
        if(stringSoloCompuestoPorNumeros(s)&&montoSuficiente(json_object)){
            return true;
        }
        return false;
    }

    private boolean montoSuficiente(@NotNull JSONObject json_object){
        int monto = Integer.parseInt(json_object.getString(MONTO_KEY));
        if(monto>=json_object.getInt(COSTO_KEY)){
            return true;
        }
        return false;
    }

    @Override
    public JSONObject consultarTurno(JSONObject json_object) {
        if(!validarDNI(json_object)) {setResErrDNI();return respuesta;}
        return agenda.consultarTurno(json_object);
    }

    @Override
    public JSONObject consultarTurnosDiponibles() {
        return agenda.consultarTurnosDiponibles();
    }

    @Override
    public JSONObject consultarTurnosDisponiblesMedico(JSONObject json_object) {
        return agenda.consultarTurnosDisponiblesMedico(json_object);
    }

    @Override
    public JSONObject consultarTurnosReservadosMedico(JSONObject json_object) {
        return agenda.consultarTurnosReservadosMedico(json_object);
    }





}
