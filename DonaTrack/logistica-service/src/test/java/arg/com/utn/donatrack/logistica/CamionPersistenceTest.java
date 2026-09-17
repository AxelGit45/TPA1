package arg.com.utn.donatrack.logistica;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CamionPersistenceTest implements SimplePersistenceTest {

  @Test
  void testCrearYPersistirCamion() {
    // 1. Instanciamos un Camion de prueba
    Camion camion = new Camion("AB123CD", 50, 3, 5000);
    camion.setIdGps("GPS-001");
    camion.setLatitud(-34.6037);
    camion.setLongitud(-58.3816);
    camion.setBienesTransportados(
        new ArrayList<>(Arrays.asList(1L, 2L, 3L))
    );

    // 2. Persistimos dentro de una transacción
    withTransaction(() -> {
      entityManager().persist(camion);
    });

    // 3. Verificamos que se generó un ID
    assertNotNull(camion.getId());

    // 4. Consultamos y validamos contra la base de datos de test
    Camion recuperado = entityManager().find(Camion.class, camion.getId());
    assertNotNull(recuperado);
    assertEquals("AB123CD", recuperado.getPatente());
    assertEquals(50, recuperado.getVolumen());
    assertEquals(3, recuperado.getAltura());
    assertEquals(5000, recuperado.getCapacidadDeCarga());
    assertEquals("GPS-001", recuperado.getIdGps());
    assertEquals(-34.6037, recuperado.getLatitud());
    assertEquals(-58.3816, recuperado.getLongitud());
    assertEquals(3, recuperado.getBienesTransportados().size());
  }

}
