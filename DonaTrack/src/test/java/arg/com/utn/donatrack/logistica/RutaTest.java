package arg.com.utn.donatrack.logistica;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
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

  }
}
