import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.json.*;


public class AgendaGUI extends JFrame {

    ArrayList<String> id_medicos;

    /*---------------DEFINICION ETIQUETAS CAMPOS JSON-----------------*/
    private static final String NOMBRE_KEY              =               "nombre";
    private static final String DNI_KEY                 =                  "dni";
    private static final String NUMERO_TARJETA_KEY      =         "n de Tarjeta";
    private static final String MONTO_KEY               =                "monto";
    private static final String COSTO_KEY               = "costo de la consulta";
    private static final String METODO_DE_PAGO_KEY      =       "metodo de pago";
    private static final String HORA_KEY                =                 "hora";
    private static final String NOMBRE_MEDICX_KEY       =    "nombre del medico";
    private static final String N_CUOTAS_KEY            =          "n de cuotas";
    private static final String VENCIMIENTO_TARJETA_KEY =          "vencimiento";
    private static final String CODSEG_TARJETA_KEY      =  "codigo de seguridad";
    private static final String ID_MEDICX_KEY           =            "id medico";
    private static final String MATRICULA_MEDICX_KEY    =            "matricula";
    private static final String CONTRASENIA_MEDICX_KEY  =          "contrasenia";
    private static final String VALIDO_KEY              =               "valido";
    private static final String ERROR_KEY               =                "error";
    private static final String TURNOS_KEY              =               "turnos";

    /*-----------DEFINICION INTERFACES DE VALIDACION----------------*/
    private Interfaz_Validacion_Turno validadorTurnos;
    private Interfaz_Validacion_Medicx validadorMedico;

    /*---------------VISTAS-----------------*/
    private JPanel ViewPanel;
    /*---------------VISTA PRINCIPAL-----------------*/
    private JPanel mainPanel;
    private JPanel mainButtonsPanel;
    private JButton iniciarSesion;
    private JButton consultarTurnoButton;
    private JButton crearTurnoButton;
    private JButton verTurnosButton;
    /*---------------VISTA INICIO DE SESIÓN-----------------*/
    private JPanel sesionPanel;
    private JPanel matriculaSesionPanel;
    private JLabel matriculaLabel;
    private JLabel matriculaIncorrectaLabel;
    private JTextField matricula;
    private JPanel buttonsSesionPanel;
    private JButton backSesionButton;
    private JButton loginButton;
    private JPanel passwordSesionPanel;
    private JLabel passwordLabel;
    private JPasswordField password;
    private JLabel passwordIncorrectaLabel;
    /*---------------VISTA CONSULTAR TURNO-----------------*/
    private JPanel consultaPanel;
    private JPanel dniConsultaPanel;
    private JLabel dniLabel1;
    private JTextField dniConsulta;
    private JPanel buttonsConsultaPanel;
    private JButton backConsultaButton;
    private JButton consultaButton;
    /*---------------VISTA CREAR TURNO-----------------*/
    private JPanel crearPanel;
    private JPanel buttonsPagoPanel;
    private JButton backCrearButton;
    private JButton reservarButton;
    private JPanel ingresoDatosCrearPanel;
    private JPanel datosPacientePagoPanel;
    private JComboBox pagoBox;
    private JTextField nombrePaciente;
    private JTextField dniCrear;
    private JComboBox medicxsBox;
    private JComboBox horarioBox;
    private JLabel nombreLabel;
    private JLabel dniLabel2;
    private JLabel medicoLabel;
    private JLabel horarioLabel;
    private JLabel pagoLabel;
    private JLabel costoConsultaLabel;
    private JPanel metodoPagoPanel;
    private JPanel efectivoPanel;
    private JLabel efectivoLabel;
    private JTextField monto;
    private JPanel debitoPanel;
    private JTextField numeroTarjetaDebito;
    private JTextField codSegTarjetaDebito;
    private JLabel numeroDebitoLabel;
    private JLabel codSegDebitoLabel;
    private JLabel vencimientoDebitoLabel;
    private JPanel vencimientoDebitoPanel;
    private JComboBox mesDebitoBox;
    private JComboBox anioDebitoBox;
    private JPanel creditoPanel;
    private JTextField numeroTarjetaCredito;
    private JTextField codSegTarjetaCredito;
    private JPanel vencimientoCreditoPanel;
    private JComboBox mesCreditoBox;
    private JComboBox anioCreditoBox;
    private JLabel numeroCreditoLabel;
    private JLabel vencimientoCreditoLabel;
    private JLabel codSegCreditoLabel;
    private JComboBox cuotasBox;
    private JLabel cuotasCreditoLabel;
    /*---------------VISTA TURNOS RESERVADOS POR MEDICO-----------------*/
    private JPanel turnosReservMedicxPanel;
    private JScrollPane scrollPanel;
    private JLabel nombreDoctor;
    private JTextPane turnosMedicoTextPane;
    private JButton cerrarSesionButton;
    /*---------------VISTA TURNO CONSULTADO-----------------*/
    private JPanel mostrarTurnoConsultadoPanel;
    private JPanel infoTurnoConsultadoPanel;
    private JTextPane textoMostrarTurnoPanel;
    private JLabel tituloMostrarTurnoLabel;
    private JPanel buttosMostrarTurnoPanel;
    private JButton backButtonMostarTurno;
    private JButton cambiarMetodoDePagoButton;

