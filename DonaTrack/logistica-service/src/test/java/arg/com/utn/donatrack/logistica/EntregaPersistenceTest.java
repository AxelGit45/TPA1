package arg.com.utn.donatrack.logistica;

import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EntregaPersistenceTest implements SimplePersistenceTest {

  @Test
  void testCrearYPersistirEntrega() {
    // 1. Instanciamos una Entrega de prueba
    Entrega entrega = new Entrega(
        null,
        "Av. Siempreviva 742",
        LocalDate.now().plusDays(5),
        10L
    );
    entrega.setDonacionIds(new ArrayList<>(Arrays.asList(1L, 2L)));
    entrega.setCamionId(99L);

    // 2. Persistimos dentro de una transacción
    withTransaction(() -> {
      entityManager().persist(entrega);
    });

    // 3. Verificamos que se generó un ID
    assertNotNull(entrega.getId());

    // 4. Consultamos y validamos contra la base de datos de test
    Entrega recuperada = entityManager().find(Entrega.class, entrega.getId());
    assertNotNull(recuperada);
    assertEquals("Av. Siempreviva 742", recuperada.getDireccionEntidadBeneficiaria());
    assertEquals(EstadoEntrega.PENDIENTE, recuperada.getEstadoEntrega());
    assertEquals(10L, recuperada.getEntidadBeneficiariaId());
    assertEquals(99L, recuperada.getCamionId());
    assertEquals(2, recuperada.getDonacionIds().size());
  }
}