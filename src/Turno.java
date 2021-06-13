public class Turno {
    private Paciente paciente;
    private Fecha fecha;
    private int id_medico;
    private Metodo_de_pago forma_pago;
    private int precio;

    public Turno(int id_medico, String nombre_paciente, String dni, int dia, int mes, int año, String hora, String ){
        this.paciente = new Paciente(nombre, dni);
        this.fecha    = new Fecha(dia,mes,año,hora);
        
    }

    public Turno(int id_medico, String nombre_paciente, String dni, int dia, int mes, int año, String hora){
        this.paciente = new Paciente(nombre, dni);
        this.fecha    = new Fecha(dia,mes,año,hora);
        
    }

    public Turno(int id_medico, String nombre_paciente, String dni, int dia, int mes, int año, String hora){
        this.paciente = new Paciente(nombre, dni);
        this.fecha    = new Fecha(dia,mes,año,hora);
        
    }





}
