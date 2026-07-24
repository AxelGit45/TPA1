package arg.com.utn.donatrack.logistica;

import arg.com.utn.donatrack.dtos.DonacionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class EntregaTest {

  private Long entidadId;
  private Long donacionId1;
  private Long donacionId2;

  @BeforeEach
  void init() {
    entidadId = 1L;
    donacionId1 = 100L;
    donacionId2 = 200L;
  }

  private Entrega crearEntrega(LocalDate fecha, List<Long> donacionIds) {
    Entrega entrega = new Entrega(1L, "Calle Falsa 123", fecha, entidadId);
    entrega.setDonacionIds(donacionIds);
    return entrega;
  }

  @Test
  void crearEntrega_DebeInicializarConEstadoPendiente() {
    Entrega entrega = new Entrega(1L, "Calle Falsa 123", LocalDate.now(), entidadId);
    assertEquals(EstadoEntrega.PENDIENTE, entrega.getEstadoEntrega());
  }

  @Test
  void crearEntrega_DebeGuardarLosIds() {
    Entrega entrega = new Entrega(1L, "Calle Falsa 123", LocalDate.now(), entidadId);
    assertEquals(1L, entrega.getId());
    assertEquals("Calle Falsa 123", entrega.getDireccionEntidadBeneficiaria());
    assertEquals(entidadId, entrega.getEntidadBeneficiariaId());
  }

  @Test
  void setDonacionIds_DebeGuardarLasIds() {
    Entrega entrega = new Entrega();
    List<Long> ids = List.of(1L, 2L, 3L);
    entrega.setDonacionIds(ids);
    assertEquals(ids, entrega.getDonacionIds());
  }

  @Test
  void cambiarEstado_DebeActualizarElEstado() {
    Entrega entrega = new Entrega(1L, "Direccion", LocalDate.now(), entidadId);
    entrega.cambiarEstado(EstadoEntrega.ENTRASLADO);
    assertEquals(EstadoEntrega.ENTRASLADO, entrega.getEstadoEntrega());
  }

  @Test
  void entregaTardia_ConFechaPasada_RetornaTrue() {
    Entrega entrega = crearEntrega(LocalDate.now().minusDays(1), List.of(donacionId1));
    assertTrue(entrega.entregaTardia());
  }

  @Test
  void entregaTardia_ConFechaFutura_RetornaFalse() {
    Entrega entrega = crearEntrega(LocalDate.now().plusDays(1), List.of(donacionId1));
    assertFalse(entrega.entregaTardia());
  }

  @Test
  void entregaTardia_ConMismaFecha_RetornaFalse() {
    Entrega entrega = crearEntrega(LocalDate.now(), List.of(donacionId1));
    assertFalse(entrega.entregaTardia());
  }

  @Test
  void iniciarTraslado_DebeCambiarAEstadoEnTraslado() {
    Entrega entrega = crearEntrega(LocalDate.now(), List.of(donacionId1));
    entrega.iniciarTraslado();
    assertEquals(EstadoEntrega.ENTRASLADO, entrega.getEstadoEntrega());
  }

  @Test
  void confirmarRecepcionDeEntrega_DebeCambiarAEstadoEntregada() {
    Entrega entrega = crearEntrega(LocalDate.now(), List.of(donacionId1));
    entrega.confirmarRecepcionDeEntrega();
    assertEquals(EstadoEntrega.ENTREGADA, entrega.getEstadoEntrega());
  }

  @Test
  void informarNoRecepcion_SiEsTardia_DebeCambiarANoRecibida() {
    Entrega entrega = crearEntrega(LocalDate.now().minusDays(1), List.of(donacionId1));
    entrega.informarNoRecepcion();
    assertEquals(EstadoEntrega.NORECIBIDA, entrega.getEstadoEntrega());
  }

  @Test
  void informarNoRecepcion_SiNoEsTardia_NoCambiaDeEstado() {
    Entrega entrega = crearEntrega(LocalDate.now().plusDays(3), List.of(donacionId1));
    entrega.setEstadoEntrega(EstadoEntrega.ENTRASLADO);
    entrega.informarNoRecepcion();
    assertEquals(EstadoEntrega.ENTRASLADO, entrega.getEstadoEntrega());
  }

  @Test
  void volverAPendiente_SinServidor_NoCambiaEstado() {
    Entrega entrega = crearEntrega(LocalDate.now(), List.of(donacionId1));
    entrega.volverAPendiente();
    assertEquals(EstadoEntrega.PENDIENTE, entrega.getEstadoEntrega());
  }

  @Test
  void setCamionId_DebeGuardarElId() {
    Entrega entrega = new Entrega();
    entrega.setCamionId(42L);
    assertEquals(42L, entrega.getCamionId());
  }

  @Test
  void setFechaDeEntregaEsperada_DebeActualizarLaFecha() {
    Entrega entrega = new Entrega();
    LocalDate nuevaFecha = LocalDate.of(2026, 12, 25);
    entrega.setFechaDeEntregaEsperada(nuevaFecha);
    assertEquals(nuevaFecha, entrega.getFechaDeEntregaEsperada());
  }
}
