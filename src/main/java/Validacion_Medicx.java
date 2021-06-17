import org.json.JSONObject;

import java.util.regex.Pattern;


public class Validacion_Medicx implements Interfaz_Validacion_Medicx{

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
    private static final String VALIDO_KEY              =               "valido";
    private static final String ERROR_KEY               =                "error";
    private static final String TURNOS_KEY              =               "turnos";
    private static final String CONTRASENIA_MEDICX_KEY  =          "contrasenia";
    private static final String MATRICULA_MEDICX_KEY    =            "matricula";
    private static final String DNI_MEDIX_KEY           =           "dni medico";

    private Gestor_Medicx gestor_medicx;

    public Validacion_Medicx(){
        try {
            gestor_medicx = new Gestor_Medicx();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validarMatricula(JSONObject json_object){
        String matricula = json_object.getString(MATRICULA_MEDICX_KEY);
        return stringSoloCompuestoPorNumeros(matricula);
    }

    private boolean stringSoloCompuestoPorNumeros(String s){
        if (s == null) {return false;}
        return Pattern.matches("[0-9]+", s);
    }

    private JSONObject ErrValMat(){
        JSONObject respuesta = new JSONObject();
        respuesta.put(VALIDO_KEY,"no");
        respuesta.put(ERROR_KEY,"matricula invalida");
        return respuesta;
    }

    @Override
    public JSONObject iniciarSesion(JSONObject json_object) {
        if(!validarMatricula(json_object)){return ErrValMat();}
        return gestor_medicx.iniciarSesion(json_object);
    }

    @Override
    public JSONObject getNomConID(JSONObject json_object) {
        if(!validarID(json_object)){return ErrNomID();}
        return gestor_medicx.getNomConID(json_object);
    }

    private boolean validarNombre(JSONObject json_object){
        String nombre = json_object.getString(NOMBRE_MEDICX_KEY);
        return stringSoloCompuestoPorletras(nombre);
    }

    private boolean stringSoloCompuestoPorletras(String s){
        if (s == null) {return false;}
        return Pattern.matches("[a-z A-Z]+", s);
    }

    private JSONObject ErrNomID(){
        JSONObject respuesta = new JSONObject();
        respuesta.put(VALIDO_KEY,"no");
        respuesta.put(ERROR_KEY,"id invalido");
        return respuesta;
    }

    @Override
    public JSONObject getIDConNom(JSONObject json_object) {
        if(!validarNombre(json_object)){return ErrIDNom();}
        return gestor_medicx.getIDConNom(json_object);
    }

    private boolean validarID(JSONObject json_object){
        String id = json_object.getString(ID_MEDICX_KEY);
        return stringSoloCompuestoPorNumeros(id);
    }

    private JSONObject ErrIDNom(){
        JSONObject respuesta = new JSONObject();
        respuesta.put(VALIDO_KEY,"no");
        respuesta.put(ERROR_KEY,"nombre invalido");
        return respuesta;
    }


    @Override
    public void crearMedicx(JSONObject json_object) {

    }
}
