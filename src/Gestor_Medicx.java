import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Gestor_Medicx implements Interfaz_Gestor_Medicx{

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

    private static final String PATH                    = "/home/nico/Facultad/Isoft/Pineapple/src/medicos.txt";

    private List<Medico> medicos;
    private BufferedReader br;
    private File file;

    public Gestor_Medicx() throws Exception{
        medicos = new ArrayList<Medico>();
        file = new File(PATH);
        br = new BufferedReader(new FileReader(file));
        levantarMedicos();
    }
    //(String nombre, String matricula, String dni,
    //                  String contrasenia, int id)
    public void crearMedicx(JSONObject json_object){
        String nombre       = json_object.getString(NOMBRE_MEDICX_KEY);
        String matricula    = json_object.getString(MATRICULA_MEDICX_KEY);
        String dni          = json_object.getString(DNI_MEDIX_KEY);
        String contrasenia  = json_object.getString(CONTRASENIA_MEDICX_KEY);
        int id_medico       = Integer.parseInt(json_object.getString(ID_MEDICX_KEY));
        Medico medico       = new Medico(nombre,matricula,dni,contrasenia,id_medico);

        medicos.add(medico);
    }

    @Override
    public JSONObject iniciarSesion(JSONObject json_object) {
        String matricula = json_object.getString(MATRICULA_MEDICX_KEY);
        String contrasenia = json_object.getString(CONTRASENIA_MEDICX_KEY);
        for(Medico medico : medicos){
            if(medico.getMatricula().equals(matricula)&&medico.getContrasenia().equals(contrasenia)){
                return logueoCorrecto();
            }
        }
        return logueoIncorrecto();
    }

    @Override
    public JSONObject getNomConID(JSONObject json_object) {
        int id = Integer.parseInt(json_object.getString(ID_MEDICX_KEY));
        for(Medico medico : medicos){
            if(medico.getId()==id){
                json_object.put(NOMBRE_MEDICX_KEY,medico.getNombre());
                return json_object;
            }
        }
        return ErrGetNomID();
    }

    private JSONObject ErrGetNomID() {
        JSONObject respuesta = new JSONObject();
        respuesta.put(VALIDO_KEY,"no");
        respuesta.put(ERROR_KEY,"no hay medico con ese id");
        return respuesta;
    }

    @Override
    public JSONObject getIDConNom(JSONObject json_object) {
        String nombre = json_object.getString(NOMBRE_MEDICX_KEY);
        for(Medico medico : medicos){
            if(medico.getNombre().equals(nombre)){
                json_object.put(ID_MEDICX_KEY,String.format("%d",medico.getId()));
                return json_object;
            }
        }
        return ErrGetIDNom();
    }

    public JSONObject ErrGetIDNom() {
        JSONObject respuesta = new JSONObject();
        respuesta.put(VALIDO_KEY,"no");
        respuesta.put(ERROR_KEY,"no hay medico con ese nombre");
        return respuesta;
    }

    private JSONObject logueoCorrecto(){
        JSONObject respuesta = new JSONObject();
        respuesta.put(VALIDO_KEY,"si");
        return respuesta;
    }

    private JSONObject logueoIncorrecto(){
        JSONObject respuesta = new JSONObject();
        respuesta.put(VALIDO_KEY,"no");
        respuesta.put(ERROR_KEY,"matricula o contrasenia invalidas");
        return respuesta;
    }

    private void levantarMedicos() throws IOException {
        String linea, nombre, matricula, dni, contrasenia;
        int id;

        while ((linea = br.readLine()) != null) {
            int aux = 0;

            aux=linea.indexOf(',');
            nombre = linea.substring(0,aux);
            linea = linea.substring(++aux);

            aux = linea.indexOf(',');
            matricula=linea.substring(0,aux);
            linea = linea.substring(++aux);

            aux = linea.indexOf(',');
            dni=linea.substring(0,aux);
            linea = linea.substring(++aux);

            aux=linea.indexOf(',');
            contrasenia=linea.substring(0,aux);
            linea = linea.substring(++aux);

            id=Integer.parseInt(linea.substring(0,linea.length()));

            Medico medico = new Medico(nombre,matricula,dni,contrasenia,id);
            medicos.add(medico);
        }
    }
}