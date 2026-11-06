package arg.com.utn.donatrack.usuarios;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.personas.contactos.Mail;
import arg.com.utn.donatrack.repositorios.RepositorioUsuarios;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServicioDeUsuariosEntidadTest implements SimplePersistenceTest {

  private final ServicioDeUsuarios servicio = new ServicioDeUsuarios();
  private final RepositorioUsuarios repositorio = new RepositorioUsuarios();

  @Test
  void registrarEntidadGuardaLaEntidadConSuEmailInstitucional() {
    withTransaction(() -> servicio.registrarEntidadBeneficiaria(entityManager(),
        "Comedor Los Pibes", "Calle Falsa 123, CABA", "Comedor@LosPibes.org", "12345678"));
    entityManager().clear();

    Usuario usuario = repositorio.buscarPorEmail(entityManager(), "comedor@lospibes.org");
    assertNotNull(usuario);
    assertEquals(Rol.ENTIDAD, usuario.getRol());

    List<EntidadBeneficiaria> entidades = entityManager()
        .createQuery("select e from EntidadBeneficiaria e where e.usuarioId = :id", EntidadBeneficiaria.class)
        .setParameter("id", usuario.getId())
        .getResultList();
    assertEquals(1, entidades.size());

    EntidadBeneficiaria entidad = entidades.get(0);
    assertEquals("Comedor Los Pibes", entidad.getRazonSocial());
    assertEquals("Calle Falsa 123, CABA", entidad.getDireccion());
    assertEquals(1, entidad.getContactos().size());
    assertTrue(entidad.getContactos().get(0) instanceof Mail);
  }

  @Test
  void noSePuedeRegistrarUnaEntidadConUnEmailYaUsado() {
    withTransaction(() -> servicio.registrarEntidadBeneficiaria(entityManager(),
        "Escuela Rural 7", "Ruta 5 km 40", "escuela7@educacion.org", "12345678"));

    assertThrows(IllegalStateException.class, () ->
        servicio.registrarEntidadBeneficiaria(entityManager(),
            "Otra Escuela", "Ruta 9 km 10", "ESCUELA7@educacion.org", "87654321"));
  }
}