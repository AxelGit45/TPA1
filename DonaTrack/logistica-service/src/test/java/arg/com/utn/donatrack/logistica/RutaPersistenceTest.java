package arg.com.utn.donatrack.logistica;

import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RutaPersistenceTest implements SimplePersistenceTest {

  @Test
  void testCrearYPersistirRuta() {
    // 1. Instanciamos una Ruta de prueba
    Ruta ruta = new Ruta(null, 5L, new ArrayList<>(Arrays.asList(10L, 20L)));

    // 2. Persistimos dentro de una transacción
    withTransaction(() -> {
      entityManager().persist(ruta);
    });

    // 3. Verificamos que se generó un ID
    assertNotNull(ruta.getId());

    // 4. Consultamos y validamos contra la base de datos de test
    Ruta recuperada = entityManager().find(Ruta.class, ruta.getId());
    assertNotNull(recuperada);
    assertEquals(5L, recuperada.getCamionId());
    assertEquals(2, recuperada.getEntregaIds().size());
    assertEquals("http://donatrack.com/mapa/ruta/" + ruta.getId(), recuperada.getLinkMapa());
  }
}