import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VistaTurnosDisp extends JFrame{
    private static final String ID_MEDICX_KEY           =         "id medico";
    private static final String NOMBRE_MEDICX_KEY       = "nombre del medico";
    private static final String VALIDO_KEY              =            "valido";
    private ArrayList<String> id_medicos;
    private final Interfaz_Validacion_Turno validadorTurnos;
    private final Interfaz_Validacion_Medicx validadorMedicxs;
    private JPanel mainPanel;
    private JTextPane turnosTextPane;
    private JLabel titleLabel;
    private JScrollPane turnosScrollPane;

    public VistaTurnosDisp(String title, Interfaz_Validacion_Turno validadorTurnos, Interfaz_Validacion_Medicx validadorMedicxs, ArrayList<String> id_medicos){
        super(title);
        this.validadorTurnos = validadorTurnos;
        this.validadorMedicxs =validadorMedicxs;
        this.id_medicos = id_medicos;
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
        if(jo.getString(VALIDO_KEY).equals("si")){
            JSONArray ja = jo.getJSONArray("medicos");
            for(int i=0; i<id_medicos.size(); i++){
                JSONObject consult = new JSONObject();
                consult.put(ID_MEDICX_KEY, id_medicos.get(i));
                consult = validadorMedicxs.getNomConID(consult);
                printInTextPane("Medicx: "+consult.getString(NOMBRE_MEDICX_KEY)+"\n");
                consult = ja.getJSONObject(Integer.parseInt(id_medicos.get(i)));
                JSONArray list = consult.getJSONArray(id_medicos.get(i)); //jo.get(id_medicos.get(i));
                for(int j=0; j<list.length(); j++){
                    String s = list.getString(j);
                    printInTextPane("\t\t" + s + "\n");
                }
            }
        }
        turnosScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    private void printInTextPane(String s){
        int len = turnosTextPane.getDocument().getLength();
        turnosTextPane.setEditable(true);
        turnosTextPane.setCaretPosition(len);
        turnosTextPane.replaceSelection(s);
        turnosTextPane.setEditable(false);
    }
}