    /*---------------CLASE AgendaGUI-----------------*/
    public AgendaGUI(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(ViewPanel);
        this.setPreferredSize(new Dimension(700, 500));
        //Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.pack();
        this.setLocationRelativeTo(null);


        String[] arr = new String[id_medicos.size()];
        for(int i=0; i<id_medicos.size(); i++){
            JSONObject jo = new JSONObject();
            jo.put(ID_MEDICX_KEY, id_medicos.get(i));
            arr[i] = validadorMedico.getNomConID(jo).getString(NOMBRE_KEY);
        }
        medicxsBox.setModel(new DefaultComboBoxModel(arr));

        mainPanel.setVisible(true);
        sesionPanel.setVisible(false);
        consultaPanel.setVisible(false);
        crearPanel.setVisible(false);
        turnosReservMedicxPanel.setVisible(false);
        mostrarTurnoConsultadoPanel.setVisible(false);


        /*---------------MÉTODOS ACCIÓN BOTONES VISTA PRINCIPAL-----------------*/
        /**
         * Método acción al presionar el botón "Reservar Turno"
         * Cambia a VISTA PRINCIPAL por VISTA CREAR TURNO
         */
        crearTurnoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPanel.setVisible(false);
                sesionPanel.setVisible(false);
                consultaPanel.setVisible(false);
                crearPanel.setVisible(true);
                turnosReservMedicxPanel.setVisible(false);

                metodoPagoPanel.setVisible(true);
                pagoBox.setSelectedItem("Efectivo");
                efectivoPanel.setVisible(true);
                debitoPanel.setVisible(false);
                creditoPanel.setVisible(false);
            }
        });
        /**
         * Método acción al presionar el botón "Consultar Turno"
         * Cambia a VISTA PRINCIPAL por VISTA CONSULTAR TURNO
         */
        consultarTurnoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPanel.setVisible(false);
                sesionPanel.setVisible(false);
                consultaPanel.setVisible(true);
                crearPanel.setVisible(false);
                turnosReservMedicxPanel.setVisible(false);
            }
        });
        /**
         * Método acción al presionar el botón "Ver Turnos Disponibles"
         * Despliega nueva ventana con VISTA TURNOS DISPONIBLES
         */
        verTurnosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                VistaTurnosDisp vistaTurnosDisp = new VistaTurnosDisp("My Agenda", validadorTurnos, validadorMedico);
                vistaTurnosDisp.cargarVistaContent();
                vistaTurnosDisp.setVisible(true);
            }
        });
        /**
         * Método acción al presionar el botón "Iniciar Sesión"
         * Cambia a VISTA PRINCIPAL por VISTA INICIO DE SESIÓN
         */
        iniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPanel.setVisible(false);
                sesionPanel.setVisible(true);
                consultaPanel.setVisible(false);
                crearPanel.setVisible(false);
                turnosReservMedicxPanel.setVisible(false);
                mostrarTurnoConsultadoPanel.setVisible(false);

                password.setText("");
                matricula.setText("");
                passwordIncorrectaLabel.setText("");
                matriculaIncorrectaLabel.setText("");
            }
        });

        /*---------------MÉTODOS ACCIÓN BOTONES VISTA INICIO DE SESIÓN-----------------*/
        /**
         * Método acción al presionar el botón "Iniciar Sesión"
         * Envia credenciales de logueo a Interfaz_Validacion_Medico para su validacion
         * Si son validas: cambia VISTA INICIO DE SESIÓN por VISTA TURNOS RESERVADOS POR MÉDICO
         * Si son invalidas: imprime mensaje de error
         */
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject jo = new JSONObject();
                jo.put(MATRICULA_MEDICX_KEY, matricula.getText());
                jo.put(CONTRASENIA_MEDICX_KEY, new String(password.getPassword()));
                jo = validadorMedico.iniciarSesion(jo);
                if(jo.getString(VALIDO_KEY).equals("si")){
                    nombreDoctor.setText(validadorMedico.getNomConID(jo).getString(NOMBRE_MEDICX_KEY));
                    jo = validadorTurnos.consultarTurnosReservadosMedico(jo);
                    JSONArray ja = jo.getJSONArray(TURNOS_KEY);
                    for(int i=0; i<ja.length(); i++){
                        jo = ja.getJSONObject(i);
                        String s = "Paciente: " + jo.getString(NOMBRE_KEY) + "Hora: " + jo.getString(HORA_KEY);
                        printInTextPane(s, turnosMedicoTextPane);
                    }
                    mainPanel.setVisible(false);
                    sesionPanel.setVisible(false);
                    consultaPanel.setVisible(false);
                    crearPanel.setVisible(false);
                    turnosReservMedicxPanel.setVisible(true);
                    mostrarTurnoConsultadoPanel.setVisible(false);
                }
                else{
                    JOptionPane.showMessageDialog(null, jo.getString(ERROR_KEY));
                }
            }
        });
        /**
         * Método acción al presionar el botón "Volver"
         * Cambia a VISTA INICIO DE SESIÓN por VISTA PRINCIPAL
         */
        backSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPanel.setVisible(true);
                sesionPanel.setVisible(false);
                consultaPanel.setVisible(false);
                crearPanel.setVisible(false);
                turnosReservMedicxPanel.setVisible(false);
                mostrarTurnoConsultadoPanel.setVisible(false);
            }
        });

        /*---------------MÉTODOS ACCIÓN BOTONES VISTA CONSULTAR TURNO-----------------*/
        /**
         * Método acción al presionar el botón "Consultar"
         * Envia dni a consultar a Interfaz_Validacion_Turno para su validación
         * Si es valido: cambia VISTA CONSULTAR TURNO por VISTA TURNO CONSULTADO
         * Si es invalido: imprime mensaje de error
         */
        consultaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject jo = new JSONObject();
                jo.put( DNI_KEY, dniConsulta.getText());
                jo = validadorTurnos.consultarTurno(jo);
                if (jo.getString(VALIDO_KEY).equals("si")){

                    mainPanel.setVisible(false);
                    sesionPanel.setVisible(false);
                    consultaPanel.setVisible(false);
                    crearPanel.setVisible(false);
                    turnosReservMedicxPanel.setVisible(false);
                    mostrarTurnoConsultadoPanel.setVisible(true);

                    textoMostrarTurnoPanel.setText("Paciente: " + jo.getString(NOMBRE_KEY) + "\n" +
                            "Medicx: " + jo.getString(NOMBRE_MEDICX_KEY) + "\n" +
                            "Hora: " + jo.getString(HORA_KEY) + "\n" +
                            "Método de Pago: " + jo.getString(METODO_DE_PAGO_KEY) + "\n");

                    textoMostrarTurnoPanel.setEditable(false);
                    tituloMostrarTurnoLabel.setText("Información del Turno:");

                    dniConsulta.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(null, "No se encontraron turnos para el paciente solicitado.");
                }
            }
        });
        /**
         * Método acción al presionar el botón "Volver"
         * Cambia a VISTA CONSULTAR TURNO por VISTA PRINCIPAL
         */
        backConsultaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPanel.setVisible(true);
                sesionPanel.setVisible(false);
                consultaPanel.setVisible(false);
                crearPanel.setVisible(false);
                turnosReservMedicxPanel.setVisible(false);
            }
        });

        /*---------------MÉTODOS ACCIÓN BOTONES VISTA CREAR TURNO-----------------*/
        /**
         * Método acción al presionar el botón "Consultar"
         * Envia datos para crear un nuevo turno a Interfaz_Validacion_Turno para su validación
         * Si son validos: imprime ticket del nuevo turno creado
         * Si son invalidos: imprime mensaje de error
         */
        reservarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject jo = new JSONObject();
                jo.put(NOMBRE_KEY, nombrePaciente.getText());
                jo.put(DNI_KEY, dniCrear.getText());
                jo.put(NOMBRE_MEDICX_KEY, medicxsBox.getSelectedItem().toString());
                jo.put(HORA_KEY, horarioBox.getSelectedItem().toString());
                jo.put(METODO_DE_PAGO_KEY, pagoBox.getSelectedItem().toString());
                if(pagoBox.getSelectedItem().toString().equals("Efectivo")){
                    jo.put(MONTO_KEY, monto.getText());
                }
                else{
                    jo.put(NUMERO_TARJETA_KEY, numeroTarjetaDebito.getText());
                    jo.put(CODSEG_TARJETA_KEY, codSegTarjetaDebito.getText());
                    jo.put(VENCIMIENTO_TARJETA_KEY, mesDebitoBox.getSelectedItem().toString() + "/" + anioDebitoBox.getSelectedItem().toString());

                    if(pagoBox.getSelectedItem().toString().equals("Tarjeta De Credito")){
                        jo.put(N_CUOTAS_KEY, cuotasBox.getSelectedItem().toString());
                    }
                }

                jo = validadorTurnos.crearTurno(jo);

                if(jo.getString(VALIDO_KEY).equals("si")){
                    //TODO finalizar implementación
                }
                else{
                    JOptionPane.showMessageDialog(null, jo.getString(ERROR_KEY));
                }
            }
        });
        /**
         * Método acción al presionar el botón "Volver"
         * Cambia a VISTA CREAR TURNO por VISTA PRINCIPAL
         */
        backCrearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPanel.setVisible(true);
                sesionPanel.setVisible(false);
                consultaPanel.setVisible(false);
                crearPanel.setVisible(false);
                turnosReservMedicxPanel.setVisible(false);
            }
        });
        /*
        * TODO documentar
        */
        medicxsBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JSONObject jo = new JSONObject();
                jo.put(NOMBRE_MEDICX_KEY, medicxsBox.getSelectedItem().toString());
                jo = validadorMedico.getIDConNom(jo);
                jo = validadorTurnos.consultarTurnosDisponiblesMedico(jo);

                if(jo.getString(VALIDO_KEY).equals("si")){
                    JSONArray ja = jo.getJSONArray(TURNOS_KEY);
                    String[] s = new String[ja.length()];
                    for(int i=0; i<ja.length(); i++){
                        jo = ja.getJSONObject(i);
                        s[i] = jo.getString(HORA_KEY);
                    }
                    horarioBox.setModel(new DefaultComboBoxModel(s));
                }
                else{
                    String[] s = {"No existen turnos disponibles"};
                    horarioBox.setModel(new DefaultComboBoxModel(s));
                }

            }
        });
        /*
         * TODO documentar
         */
        pagoBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                efectivoPanel.setVisible(false);
                debitoPanel.setVisible(false);
                creditoPanel.setVisible(false);

                switch (pagoBox.getSelectedItem().toString()){
                    case "Efectivo":            efectivoPanel.setVisible(true); break;
                    case "Tarjeta De Debito":   debitoPanel.setVisible(true);   break;
                    case "Tarjeta De Credito":  creditoPanel.setVisible(true);  break;
                }
            }
        });

        /*---------------MÉTODOS ACCIÓN BOTONES VISTA TURNOS RESERVADOS POR MEDICO-----------------*/
        /**
         * Método acción al presionar el botón "Volver"
         * Cambia a VISTA TURNOS RESERVADOS POR MÉDICO por VISTA INICIO DE SESIÓN
         */
        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPanel.setVisible(false);
                sesionPanel.setVisible(true);
                consultaPanel.setVisible(false);
                crearPanel.setVisible(false);
                turnosReservMedicxPanel.setVisible(false);
                mostrarTurnoConsultadoPanel.setVisible(false);

                password.setText("");
                matricula.setText("");
                passwordIncorrectaLabel.setText("");
                matriculaIncorrectaLabel.setText("");
            }
        });

        /*---------------MÉTODOS ACCIÓN BOTONES VISTA TURNO CONSULTADO-----------------*/
        /**
         * Método acción al presionar el botón "Consultar"
         * Envia datos para metodo de pago del turno consultado a Interfaz_Validacion_Turno para su validación
         * Si son validos: imprime ticket del turno con los nuevos datos
         * Si son invalidos: imprime mensaje de error
         */
        cambiarMetodoDePagoButton.addActionListener(new ActionListener() {
            @Override //TODO implementar
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        /**
         * Método acción al presionar el botón "Volver"
         * Cambia a VISTA TURNO CONSULTADO por VISTA PRINCIPAL
         */
        backButtonMostarTurno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPanel.setVisible(true);
                sesionPanel.setVisible(false);
                consultaPanel.setVisible(false);
                crearPanel.setVisible(false);
                turnosReservMedicxPanel.setVisible(false);
                mostrarTurnoConsultadoPanel.setVisible(false);
            }
        });


    }

    private void printInTextPane(String s, JTextPane tp){
        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.replaceSelection(s);
    }

    /*--------METODO MAIN----------*/
    public static void main(String[] args) {
        JFrame frame = new AgendaGUI("My Agenda");
        frame.setVisible(true);
    }
}
