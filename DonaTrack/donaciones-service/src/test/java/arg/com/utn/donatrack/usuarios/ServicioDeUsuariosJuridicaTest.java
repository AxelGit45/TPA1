package arg.com.utn.donatrack.usuarios;

import arg.com.utn.donatrack.personas.PersonaJuridica;
import arg.com.utn.donatrack.personas.TipoRazonSocial;
import arg.com.utn.donatrack.personas.contactos.Mail;
import arg.com.utn.donatrack.repositorios.RepositorioUsuarios;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServicioDeUsuariosJuridicaTest implements SimplePersistenceTest {

  private final ServicioDeUsuarios servicio = new ServicioDeUsuarios();
  private final RepositorioUsuarios repositorio = new RepositorioUsuarios();

  @Test
  void registrarDonanteJuridicaGuardaLaPersonaConSuEmailInstitucional() {
    withTransaction(() -> servicio.registrarDonanteJuridica(entityManager(),
        "Arcos Plateados S.A.", "EMPRESA", "Alimentos", "Contacto@Arcos.com", "12345678"));
    entityManager().clear();

    Usuario usuario = repositorio.buscarPorEmail(entityManager(), "contacto@arcos.com");
    assertNotNull(usuario);
    assertEquals(Rol.DONANTE, usuario.getRol());

    List<PersonaJuridica> personas = entityManager()
        .createQuery("select p from PersonaJuridica p where p.usuarioId = :id", PersonaJuridica.class)
        .setParameter("id", usuario.getId())
        .getResultList();
    assertEquals(1, personas.size());

    PersonaJuridica persona = personas.get(0);
    assertEquals("Arcos Plateados S.A.", persona.getRazonSocial());
    assertEquals(TipoRazonSocial.EMPRESA, persona.getTipoRazonSocial());
    assertEquals("Alimentos", persona.getRubro());
    assertEquals(1, persona.getContactos().size());
    assertTrue(persona.getContactos().get(0) instanceof Mail);
  }

  @Test
  void noSePuedeRegistrarUnaJuridicaConUnEmailYaUsado() {
    withTransaction(() -> servicio.registrarDonanteJuridica(entityManager(),
        "Fundación Esperanza", "ONG", "Ayuda social", "info@esperanza.org", "12345678"));

    assertThrows(IllegalStateException.class, () ->
        servicio.registrarDonanteJuridica(entityManager(),
            "Otra ONG", "ONG", "Salud", "INFO@esperanza.org", "87654321"));
  }
}