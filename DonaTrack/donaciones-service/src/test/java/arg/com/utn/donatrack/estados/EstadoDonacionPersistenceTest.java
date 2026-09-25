package arg.com.utn.donatrack.estados;

import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EstadoDonacionPersistenceTest implements SimplePersistenceTest {

  @Test
  void testPersistirEnDeposito() {
    EnDeposito estado = new EnDeposito();
    withTransaction(() -> entityManager().persist(estado));
    assertNotNull(estado.getId());

    EnDeposito recuperado = entityManager().find(EnDeposito.class, estado.getId());
    assertNotNull(recuperado);
  }

  @Test
  void testPersistirEnTraslado() {
    EnTraslado estado = new EnTraslado(42L);
    withTransaction(() -> entityManager().persist(estado));
    assertNotNull(estado.getId());

    EnTraslado recuperado = entityManager().find(EnTraslado.class, estado.getId());
    assertNotNull(recuperado);
    assertEquals(42L, recuperado.getCamionId());
  }

  @Test
  void testPersistirListaParaEntregar() {
    ListaParaEntregar estado = new ListaParaEntregar("Ruta A-1");
    withTransaction(() -> entityManager().persist(estado));
    assertNotNull(estado.getId());

    ListaParaEntregar recuperado = entityManager().find(ListaParaEntregar.class, estado.getId());
    assertNotNull(recuperado);
    assertEquals("Ruta A-1", recuperado.getRuta());
  }

  @Test
  void testPersistirEntregada() {
    Entregada estado = new Entregada(10L, 5L);
    withTransaction(() -> entityManager().persist(estado));
    assertNotNull(estado.getId());

    Entregada recuperado = entityManager().find(Entregada.class, estado.getId());
    assertNotNull(recuperado);
    assertEquals(10L, recuperado.getEntregaId());
    assertEquals(5L, recuperado.getCamionId());
  }

  @Test
  void testPersistirEntregaFallida() {
    EntregaFallida estado = new EntregaFallida(11L, 6L, "Destinatario ausente");
    withTransaction(() -> entityManager().persist(estado));
    assertNotNull(estado.getId());

    EntregaFallida recuperado = entityManager().find(EntregaFallida.class, estado.getId());
    assertNotNull(recuperado);
  }

  @Test
  void testPersistirVencida() {
    Vencida estado = new Vencida();
    withTransaction(() -> entityManager().persist(estado));
    assertNotNull(estado.getId());

    Vencida recuperado = entityManager().find(Vencida.class, estado.getId());
    assertNotNull(recuperado);
  }

  @Test
  void testPersistirAsignacionRealizada() {
    AsignacionRealizada estado = new AsignacionRealizada(null, null);
    withTransaction(() -> entityManager().persist(estado));
    assertNotNull(estado.getId());

    AsignacionRealizada recuperado = entityManager().find(AsignacionRealizada.class, estado.getId());
    assertNotNull(recuperado);
  }
}
