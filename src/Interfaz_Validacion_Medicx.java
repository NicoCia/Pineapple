import org.json.*;
public interface Interfaz_Validacion_Medicx {
    public JSONObject iniciarSesion(JSONObject json_object);
    public JSONObject getNomConID(JSONObject json_object)  ;
    public JSONObject getIDConNom(JSONObject json_object)  ;
    public void       crearMedicx(JSONObject json_object)  ;
}
