import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;

import org.json.*;


public class AgendaGUI extends JFrame {

    private final ArrayList<String> id_medicos;
    private static final int costoConsulta = 200;
    JSONObject jo_consultado;

    /*---------------DEFINICIÓN ETIQUETAS CAMPOS JSON-----------------*/
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

    /*---------------DEFINICIÓN ETIQUETAS VISTAS-----------------*/
    private static final String VISTA_PRINCIPAL                     =                     "mainPanel";
    private static final String VISTA_INICIO_DE_SESION              =                   "sesionPanel";
    private static final String VISTA_CONSULTAR_TURNO               =                 "consultaPanel";
    private static final String VISTA_CREAR_TURNO                   =                    "crearPanel";
    private static final String VISTA_TURNOS_RESERVADOS_POR_MEDICO  =       "turnosReservMedicxPanel";
    private static final String VISTA_TURNO_CONSULTADO              =   "mostrarTurnoConsultadoPanel";
    private static final String VISTA_CAMBIAR_METODO_DE_PAGO        =        "cambiarMetodoPagoPanel";

    /*-----------DEFINICIÓN INTERFACES DE VALIDACIÓN----------------*/
    private final Interfaz_Validacion_Turno validadorTurnos;
    private final Interfaz_Validacion_Medicx validadorMedicx;

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
    private JComboBox pagoBox1;
    private JTextField nombrePaciente;
    private JTextField dniCrear;
    private JComboBox medicxsBox;
    private JComboBox horarioBox;
    private JLabel nombreLabel;
    private JLabel dniLabel2;
    private JLabel medicoLabel;
    private JLabel horarioLabel;
    private JLabel pagoLabel1;
    private JLabel costoConsultaLabel1;
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
    /*---------------VISTA CAMBIAR METODO DE PAGO-----------------*/
    private JPanel cambiarMetodoPagoPanel;
    private JButton realizarCambioButton;
    private JButton backCambiarButton;
    private JComboBox pagoBox2;
    private JLabel pagoLabel2;
    private JLabel costoConsultaLabel2;
    private JPanel cambiarEfectivoPanel;
    private JPanel cambiarDebitoPanel;
    private JPanel cambiarCreditoPanel;
    private JPanel cambiarButtonsPanel;
    private JTextField cambiarEfectivoMonto;
    private JLabel cambiarEfectivoMontoLabel;
    private JLabel numeroTarjetaDebitoCambiarLabel;
    private JLabel vencimientoDebitoCambiarLabel;
    private JLabel codSegDebitoCambiarLabel;
    private JTextField numeroTarjetaDebitoCambiar;
    private JTextField codSegTarjetaDebitoCambiar;
    private JTextField numeroTarjetaCreditoCambiar;
    private JLabel numeroTarjetaCreditoCambiarLabel;
    private JTextField codSegTarjetaCreditoCambiar;
    private JLabel vencimientoCreditoCambiarLabel;
    private JLabel codSegCreditoCambiarLabel;
    private JComboBox cuotasCambiarBox;
    private JLabel cuotasCreditoCambiarLabel;
    private JComboBox mesCreditoCambiarBox;
    private JComboBox anioCreditoCambiarBox;
    private JPanel nuevoMetodoPagoPanel;
    private JComboBox mesDebitoCambiarBox;
    private JComboBox anioDebitoCambiarBox;

