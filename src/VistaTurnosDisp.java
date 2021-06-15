import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VistaTurnosDisp extends JFrame{
    private static final String ID_MEDICX_KEY = "id medico";
    private static final String NOMBRE_MEDICX_KEY = "nombre del medico";
    ArrayList<String> id_medicos;
    private final Interfaz_Validacion_Turno validadorTurnos;
    private final Interfaz_Validacion_Medicx validadorMedicxs;
    private JPanel mainPanel;
    private JTextPane turnosTextPane;
    private JLabel titleLabel;
    private JScrollPane turnosScrollPane;

    public VistaTurnosDisp(String title, Interfaz_Validacion_Turno validadorTurnos, Interfaz_Validacion_Medicx validadorMedicxs){
        super(title);
        this.validadorTurnos = validadorTurnos;
        this.validadorMedicxs =validadorMedicxs;
        turnosScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        titleLabel.setText("TURNOS DISPONIBLES");
        turnosTextPane.setEditable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setPreferredSize(new Dimension(700, 500));
        this.pack();
    }

    public void update() {
        cargarVistaContent();
    }

    public void cargarVistaContent(){
        JSONObject jo = validadorTurnos.consultarTurnosDiponibles();
        for(int i=0; i<id_medicos.size(); i++){
            JSONObject consult = new JSONObject();
            consult.put(ID_MEDICX_KEY, id_medicos.get(i));
            consult = validadorMedicxs.getNomConID(consult);
            printInTextPane(consult.getString(NOMBRE_MEDICX_KEY));
            ArrayList<String> list = (ArrayList<String>) jo.get(id_medicos.get(i));
            list.forEach((n) -> printInTextPane("\t\t"+n));
        }
    }

    private void printInTextPane(String s){
        int len = turnosTextPane.getDocument().getLength();
        turnosTextPane.setCaretPosition(len);
        turnosTextPane.replaceSelection(s);
    }

    public static void main(String[] args){
        //JFrame frame = new VistaTurnosDisp("My Agenda");
        //frame.setVisible(true);
    }
}
