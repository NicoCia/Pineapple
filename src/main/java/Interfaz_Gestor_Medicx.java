import org.json.JSONObject;

import java.util.ArrayList;

public interface Interfaz_Gestor_Medicx {
    public JSONObject iniciarSesion(JSONObject json_object);
    public JSONObject getNomConID(JSONObject json_object);
    public JSONObject getIDConNom(JSONObject json_object);
    public void crearMedicx(JSONObject json_object);
}
