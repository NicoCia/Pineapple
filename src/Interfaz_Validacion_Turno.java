import org.json.*;
public interface Interfaz_Validacion_Turno {
    public JSONObject crearTurno(JSONObject json_object);
    public JSONObject consultarTurno(JSONObject json_object);
    public JSONObject consultarTurnosDiponibles();
    public JSONObject consultarTurnosDisponiblesMedico(JSONObject json_object);
    public JSONObject consultarTurnosReservadosMedico(JSONObject json_object);
    public JSONObject cambiarMetodoPago(JSONObject js);

}

