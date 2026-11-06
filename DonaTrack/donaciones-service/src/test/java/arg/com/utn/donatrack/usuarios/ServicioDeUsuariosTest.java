package arg.com.utn.donatrack.usuarios;

import arg.com.utn.donatrack.personas.PersonaHumana;
import arg.com.utn.donatrack.personas.contactos.Mail;
import arg.com.utn.donatrack.personas.contactos.WhatsApp;
import arg.com.utn.donatrack.repositorios.RepositorioUsuarios;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServicioDeUsuariosTest implements SimplePersistenceTest {

  private final ServicioDeUsuarios servicio = new ServicioDeUsuarios();
  private final RepositorioUsuarios repositorio = new RepositorioUsuarios();

  private void registrar(String email, String telefono, String whatsapp, String medio) {
    withTransaction(() -> servicio.registrarDonante(entityManager(), "Ana", "Pérez", 30, 12345678,
        "Femenino", "Av. Siempre Viva 742", email, telefono, whatsapp, medio, "1234"));
  }

  private PersonaHumana buscarPersona(String email) {
    Usuario usuario = repositorio.buscarPorEmail(entityManager(), email);
    List<PersonaHumana> personas = entityManager()
        .createQuery("select p from PersonaHumana p where p.usuarioId = :id", PersonaHumana.class)
        .setParameter("id", usuario.getId())
        .getResultList();
    assertEquals(1, personas.size());
    return personas.get(0);
  }

  @Test
  void registrarDonanteGuardaTodosLosDatosYLosContactos() {
    registrar("Ana@Mail.com", "1111", "2222", "WHATSAPP");
    entityManager().clear();

    Usuario usuario = repositorio.buscarPorEmail(entityManager(), "ana@mail.com");
    assertNotNull(usuario);
    assertEquals(Rol.DONANTE, usuario.getRol());

    PersonaHumana persona = buscarPersona("ana@mail.com");
    assertEquals("Ana", persona.getNombre());
    assertEquals("Pérez", persona.getApellido());
    assertEquals(30, persona.getEdad());
    assertEquals(12345678, persona.getNumeroDeDocumento());
    assertEquals("Femenino", persona.getGenero());
    assertEquals("Av. Siempre Viva 742", persona.getDireccion());
    assertEquals(3, persona.getContactos().size());
    assertTrue(persona.getPredeterminado() instanceof WhatsApp);
  }

  @Test
  void siElMedioElegidoNoSeCargoElPredeterminadoEsElEmail() {
    registrar("beto@mail.com", "", "", "TELEFONO");
    entityManager().clear();

    PersonaHumana persona = buscarPersona("beto@mail.com");
    assertEquals(1, persona.getContactos().size());
    assertTrue(persona.getPredeterminado() instanceof Mail);
  }

  @Test
  void autenticarDevuelveElUsuarioSoloConContrasenaCorrecta() {
    registrar("caro@mail.com", "", "", "EMAIL");

    assertNotNull(servicio.autenticar(entityManager(), "caro@mail.com", "1234"));
    assertNull(servicio.autenticar(entityManager(), "caro@mail.com", "otra"));
    assertNull(servicio.autenticar(entityManager(), "noexiste@mail.com", "1234"));
  }

  @Test
  void noSePuedeRegistrarDosVecesElMismoEmail() {
    registrar("dani@mail.com", "", "", "EMAIL");

    assertThrows(IllegalStateException.class, () ->
        servicio.registrarDonante(entityManager(), "Otra", "Persona", 40, 11111111,
            "Otro", "Calle 1", "DANI@mail.com", "", "", "EMAIL", "5678"));
  }
}