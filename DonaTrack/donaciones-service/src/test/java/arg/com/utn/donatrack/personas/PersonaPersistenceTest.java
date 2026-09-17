package arg.com.utn.donatrack.personas;

import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonaPersistenceTest implements SimplePersistenceTest {

  @Test
  void testCrearYPersistirPersonaHumana() {
    // Creamos una instancia concreta (PersonaHumana hereda de Persona)
    PersonaHumana persona = new PersonaHumana(
        "Juan",
        "Pérez",
        30,
        12345678,
        "Masculino",
        "Av. Corrientes 1234",
        null,
        null
    );
    persona.setUltimaConexion(LocalDate.now());

    // Persistimos dentro de una transacción
    withTransaction(() -> {
      entityManager().persist(persona);
    });

    // Verificamos que se generó un ID automáticamente en la base de datos de HSQLDB
    assertNotNull(persona.getId());

    // Consultamos la persona desde la base de datos
    PersonaHumana recuperada = entityManager().find(PersonaHumana.class, persona.getId());
    assertNotNull(recuperada);
    assertEquals("Juan", recuperada.getNombre());
    assertEquals("Pérez", recuperada.getApellido());
  }
}