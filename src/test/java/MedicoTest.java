import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MedicoTest {
    @Test
    public void testGetNombre() {
        Medico medico = new Medico("Nombre Apellido","1234","4321","12345ascsd",1);
        String name="Nombre Apellido";
        assertEquals(medico.getNombre(), name);
    }
    @Test
    public void testGetMatricula() {
        Medico medico = new Medico("Nombre Apellido","1234","4321","12345ascsd",1);
        String matricula="1234";
        assertEquals(medico.getMatricula(), matricula);
    }
    @Test
    public void testGetDni() {
        Medico medico = new Medico("Nombre Apellido","1234","4321","12345ascsd",1);
        String dni="4321";
        assertEquals(medico.getDni(), dni);
    }
    @Test
    public void testgetContrasenia() {
        Medico medico = new Medico("Nombre Apellido","1234","4321","12345ascsd",1);
        String pass="12345ascsd";
        assertEquals(medico.getContrasenia(), pass);
    }
    @Test
    public void getId() {
        Medico medico = new Medico("Nombre Apellido","1234","4321","12345ascsd",1);
        int id=1;
        assertEquals(medico.getId(), id);
    }
}