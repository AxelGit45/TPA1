package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.donaciones.Subcategoria;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NecesidadPersistenceTest implements SimplePersistenceTest {

  @Test
  void testPersistirNecesidadExtraordinaria() {
    NecesidadExtraordinaria necesidad = new NecesidadExtraordinaria(
        null,
        new Subcategoria("Ropa", null, false, true),
        5,
        "Abrigos de invierno",
        false,
        0
    );

    withTransaction(() -> entityManager().persist(necesidad));
    assertNotNull(necesidad.getId());

    NecesidadExtraordinaria recuperada = entityManager().find(NecesidadExtraordinaria.class, necesidad.getId());
    assertNotNull(recuperada);
    assertEquals("Abrigos de invierno", recuperada.getDescripcion());
    assertEquals(5, recuperada.getCantidadNecesitada());
  }

  @Test
  void testPersistirNecesidadRecurrente() {
    NecesidadRecurrente necesidad = new NecesidadRecurrente(
        null,
        new Subcategoria("Alimentos", null, false, true),
        50,
        "Fideos y arroz",
        false,
        10,
        LocalDate.now().plusMonths(2)
    );

    withTransaction(() -> entityManager().persist(necesidad));
    assertNotNull(necesidad.getId());

    NecesidadRecurrente recuperada = entityManager().find(NecesidadRecurrente.class, necesidad.getId());
    assertNotNull(recuperada);
    assertEquals("Fideos y arroz", recuperada.getDescripcion());
    assertEquals(50, recuperada.getCantidadNecesitada());
    assertEquals(10, recuperada.getRecibidos());
  }
}