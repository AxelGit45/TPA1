package arg.com.utn.donatrack.tareas;

import arg.com.utn.donatrack.personas.PersonaHumana;
import arg.com.utn.donatrack.personas.contactos.ContactoMock;
import arg.com.utn.donatrack.repositorios.RepositorioPersonas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VerificadorDeInactividadTest {

  private VerificadorDeInactividad verificador;
  private PersonaHumana personaInactiva;
  private PersonaHumana personaActiva;
  private ContactoMock contactoInactivo;
  private ContactoMock contactoActivo;

  @BeforeEach
  public void init() {
    verificador = new VerificadorDeInactividad();
    RepositorioPersonas.getPersonas().clear();

    contactoInactivo = new ContactoMock();
    personaInactiva = new PersonaHumana();
    personaInactiva.setUltimaConexion(LocalDate.now().minusDays(25));
    personaInactiva.setContactos(List.of(contactoInactivo));

    contactoActivo = new ContactoMock();
    personaActiva = new PersonaHumana();
    personaActiva.setUltimaConexion(LocalDate.now().minusDays(5));
    personaActiva.setContactos(List.of(contactoActivo));

    RepositorioPersonas.agregar(personaInactiva);
    RepositorioPersonas.agregar(personaActiva);
  }

  @Test
  public void verificar_DebeNotificarSoloAPersonasConMasDe20DiasDeInactividad() {
    verificador.verificar();

    assertEquals(1, contactoInactivo.getMensajesRecibidos().size());
    assertEquals("Hola! Hace mucho no te vemos en DonaTrack", contactoInactivo.getMensajesRecibidos().get(0));

    assertEquals(0, contactoActivo.getMensajesRecibidos().size());
  }
}
