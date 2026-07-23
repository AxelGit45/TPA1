package arg.com.utn.donatrack.logistica;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.estados.EnDeposito;
import arg.com.utn.donatrack.estados.EntregaFallida;
import arg.com.utn.donatrack.estados.Entregada;
import arg.com.utn.donatrack.personas.PersonaHumana;
import arg.com.utn.donatrack.personas.contactos.ContactoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class EntregaTest {

  private ContactoMock contactoEntidad;
  private ContactoMock contactoDonador;
  private ContactoMock contactoDonador2;
  private PersonaHumana donador;
  private PersonaHumana donador2;
  private Donacion donacion;
  private Donacion donacion2;
  private EntidadBeneficiaria entidad;
  private Camion camion;

  @BeforeEach
  void init() {
    contactoEntidad = new ContactoMock();
    contactoDonador = new ContactoMock();
    contactoDonador2 = new ContactoMock();

    donador = new PersonaHumana();
    donador.setContactos(List.of(contactoDonador));

    donador2 = new PersonaHumana();
    donador2.setContactos(List.of(contactoDonador2));

    donacion = new Donacion();
    donacion.setDonadores(List.of(donador));

    donacion2 = new Donacion();
    donacion2.setDonadores(List.of(donador2));

    entidad = new EntidadBeneficiaria();
    entidad.setContactos(List.of(contactoEntidad));

    camion = new Camion("AB123CD", 100, 200, 1000);
  }

  private Entrega crearEntrega(LocalDate fecha, List<Donacion> donaciones) {
    Entrega entrega = new Entrega(1L, "Calle Falsa 123", fecha, entidad);
    entrega.setDonacionesAEntregar(donaciones);
    entrega.setCamionQueLaEntrego(camion);
    return entrega;
  }

  // === confirmarRecepcionDeEntrega ===

  @Test
  void confirmarRecepcion_DeLaEntrega_CambiaAEntregada_YLasDonacionesTambien() {
    Entrega entrega = crearEntrega(LocalDate.now(), List.of(donacion));

    entrega.confirmarRecepcionDeEntrega();

    assertEquals(EstadoEntrega.ENTREGADA, entrega.getEstadoEntrega());
    assertTrue(donacion.getEstado() instanceof Entregada);
  }

  // === informarNoRecepcion ===

  @Test
  void informarNoRecepcion_SiEsTardia_LaEntregaCambiaANoRecibida_YLasDonacionesAFallida() {
    Entrega entrega = crearEntrega(LocalDate.now().minusDays(1), List.of(donacion));

    entrega.informarNoRecepcion();

    assertEquals(EstadoEntrega.NORECIBIDA, entrega.getEstadoEntrega());
    assertTrue(donacion.getEstado() instanceof EntregaFallida);
  }

  @Test
  void informarNoRecepcion_SiNoEsTardia_LaEntregaNoCambiaDeEstado() {
    Entrega entrega = crearEntrega(LocalDate.now().plusDays(3), List.of(donacion));
    entrega.setEstadoEntrega(EstadoEntrega.ENTRASLADO);

    entrega.informarNoRecepcion();

    assertEquals(EstadoEntrega.ENTRASLADO, entrega.getEstadoEntrega());
  }

  // === entregaTardia ===

  @Test
  void entregaTardia_ConFechaPasada_RetornaTrue() {
    Entrega entrega = crearEntrega(LocalDate.now().minusDays(1), List.of(donacion));

    assertTrue(entrega.entregaTardia());
  }

  @Test
  void entregaTardia_ConFechaFutura_RetornaFalse() {
    Entrega entrega = crearEntrega(LocalDate.now().plusDays(1), List.of(donacion));

    assertFalse(entrega.entregaTardia());
  }

  @Test
  void entregaTardia_ConMismaFecha_RetornaFalse() {
    Entrega entrega = crearEntrega(LocalDate.now(), List.of(donacion));

    assertFalse(entrega.entregaTardia());
  }

  // === volverAPendiente ===

  @Test
  void volverAPendiente_ConDonacionEnDeposito_VuelveAPendiente() {
    Entrega entrega = crearEntrega(LocalDate.now(), List.of(donacion));
    donacion.cambiarEstado(new EnDeposito());

    entrega.volverAPendiente();

    assertEquals(EstadoEntrega.PENDIENTE, entrega.getEstadoEntrega());
  }

  @Test
  void volverAPendiente_SinDonacionEnDeposito_NoCambia() {
    Entrega entrega = crearEntrega(LocalDate.now(), List.of(donacion));
    donacion.iniciarTraslado(camion);
    entrega.setEstadoEntrega(EstadoEntrega.ENTRASLADO);

    entrega.volverAPendiente();

    assertEquals(EstadoEntrega.ENTRASLADO, entrega.getEstadoEntrega());
  }

  // === Tests con 2 donaciones ===

  @Test
  void confirmarRecepcion_ConDosDonaciones_AmbasCambianAEntregada() {
    Entrega entrega = crearEntrega(LocalDate.now(), List.of(donacion, donacion2));

    entrega.confirmarRecepcionDeEntrega();

    assertEquals(EstadoEntrega.ENTREGADA, entrega.getEstadoEntrega());
    assertTrue(donacion.getEstado() instanceof Entregada);
    assertTrue(donacion2.getEstado() instanceof Entregada);
  }

  @Test
  void informarNoRecepcion_SiEsTardia_ConDosDonaciones_AmbasCambianAFallida() {
    Entrega entrega = crearEntrega(LocalDate.now().minusDays(1), List.of(donacion, donacion2));

    entrega.informarNoRecepcion();

    assertEquals(EstadoEntrega.NORECIBIDA, entrega.getEstadoEntrega());
    assertTrue(donacion.getEstado() instanceof EntregaFallida);
    assertTrue(donacion2.getEstado() instanceof EntregaFallida);
  }

  @Test
  void volverAPendiente_ConUnaDonacionEnDepositoYOtraNo_VuelveAPendiente() {
    Entrega entrega = crearEntrega(LocalDate.now(), List.of(donacion, donacion2));
    donacion.cambiarEstado(new EnDeposito());
    donacion2.iniciarTraslado(camion);

    entrega.volverAPendiente();

    assertEquals(EstadoEntrega.PENDIENTE, entrega.getEstadoEntrega());
  }

  @Test
  void volverAPendiente_ConDosDonacionesEnEnTraslado_NoCambia() {
    Entrega entrega = crearEntrega(LocalDate.now(), List.of(donacion, donacion2));
    donacion.iniciarTraslado(camion);
    donacion2.iniciarTraslado(camion);
    entrega.setEstadoEntrega(EstadoEntrega.ENTRASLADO);

    entrega.volverAPendiente();

    assertEquals(EstadoEntrega.ENTRASLADO, entrega.getEstadoEntrega());
  }
}