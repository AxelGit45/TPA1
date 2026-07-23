package arg.com.utn.donatrack.estados;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.logistica.Camion;
import arg.com.utn.donatrack.logistica.Entrega;
import arg.com.utn.donatrack.personas.PersonaHumana;
import arg.com.utn.donatrack.personas.contactos.ContactoMock;
import arg.com.utn.donatrack.repositorios.RepositorioAdministradores;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntregaFallidaTest {

  private EntregaFallida entregaFallida;
  private ContactoMock contactoEntidad;
  private ContactoMock contactoDonador;

  @BeforeEach
  public void init() {
    contactoEntidad = new ContactoMock();
    contactoDonador = new ContactoMock();

    RepositorioAdministradores.getAdministradores().clear();

    PersonaHumana donador = new PersonaHumana();
    donador.setContactos(List.of(contactoDonador));

    Donacion donacion = new Donacion();
    donacion.setDonadores(List.of(donador));

    EntidadBeneficiaria entidad = new EntidadBeneficiaria();
    entidad.setContactos(List.of(contactoEntidad));

    Entrega entrega = new Entrega(1L, "Calle Falsa 123", LocalDate.now(), entidad);
    entrega.setDonacionesAEntregar(List.of(donacion));

    Camion camion = new Camion("AB123CD", 100, 200, 1000);

    entregaFallida = new EntregaFallida(entrega, camion, "Choque en la ruta");
  }

  @Test
  public void notificar_DebeEnviarMensajeDeFalloCorrecto() {

    entregaFallida.notificar();

    assertEquals(1, contactoEntidad.getMensajesRecibidos().size());
    assertEquals("La entrega ha fallado.", contactoEntidad.getMensajesRecibidos().get(0));

    assertEquals(1, contactoDonador.getMensajesRecibidos().size());
    assertEquals("La entrega ha fallado.", contactoDonador.getMensajesRecibidos().get(0));
  }
}
