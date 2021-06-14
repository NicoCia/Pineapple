import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedicoTest {
    Medico medico = new Medico("Nombre Apellido","1234","4321","12345ascsd",1);
    Paciente paciente = new Paciente("Nombre Apellido","392658471");
    @Test
    public void testGetNombre() {
        String name="Nombre Apellido";
        assertEquals(medico.getNombre(), name);
    }
    @Test
    public void testGetMatricula() {
        String matricula="1234";
        assertEquals(medico.getMatricula(), matricula);
    }
    @Test
    public void testGetDni() {
        String dni="4321";
        assertEquals(medico.getDni(), dni);
    }
    @Test
    public void testgetContrasenia() {
        String pass="12345ascsd";
        assertEquals(medico.getContrasenia(), pass);
    }
    @Test
    public void getId() {
        int id=1;
        assertEquals(medico.getId(), id);
    }
    @Test
    public void testPacienteGetNombre() {
        String name = "Nombre Apellido";
        assertEquals(paciente.getNombre(), name);
    }
    @Test
    public void testPacienteGetDni() {
        String dni = "392658471";
        assertEquals(paciente.getDni(), dni);
    }
}
