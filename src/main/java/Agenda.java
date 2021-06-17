import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.BitSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Agenda implements Interfaz_Agenda, Subject{

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

    private static final int    INICIO_JORNADA_LABORAL  =                                           8;
    private static final int    FIN_JORNADA_LABORAL     =                                          16;
    private static final int    FRANJA_HORARIA          =  FIN_JORNADA_LABORAL-INICIO_JORNADA_LABORAL;
    private static final int    TIEMPO_POR_TURNO        =                                          30;
    private static final double TURNOS_POR_HORA         =                       60.0/TIEMPO_POR_TURNO;
    private static final int    N_TURNOS                = (int) ((int)FRANJA_HORARIA*TURNOS_POR_HORA);
    private static final int    N_MEDICOS               =                                           6;


    private static final String PATH                    = System.getProperty("user.dir")+ "/data/turnos.txt";

    //Campos
    private final List<Turno>   turnos;
    private final List<Observer> observers;
    private final BufferedReader br;
    private final File file;
    private boolean flag_levantando_file;

    //Constructor
    public Agenda() throws Exception{
        turnos    = new ArrayList<>();
        observers = new ArrayList<>();
        new File(System.getProperty("user.dir")+ "/data").mkdir();
        file = new File(PATH);
        file.createNewFile();
        br = new BufferedReader(new FileReader(file));
        levantarTurnos();
    }

    //TODO
    private boolean turno_disponible(String hora, int id_medico){
        for(Turno turno : turnos){
            if(turno.getFecha().get_hora().equals(hora) && (turno.getIdMedico() == id_medico)){
                return false;
            }
        }
        return true;
    }

    @Override
    public JSONObject crearTurno(JSONObject js) {
        JSONObject respuesta = new JSONObject();
        String hora = js.getString(HORA_KEY);
        int id_medico = Integer.parseInt(js.getString(ID_MEDICX_KEY));

        if(turno_disponible(hora,id_medico)){
            agregarTurno(js,hora,id_medico);

            respuesta.put(VALIDO_KEY,"si");
            notifyObservers();
            return respuesta;
        }
        respuesta.put(VALIDO_KEY,"no");
        respuesta.put("error","horario no disponible");
        return respuesta;
    }

    private void agregarTurno(JSONObject js,String hora, int id_medico){
        LocalDate fecha_actual = java.time.LocalDate.now();
        Paciente paciente = new Paciente(js.getString(NOMBRE_KEY),js.getString(DNI_KEY));

        int dia = fecha_actual.getDayOfMonth();
        int mes = fecha_actual.getMonthValue();
        int anio= fecha_actual.getYear();
        Fecha fecha = new Fecha(dia, mes, anio, hora);

        Metodo_de_pago mdp = crearMetodoDePago(js);
        int costo = Integer.parseInt(js.getString(COSTO_KEY));
        Turno turno = new Turno(paciente,fecha,id_medico,mdp,costo);

        turnos.add(turno);

        if(!flag_levantando_file){
            escribirTurnoTxt(js,dia,mes,anio);
        }
    }

    private Metodo_de_pago crearMetodoDePago(JSONObject js){
        Metodo_de_pago metodo_de_pago;
        switch (js.getString(METODO_DE_PAGO_KEY)) {
            case EFECTIVO:
                int monto = Integer.parseInt(js.getString(MONTO_KEY));
                metodo_de_pago = new Efectivo(monto);
                break;
            case TARJETA_DEBITO:
                long numeroD = Long.parseLong(js.getString(NUMERO_TARJETA_KEY));
                String vencimientoD = js.getString(VENCIMIENTO_TARJETA_KEY);
                int codigoD = Integer.parseInt(js.getString(CODSEG_TARJETA_KEY));
                metodo_de_pago = new Tarjeta_debito(numeroD, vencimientoD, codigoD);
                break;
            case TARJETA_CREDITO:
                long numeroC = Long.parseLong(js.getString(NUMERO_TARJETA_KEY));
                String vencimientoC = js.getString(VENCIMIENTO_TARJETA_KEY);
                int codigoC = Integer.parseInt(js.getString(CODSEG_TARJETA_KEY));
                int n_cuotas = Integer.parseInt(js.getString(N_CUOTAS_KEY));
                metodo_de_pago = new Tarjeta_credito(numeroC, vencimientoC, codigoC, n_cuotas);
                break;
            default: metodo_de_pago = null;
        }
        return metodo_de_pago;
    }

    @Override
    public JSONObject consultarTurno(JSONObject json_object) {
        JSONObject respuesta = new JSONObject();
        String dni = json_object.getString(DNI_KEY);

        for(Turno turno : turnos){
            if(turno.getPaciente().getDni().equals(dni)){
                respuesta = respuestaConsultarTurno(turno);
                return respuesta;
            }
        }
        respuesta.put(VALIDO_KEY,"no");
        respuesta.put(ERROR_KEY,"No se encontraron turnos para el paciente solicitado.");
        return respuesta;
    }


    private JSONObject respuestaConsultarTurno(Turno turno) {
        JSONObject respuesta = new JSONObject();
        respuesta.put(VALIDO_KEY,"si");
        respuesta.put(NOMBRE_KEY, turno.getPaciente().getNombre());
        respuesta.put(ID_MEDICX_KEY,""+turno.getIdMedico());
        respuesta.put(HORA_KEY,turno.getFecha().get_hora());
        if (turno.getMetodoDePago() instanceof Efectivo){
            respuesta.put(METODO_DE_PAGO_KEY,EFECTIVO);
        }
        else if (turno.getMetodoDePago() instanceof Tarjeta_debito){
            respuesta.put(METODO_DE_PAGO_KEY,TARJETA_DEBITO);
        }
        else if (turno.getMetodoDePago() instanceof Tarjeta_credito){
            respuesta.put(METODO_DE_PAGO_KEY,TARJETA_CREDITO);
        }
        return respuesta;
    }
    /*
    consultar turno disponibles:
    {
        valido:
        JSON_array medicos:[
            nombre_medico1: arrayList_horarios[]
            nombre_medico2: arrayList_horarios[]
        ]
    }
    /*
     */
    @Override
    public JSONObject consultarTurnosDiponibles() {
        BitSet[] bs = turnosReservadosEnBitsets();
        JSONObject respuesta = new JSONObject();
        JSONArray array = new JSONArray();

        for (int i=0;i<N_MEDICOS;i++){
            ArrayList<String> list = new ArrayList<>();
            for(int j=0;j<N_TURNOS;j++){
                if(!bs[i].get(j)){
                    list.add(horaConIJ(j));
                }
            }
            JSONObject elemento = new JSONObject();
            elemento.put(String.format("%d",i),list);
            array.put(i,elemento);
        }
        respuesta.put(VALIDO_KEY,"si");
        respuesta.put("medicos",array);
        return respuesta;
    }

    private String horaConIJ(int j){
        int hora =   (int)(j/TURNOS_POR_HORA)+INICIO_JORNADA_LABORAL;
        int min  =  (j%(int)TURNOS_POR_HORA)*TIEMPO_POR_TURNO;
        return String.format("%02d:%02d",hora,min);
    }

    @Override
    public JSONObject consultarTurnosDisponiblesMedico(JSONObject json_object) {
        BitSet[] bs = turnosReservadosEnBitsets();
        JSONObject respuesta = new JSONObject();
        JSONArray array = new JSONArray();

        int i = Integer.parseInt(json_object.getString(ID_MEDICX_KEY));
        for(int j=0;j<N_TURNOS;j++){
            if(!bs[i].get(j)){
                JSONObject elemento = new JSONObject();
                elemento.put(HORA_KEY,horaConIJ(j));
                array.put(elemento);
            }
        }
        respuesta.put(VALIDO_KEY,"si");
        respuesta.put("turnos",array);
        return respuesta;
    }
    /*
    turnos resevados medico
    {
        valido:
        turnos:JSONArray[
                {
                        nombre_paciente:
                        horario:
                }
        ]
    }
    */
    private BitSet[] turnosReservadosEnBitsets(){
        BitSet[] bs = new BitSet[N_MEDICOS];
        for(int i=0;i<N_MEDICOS;i++){
            bs[i] = new BitSet(N_TURNOS);
        }

        for(Turno turno : turnos){
            int indice = calcularIndice(turno.getFecha().get_hora());
            bs[turno.getIdMedico()].set(indice);
        }

        return bs;
    }

    private int calcularIndice(String s){
        int hora   = Integer.parseInt(s.substring(0,2));
        int minutos= Integer.parseInt(s.substring(3,5));
        return (int) ((hora-INICIO_JORNADA_LABORAL)*TURNOS_POR_HORA+minutos/TIEMPO_POR_TURNO);
    }

    @Override
    public JSONObject consultarTurnosReservadosMedico(JSONObject json_object) {
        BitSet[] bs = turnosReservadosEnBitsets();
        JSONObject respuesta = new JSONObject();
        JSONArray array = new JSONArray();

        int i = Integer.parseInt(json_object.getString(ID_MEDICX_KEY));
        for(int j=0;j<N_TURNOS;j++){
            if(bs[i].get(j)){
                Turno turno = getTurnoPorIDMedYHora(i,horaConIJ(j));
                JSONObject elemento = new JSONObject();
                elemento.put(NOMBRE_KEY,turno.getPaciente().getNombre());
                elemento.put(HORA_KEY,turno.getFecha().get_hora());
                array.put(elemento);
            }
        }
        respuesta.put(VALIDO_KEY,"si");
        respuesta.put("turnos",array);
        return respuesta;
    }

    private Turno getTurnoPorIDMedYHora(int id_med, String hora){
        for (Turno turno : turnos){
            if (turno.getIdMedico()==id_med&&turno.getFecha().get_hora().equals(hora)){
                return turno;
            }
        }
        return null;
    }

    public JSONObject cambiarMetodoPago(JSONObject js){
        int id_medico = Integer.parseInt(js.getString(ID_MEDICX_KEY));
        JSONObject respuesta = new JSONObject();

        Turno turno = getTurnoPorIDMedYHora(id_medico,js.getString(HORA_KEY));
        if(turno==null){
            respuesta.put(VALIDO_KEY,"no");
            respuesta.put(ERROR_KEY,"No se encontro turno");
            return respuesta;
        }

        Metodo_de_pago metodo_de_pago = crearMetodoDePago(js);
        turno.setMetodoPago(metodo_de_pago);
        respuesta.put(VALIDO_KEY,"si");
        return respuesta;
    }

    private void levantarTurnos() throws IOException {
        flag_levantando_file = true;
        Pattern pattern = Pattern.compile("[,\n]");
        String linea;
        while ((linea = br.readLine()) != null) {

            if (!(linea.equals(""))) {
                String[] s = pattern.split(linea);
                JSONObject js= generarJsonApartirDeArray(s);
                crearTurno(js);
            }

        }
        flag_levantando_file = false;
    }

    private JSONObject generarJsonApartirDeArray(String[] s){
        JSONObject js = new JSONObject();

        js.put(NOMBRE_KEY,s[0]);
        js.put(DNI_KEY,s[1]);
        js.put(HORA_KEY,s[2]);

        js.put(ID_MEDICX_KEY,s[6]);
        js.put(METODO_DE_PAGO_KEY,s[7]);
        switch (s[7]){
            case EFECTIVO:
                js.put(MONTO_KEY,s[8]);
                js.put(COSTO_KEY,s[9]);
                break;
            case TARJETA_DEBITO:
                js.put(NUMERO_TARJETA_KEY,s[8]);
                js.put(VENCIMIENTO_TARJETA_KEY,s[9]);
                js.put(CODSEG_TARJETA_KEY,s[10]);
                js.put(COSTO_KEY,s[11]);
                break;
            case TARJETA_CREDITO:
                js.put(NUMERO_TARJETA_KEY,s[8]);
                js.put(VENCIMIENTO_TARJETA_KEY,s[9]);
                js.put(CODSEG_TARJETA_KEY,s[10]);
                js.put(N_CUOTAS_KEY,s[11]);
                js.put(COSTO_KEY,s[12]);
                break;
            default:
                break;
        }
        return js;
    }

    private void escribirTurnoTxt(JSONObject js, int dia, int mes, int anio) {
        Path filePath = Paths.get(PATH);
        String a_escribir = generarString(js,dia,mes,anio);
        try {
            Files.writeString(filePath, a_escribir, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generarString(JSONObject js, int dia, int mes, int anio){
        String cadena = "\n";
        cadena += js.getString(NOMBRE_KEY)+","+js.getString(DNI_KEY)+","+js.getString(HORA_KEY)+",";
        cadena += dia + "," + mes +","+anio+","+js.getString(ID_MEDICX_KEY)+",";
        cadena += js.getString(METODO_DE_PAGO_KEY) + ",";

        if(js.getString(METODO_DE_PAGO_KEY).equals(EFECTIVO)){
            cadena+= js.getString(MONTO_KEY)+",";
        }
        else{
            cadena+= js.getString(NUMERO_TARJETA_KEY)+",";
            cadena+= js.getString(VENCIMIENTO_TARJETA_KEY)+",";
            cadena+= js.getString(CODSEG_TARJETA_KEY)+",";
            if(js.getString(METODO_DE_PAGO_KEY).equals(TARJETA_CREDITO)){
                cadena+=js.getString(N_CUOTAS_KEY)+",";
            }
        }
        cadena+=js.getString(COSTO_KEY);
        return cadena;
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if(i >= 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++){
            Observer observer = observers.get(i);
            observer.update();
        }
    }

    public static void main(String[] args) {
        try {
            Agenda agenda = new Agenda();
            Gestor_Medicx gestor = new Gestor_Medicx();
            Validacion_Medicx validadorMedicxs = new Validacion_Medicx();
            Validacion_Turno validadorTurno = new Validacion_Turno();
            AgendaGUI agendaGui = new AgendaGUI("My Agenda", validadorTurno, validadorMedicxs, gestor.getMedicxs_id());
            agendaGui.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}