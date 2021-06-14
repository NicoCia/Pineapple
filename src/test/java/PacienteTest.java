import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PacienteTest {
    Paciente paciente = new Paciente("Nombre Apellido","392658471");
    @Test
    public void testGetNombre() {
        String name = "Nombre Apellido";
        assertEquals(paciente.getNombre(), name);
    }
    @Test
    public void testGetDni() {
        String dni = "392658471";
        assertEquals(paciente.getDni(), dni);
    }
}