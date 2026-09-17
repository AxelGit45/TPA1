package arg.com.utn.donatrack.personas;

import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PersonaJuridicaPersistenceTest implements SimplePersistenceTest {

  @Test
  void testCrearYPersistirPersonaJuridica() {
    // 1. Instanciamos una Persona Jurídica de prueba
    PersonaJuridica personaJuridica = new PersonaJuridica(
        "Arcos Plateados S.A.",
        TipoRazonSocial.EMPRESA,
        "Gastronomía",
        new ArrayList<>()
    );
    personaJuridica.setUltimaConexion(LocalDate.now());

    // 2. Persistimos dentro de una transacción
    withTransaction(() -> {
      entityManager().persist(personaJuridica);
    });

    // 3. Verificamos que se generó un ID
    assertNotNull(personaJuridica.getId());

    // 4. Consultamos y validamos contra la base de datos de test
    PersonaJuridica recuperada = entityManager().find(PersonaJuridica.class, personaJuridica.getId());
    assertNotNull(recuperada);
    assertEquals("Arcos Plateados S.A.", recuperada.getRazonSocial());
    assertEquals(TipoRazonSocial.EMPRESA, recuperada.getTipoRazonSocial());
    assertEquals("Gastronomía", recuperada.getRubro());
  }
}