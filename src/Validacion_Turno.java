import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class Validacion_Turno implements Interfaz_Validacion_Turno{
    //Constantes
    private static String NOMBRE_KEY         =               "nombre";
    private static String DNI_KEY            =                  "dni";
    private static String NUMERO_TARJETA_KEY =         "n de Tarjeta";
    private static String MONTO_KEY          =                "monto";
    private static String COSTO_KEY          = "costo de la consulta";
    private static int    N_TARJETA_LENGHT   =                     20;
    private static int    N_CODSEG_LENGHT    =                      6;

    @Override//Devolver valido si o no
    public JSONObject crearTurno(JSONObject json_object) {
        validarNombre(json_object);
        validarDNI(json_object);
        //validarHorario(json_object);
        validadMetodoDePago(json_object);
        return null;
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
        if (Pattern.matches("[0-9]+",s)) {
            return true;
        }
        return false;
    }

    private boolean validadMetodoDePago(JSONObject json_object) {
        switch (json_object.getString("Metodo De Pago")){
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

    private boolean validarNumeroTarjeta(JSONObject json_object) {
        String n_tarjeta = json_object.getString(NUMERO_TARJETA_KEY);
        return stringSoloCompuestoPorNumeros(n_tarjeta)&&(n_tarjeta.length()==N_TARJETA_LENGHT);
    }

    private boolean validarCodigoDeSeguridad(JSONObject json_object){
        String n_tarjeta = json_object.getString(NUMERO_TARJETA_KEY);
        return stringSoloCompuestoPorNumeros(n_tarjeta)&&(n_tarjeta.length()==N_CODSEG_LENGHT);
    }

    private boolean validarEfectivo(JSONObject jsonObject) {
        String s = jsonObject.getString(MONTO_KEY);
        if(stringSoloCompuestoPorNumeros(s)&&montoSuficiente(jsonObject)){
            return true;
        }
        return false;
    }

    private boolean montoSuficiente(JSONObject jsonObject){
        int monto = Integer.parseInt(jsonObject.getString(MONTO_KEY));
        if(monto>=jsonObject.getInt(COSTO_KEY)){
            return true;
        }
        return false;
    }

    @Override
    public JSONObject consultarTurno(JSONObject json_object) {
        validarDNI(json_object);
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
