package arg.com.utn.donatrack.logistica;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.estados.EnTraslado;
import arg.com.utn.donatrack.personas.PersonaHumana;
import arg.com.utn.donatrack.personas.contactos.ContactoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RutaTest {

  private Ruta ruta;
  private Entrega entrega;
  private ContactoMock contactoEntidad;
  private ContactoMock contactoDonador;

  @BeforeEach
  public void init() {
    contactoEntidad = new ContactoMock();
    contactoDonador = new ContactoMock();

    PersonaHumana donador = new PersonaHumana();
    donador.setContactos(List.of(contactoDonador));

    Donacion donacion = new Donacion();
    donacion.setDonadores(List.of(donador));

    EntidadBeneficiaria entidad = new EntidadBeneficiaria();
    entidad.setContactos(List.of(contactoEntidad));

    Entrega entrega = new Entrega(1L, "Calle Falsa 123", LocalDate.now(), entidad);
    entrega.setDonacionesAEntregar(List.of(donacion));
    this.entrega = entrega;

    Camion camion = new Camion("AB123CD", 100, 200, 1000);
    ruta = new Ruta(55L, camion, List.of(entrega));
  }

  @Test
  public void alIniciarseLaRuta_SeDebeNotificarAEntidadesYDonadoresConElLinkDelMapa() {

    ruta.Iniciarse();

    assertEquals(1, contactoEntidad.getMensajesRecibidos().size());
    assertTrue(contactoEntidad.getMensajesRecibidos().get(0).contains("http://donatrack.com/mapa/ruta/55"));

    assertEquals(1, contactoDonador.getMensajesRecibidos().size());
    assertTrue(contactoDonador.getMensajesRecibidos().get(0).contains("http://donatrack.com/mapa/ruta/55"));
  }

  @Test
  public void alIniciarseUnaRuta_LaEntregaYSusDonacionesCambianDeEstado() {
    ruta.Iniciarse();

    assertEquals(EstadoEntrega.ENTRASLADO, entrega.getEstadoEntrega());

    for (Donacion donacion : entrega.getDonaciones()) {
      assertTrue(donacion.getEstado() instanceof EnTraslado);
    }
  }

  @Test
  void alIniciarseUnaRuta_ConMultiplesEntregas_TodasCambianAEstraslado() {
    PersonaHumana donador2 = new PersonaHumana();
    donador2.setContactos(List.of(new ContactoMock()));

    Donacion donacion2 = new Donacion();
    donacion2.setDonadores(List.of(donador2));

    EntidadBeneficiaria entidad2 = new EntidadBeneficiaria();
    entidad2.setContactos(List.of(new ContactoMock()));

    Entrega entrega2 = new Entrega(2L, "Avenida Siempre Viva 742", LocalDate.now(), entidad2);
    entrega2.setDonacionesAEntregar(List.of(donacion2));

    Camion camion2 = new Camion("CD456EF", 150, 250, 1500);
    Ruta rutaMultiple = new Ruta(56L, camion2, List.of(entrega, entrega2));

    rutaMultiple.Iniciarse();

    assertEquals(EstadoEntrega.ENTRASLADO, entrega.getEstadoEntrega());
    for (Donacion d : entrega.getDonaciones()) {
      assertTrue(d.getEstado() instanceof EnTraslado);
    }

    assertEquals(EstadoEntrega.ENTRASLADO, entrega2.getEstadoEntrega());
    for (Donacion d : entrega2.getDonaciones()) {
      assertTrue(d.getEstado() instanceof EnTraslado);
    }
  }
}
