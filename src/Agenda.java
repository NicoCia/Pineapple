import java.util.ArrayList;
import java.util.List;

public class Agenda{
    //Campos
    private List<Turno> turnos;

    //Constructor
    public Agenda(){
        turnos = new ArrayList<Turno>();
    }

    //Metodos
    public boolean crear_turno(String hora, int id_medico){
        if(turno_disponible(hora,id_medico)){
            Turno turno = new Turno();//TODO Completar constructor
            turnos.add(turno);
            return true;
        }
        return false;
    }

    public ArrayList<Turno> consultar_turno_paciente(int dni){
        List turnos_del_paciente = new ArrayList<>();
        for (Turno turno : turnos){
            if(turno.get_paciente().get_dni() == dni){
                turnos_del_paciente.add(turno);
            }
        }
        return turnos_del_paciente;
    }

    private boolean turno_disponible(){
        for(Turno turno : turnos){
            if(turno.get_hora == hora && turno.get_id_medico() == id_medico){
                return false;
            }
        }
        return true;
    }

}