    /*---------------CLASE AgendaGUI-----------------*/
    public AgendaGUI(String title, Interfaz_Validacion_Turno validadorTurnos, Interfaz_Validacion_Medicx validadorMedicx, ArrayList<String> id_medicos){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(ViewPanel);
        this.setPreferredSize(new Dimension(700, 500));
        this.pack();
        this.setLocationRelativeTo(null);

        this.validadorTurnos = validadorTurnos;
        this.validadorMedicx = validadorMedicx;
        this.id_medicos = id_medicos;
        initGUI();
        goTo(VISTA_PRINCIPAL);

        /*---------------MÉTODOS ACCIÓN BOTONES VISTA PRINCIPAL-----------------*/
        /**
         * Método acción al presionar el botón "Reservar Turno"
         * Cambia a VISTA PRINCIPAL por VISTA CREAR TURNO
         */
        crearTurnoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                goTo(VISTA_CREAR_TURNO);

                pagoBox1.setSelectedItem("Efectivo");
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
                goTo(VISTA_CONSULTAR_TURNO);
            }
        });
        /**
         * Método acción al presionar el botón "Ver Turnos Disponibles"
         * Despliega nueva ventana con VISTA TURNOS DISPONIBLES
         */
        verTurnosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                VistaTurnosDisp vistaTurnosDisp = new VistaTurnosDisp("My Agenda", AgendaGUI.this.validadorTurnos, AgendaGUI.this.validadorMedicx, AgendaGUI.this.id_medicos);
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
                goTo(VISTA_INICIO_DE_SESION);

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
                jo = AgendaGUI.this.validadorMedicx.iniciarSesion(jo);
                if(jo.getString(VALIDO_KEY).equals("si")){
                    nombreDoctor.setText(AgendaGUI.this.validadorMedicx.getNomConID(jo).getString(NOMBRE_MEDICX_KEY));
                    jo = AgendaGUI.this.validadorTurnos.consultarTurnosReservadosMedico(jo);
                    JSONArray ja = jo.getJSONArray(TURNOS_KEY);
                    for(int i=0; i<ja.length(); i++){
                        jo = ja.getJSONObject(i);
                        String s = "Paciente: " + jo.getString(NOMBRE_KEY) + " Hora: " + jo.getString(HORA_KEY) + "\n";
                        printInTextPane(s, turnosMedicoTextPane);
                    }

                    goTo(VISTA_TURNOS_RESERVADOS_POR_MEDICO);
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
                goTo(VISTA_PRINCIPAL);
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
                jo_consultado = new JSONObject();
                jo_consultado.put( DNI_KEY, dniConsulta.getText());
                jo_consultado = AgendaGUI.this.validadorTurnos.consultarTurno(jo_consultado);
                if (jo_consultado.getString(VALIDO_KEY).equals("si")){

                    goTo(VISTA_TURNO_CONSULTADO);

                    textoMostrarTurnoPanel.setText("Paciente: " + jo_consultado.getString(NOMBRE_KEY) + "\n" +
                            "Medicx: " + (AgendaGUI.this.validadorMedicx.getNomConID(jo_consultado).getString(NOMBRE_MEDICX_KEY) ) + "\n" +
                            "Hora: " + jo_consultado.getString(HORA_KEY) + "\n" +
                            "Método de Pago: " + jo_consultado.getString(METODO_DE_PAGO_KEY) + "\n");

                    textoMostrarTurnoPanel.setEditable(false);
                    tituloMostrarTurnoLabel.setText("Información del Turno:");

                    dniConsulta.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(null, jo_consultado.getString(ERROR_KEY));
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
                goTo(VISTA_PRINCIPAL);
                dniConsulta.setText("");
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
                jo.put(NOMBRE_MEDICX_KEY, medicxsBox.getSelectedItem().toString());
                jo = AgendaGUI.this.validadorMedicx.getIDConNom(jo);
                jo.put(NOMBRE_KEY, nombrePaciente.getText());
                jo.put(DNI_KEY, dniCrear.getText());
                jo.put(HORA_KEY, horarioBox.getSelectedItem().toString());
                jo.put(METODO_DE_PAGO_KEY, pagoBox1.getSelectedItem().toString().toLowerCase(Locale.ROOT));
                jo.put(COSTO_KEY, ""+costoConsulta);
                if(pagoBox1.getSelectedItem().toString().equals("Efectivo")){
                    jo.put(MONTO_KEY, monto.getText());
                }
                else{
                    if (pagoBox1.getSelectedItem().toString().equals("Tarjeta De Debito")){
                        jo.put(NUMERO_TARJETA_KEY, numeroTarjetaDebito.getText());
                        jo.put(CODSEG_TARJETA_KEY, codSegTarjetaDebito.getText());
                        jo.put(VENCIMIENTO_TARJETA_KEY, mesDebitoBox.getSelectedItem().toString() + "/" + anioDebitoBox.getSelectedItem().toString());
                    }
                    else{
                        jo.put(NUMERO_TARJETA_KEY, numeroTarjetaCredito.getText());
                        jo.put(CODSEG_TARJETA_KEY, codSegTarjetaCredito.getText());
                        jo.put(VENCIMIENTO_TARJETA_KEY, mesCreditoBox.getSelectedItem().toString() + "/" + anioCreditoBox.getSelectedItem().toString());
                        jo.put(N_CUOTAS_KEY, cuotasBox.getSelectedItem().toString());
                    }
                }

                jo = AgendaGUI.this.validadorTurnos.crearTurno(jo);

                if(jo.getString(VALIDO_KEY).equals("si")){
                    //TODO finalizar implementación - imprimir ticket
                    JOptionPane.showMessageDialog(null, "Turno creado con exito!");
                    cleanCrearTurnoPanel();
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
                goTo(VISTA_PRINCIPAL);
                cleanCrearTurnoPanel();
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
                jo = AgendaGUI.this.validadorMedicx.getIDConNom(jo);
                jo = AgendaGUI.this.validadorTurnos.consultarTurnosDisponiblesMedico(jo);

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
        pagoBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                efectivoPanel.setVisible(false);
                debitoPanel.setVisible(false);
                creditoPanel.setVisible(false);

                switch (pagoBox1.getSelectedItem().toString()){
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
                goTo(VISTA_INICIO_DE_SESION);

                turnosMedicoTextPane.setText("");
                password.setText("");
                matricula.setText("");
                passwordIncorrectaLabel.setText("");
                matriculaIncorrectaLabel.setText("");
            }
        });

        /*---------------MÉTODOS ACCIÓN BOTONES VISTA TURNO CONSULTADO-----------------*/
        /**
         * Método acción al presionar el botón "Cambiar Metodo De Pago"
         * Cambia VISTA TURNO CONSULTADO por VISTA CAMBIAR METODO DE PAGO
         */
        cambiarMetodoDePagoButton.addActionListener(new ActionListener() {
            @Override //TODO implementar
            public void actionPerformed(ActionEvent actionEvent) {
                goTo(VISTA_CAMBIAR_METODO_DE_PAGO);
            }
        });
        /**
         * Método acción al presionar el botón "Volver"
         * Cambia a VISTA TURNO CONSULTADO por VISTA PRINCIPAL
         */
        backButtonMostarTurno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                goTo(VISTA_PRINCIPAL);
            }
        });

        /*---------------MÉTODOS ACCIÓN BOTONES VISTA CAMBIAR METODO DE PAGO-----------------*/
        /**
         * Método acción al presionar el botón "Realizar Cambio"
         * Envia datos para cambiar metodo de pago del turno consultado a Interfaz_Validacion_Turno para su validación
         * Si son validos: imprime ticket del turno con los nuevos datos
         * Si son invalidos: imprime mensaje de error
         */
        realizarCambioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jo_consultado.put(METODO_DE_PAGO_KEY, pagoBox2.getSelectedItem().toString().toLowerCase(Locale.ROOT));
                jo_consultado.put(COSTO_KEY, ""+costoConsulta);
                if(pagoBox2.getSelectedItem().toString().equals("Efectivo")){
                    jo_consultado.put(MONTO_KEY, cambiarEfectivoMonto.getText());
                }
                else{
                    if (pagoBox1.getSelectedItem().toString().equals("Tarjeta De Debito")){
                        jo_consultado.put(NUMERO_TARJETA_KEY, numeroTarjetaDebitoCambiar.getText());
                        jo_consultado.put(CODSEG_TARJETA_KEY, codSegTarjetaDebitoCambiar.getText());
                        jo_consultado.put(VENCIMIENTO_TARJETA_KEY, mesDebitoCambiarBox.getSelectedItem().toString() + "/" + anioDebitoCambiarBox.getSelectedItem().toString());
                    }
                    else{
                        jo_consultado.put(NUMERO_TARJETA_KEY, numeroTarjetaCreditoCambiar.getText());
                        jo_consultado.put(CODSEG_TARJETA_KEY, codSegTarjetaCreditoCambiar.getText());
                        jo_consultado.put(VENCIMIENTO_TARJETA_KEY, mesCreditoCambiarBox.getSelectedItem().toString() + "/" + anioCreditoCambiarBox.getSelectedItem().toString());
                        jo_consultado.put(N_CUOTAS_KEY, cuotasCambiarBox.getSelectedItem().toString());
                    }
                }

                jo_consultado = AgendaGUI.this.validadorTurnos.cambiarMetodoPago(jo_consultado);

                if(jo_consultado.getString(VALIDO_KEY).equals("si")){
                    //TODO finalizar implementación - imprimir ticket
                    JOptionPane.showMessageDialog(null, "Cambio realizado con exito!");
                    cleanCambiarMetodoDePagoPanel();
                    goTo(VISTA_PRINCIPAL);
                }
                else{
                    JOptionPane.showMessageDialog(null, jo_consultado.getString(ERROR_KEY));
                }
            }
        });
        /**
         * Método acción al presionar el botón "Volver"
         * Cambia a VISTA AMBIAR METODO DE PAGO por VISTA TURNO CONSULTADO
         */
        backCambiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                goTo(VISTA_TURNO_CONSULTADO);
                cleanCambiarMetodoDePagoPanel();
            }
        });

        //TODO documentar
        pagoBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cambiarEfectivoPanel.setVisible(false);
                cambiarDebitoPanel.setVisible(false);
                cambiarCreditoPanel.setVisible(false);

                switch (pagoBox2.getSelectedItem().toString()){
                    case "Efectivo":            cambiarEfectivoPanel.setVisible(true); break;
                    case "Tarjeta De Debito":   cambiarDebitoPanel.setVisible(true);   break;
                    case "Tarjeta De Credito":  cambiarCreditoPanel.setVisible(true);  break;
                }
            }
        });
    }

    private void cleanCrearTurnoPanel(){
        nombrePaciente.setText("");
        dniCrear.setText("");
        monto.setText("");
        pagoBox1.setSelectedItem("Efectivo");
        numeroTarjetaDebito.setText("");
        codSegTarjetaDebito.setText("");
        mesDebitoBox.setSelectedItem("1");
        anioDebitoBox.setSelectedItem("2020");
        numeroTarjetaCredito.setText("");
        codSegTarjetaCredito.setText("");
        mesCreditoBox.setSelectedItem("1");
        anioCreditoBox.setSelectedItem("2020");
        cuotasBox.setSelectedItem("1");
    }

    private void cleanCambiarMetodoDePagoPanel(){
        pagoBox2.setSelectedItem("Efectivo");
        cambiarEfectivoMonto.setText("");
        numeroTarjetaDebitoCambiar.setText("");
        codSegTarjetaDebitoCambiar.setText("");
        mesDebitoCambiarBox.setSelectedItem("1");
        anioDebitoCambiarBox.setSelectedItem("2020");
        numeroTarjetaCreditoCambiar.setText("");
        codSegTarjetaCreditoCambiar.setText("");
        mesCreditoCambiarBox.setSelectedItem("1");
        anioCreditoCambiarBox.setSelectedItem("2020");
        cuotasCambiarBox.setSelectedItem("1");
    }

    /**
     * Metodo de escritura de texto sobre paneles de la vista
     * @param s - Texto a ser impreso en pantalla
     * @param tp - Panel donde se debe escribir el texto
     */
    private void printInTextPane(String s, JTextPane tp){
        tp.setEditable(true);
        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.replaceSelection(s);
        tp.setEditable(false);
    }

    /**
     * Metodo de cambio de vistas
     * @param vista - Vista que desea ser activada
     */
    private void goTo(String vista){
        mainPanel.setVisible(false);
        sesionPanel.setVisible(false);
        consultaPanel.setVisible(false);
        crearPanel.setVisible(false);
        turnosReservMedicxPanel.setVisible(false);
        mostrarTurnoConsultadoPanel.setVisible(false);
        cambiarMetodoPagoPanel.setVisible(false);

        switch (vista){
            case VISTA_INICIO_DE_SESION: sesionPanel.setVisible(true); break;
            case VISTA_CONSULTAR_TURNO: consultaPanel.setVisible(true); break;
            case VISTA_CREAR_TURNO: crearPanel.setVisible(true); break;
            case VISTA_TURNOS_RESERVADOS_POR_MEDICO: turnosReservMedicxPanel.setVisible(true); break;
            case VISTA_TURNO_CONSULTADO: mostrarTurnoConsultadoPanel.setVisible(true); break;
            case VISTA_CAMBIAR_METODO_DE_PAGO: cambiarMetodoPagoPanel.setVisible(true); break;
            default: mainPanel.setVisible(true); break;
        }
    }

    /**
     * Método de inicializacion de la GUI
     * carga las vistas
     */
    private void initGUI(){
        mainButtonsPanel.setVisible(true);
        matriculaSesionPanel.setVisible(true);
        matriculaLabel.setText("Matricula:");
        buttonsSesionPanel.setVisible(true);
        passwordSesionPanel.setVisible(true);
        passwordLabel.setText("Password: ");
        dniConsultaPanel.setVisible(true);
        dniLabel1.setText("DNI:");
        buttonsConsultaPanel.setVisible(true);
        buttonsPagoPanel.setVisible(true);
        ingresoDatosCrearPanel.setVisible(true);
        datosPacientePagoPanel.setVisible(true);
        nombreLabel.setText("Nombre:");
        dniLabel2.setText("DNI:");
        medicoLabel.setText("Medicx:");
        horarioLabel.setText("Horario:");
        pagoLabel1.setText("Forma De Pago:");
        costoConsultaLabel1.setText("Valor consulta: $" + costoConsulta);
        metodoPagoPanel.setVisible(true);
        efectivoLabel.setText("Monto:");
        numeroDebitoLabel.setText("Nº Tarjeta:");
        codSegDebitoLabel.setText("Cod Seguridad:");
        vencimientoDebitoLabel.setText("Vencimiento:");
        vencimientoDebitoPanel.setVisible(true);
        vencimientoCreditoPanel.setVisible(true);
        numeroCreditoLabel.setText("Nº Tarjeta:");
        vencimientoCreditoLabel.setText("Vencimiento:");
        codSegCreditoLabel.setText("Cod Seguridad:");
        cuotasCreditoLabel.setText("Nº Cuotas:");
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        infoTurnoConsultadoPanel.setVisible(true);
        buttosMostrarTurnoPanel.setVisible(true);
        cambiarButtonsPanel.setVisible(true);
        pagoLabel2.setText("Forma De Pago:");
        costoConsultaLabel2.setText("Valor consulta: $" + costoConsulta);
        cambiarEfectivoMontoLabel.setText("Monto:");
        numeroTarjetaDebitoCambiarLabel.setText("Nº Tarjeta:");
        vencimientoDebitoCambiarLabel.setText("Vencimiento:");
        codSegDebitoCambiarLabel.setText("Cod Seguridad:");
        numeroTarjetaCreditoCambiarLabel.setText("Nº Tarjeta:");
        vencimientoCreditoCambiarLabel.setText("Vencimiento:");
        codSegCreditoCambiarLabel.setText("Cod Seguridad:");
        cuotasCreditoCambiarLabel.setText("Nº Cuotas:");
        nuevoMetodoPagoPanel.setVisible(true);

        String[] arr = new String[id_medicos.size()];
        for(int i=0; i<id_medicos.size(); i++){
            JSONObject jo = new JSONObject();
            jo.put(ID_MEDICX_KEY, id_medicos.get(i));
            arr[i] = validadorMedicx.getNomConID(jo).getString(NOMBRE_MEDICX_KEY);
        }
        medicxsBox.setModel(new DefaultComboBoxModel(arr));
    }
}
