package arg.com.utn.donatrack.logistica;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RutaTest {

  private Long camionId;
  private Long entregaId1;
  private Long entregaId2;

  @BeforeEach
  public void init() {
    camionId = 10L;
    entregaId1 = 100L;
    entregaId2 = 200L;
  }

  @Test
  public void crearRuta_DebeInicializarCorrectamente() {
    Ruta ruta = new Ruta(1L, camionId, List.of(entregaId1));

    assertEquals(1L, ruta.getId());
    assertEquals(camionId, ruta.getCamionId());
    assertEquals(List.of(entregaId1), ruta.getEntregaIds());
    assertTrue(ruta.getLinkMapa().contains("http://donatrack.com/mapa/ruta/1"));
  }

  @Test
  public void crearRuta_ConMultiplesEntregas_DebeGuardarTodas() {
    Ruta ruta = new Ruta(2L, camionId, List.of(entregaId1, entregaId2));

    assertEquals(2, ruta.getEntregaIds().size());
    assertTrue(ruta.getEntregaIds().contains(entregaId1));
    assertTrue(ruta.getEntregaIds().contains(entregaId2));
  }

  @Test
  public void setCamionId_DebeActualizarElId() {
    Ruta ruta = new Ruta();
    ruta.setCamionId(99L);
    assertEquals(99L, ruta.getCamionId());
  }

  @Test
  public void setEntregaIds_DebeActualizarLasIds() {
    Ruta ruta = new Ruta();
    List<Long> nuevasIds = List.of(5L, 6L, 7L);
    ruta.setEntregaIds(nuevasIds);
    assertEquals(nuevasIds, ruta.getEntregaIds());
  }

  @Test
  public void linkMapa_DebeContenerElIdDeLaRuta() {
    Ruta ruta = new Ruta(42L, camionId, List.of(entregaId1));
    assertTrue(ruta.getLinkMapa().contains("42"));
  }
}
