package arg.com.utn.donatrack.personas;

import arg.com.utn.donatrack.personas.contactos.Mail;
import arg.com.utn.donatrack.personas.contactos.Telefono;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PersonaConContactosPersistenceTest implements SimplePersistenceTest {

  @Test
  void testPersistirPersonaConContactos() {
    // 1. Creamos la persona humana
    PersonaHumana persona = new PersonaHumana(
        "María",
        "Gómez",
        28,
        87654321,
        "Femenino",
        "Av. Santa Fe 4567",
        null,
        null
    );
    persona.setUltimaConexion(LocalDate.now());

    // 2. Creamos y agregamos contactos usando el método auxiliar
    persona.agregarContacto(new Mail("maria.gomez@mail.com"));
    persona.agregarContacto(new Telefono("+5491112345678"));

    // 3. Persistimos en la base de datos dentro de una transacción
    withTransaction(() -> {
      entityManager().persist(persona);
    });

    // 4. Verificamos que se generó ID
    assertNotNull(persona.getId());

    // 5. Consultamos la persona desde la base de datos
    PersonaHumana recuperada = entityManager().find(PersonaHumana.class, persona.getId());
    assertNotNull(recuperada);
    assertEquals("María", recuperada.getNombre());

    // 6. Verificamos que los contactos se guardaron y recuperaron correctamente
    assertNotNull(recuperada.getContactos());
    assertEquals(2, recuperada.getContactos().size());
  }
}