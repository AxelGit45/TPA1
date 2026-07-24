package arg.com.utn.donatrack.estados;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.personas.PersonaHumana;
import arg.com.utn.donatrack.personas.contactos.ContactoMock;
import arg.com.utn.donatrack.repositorios.RepositorioAdministradores;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class EntregaFallidaTest {

  private ContactoMock contactoDonador;

  @BeforeEach
  public void init() {
    contactoDonador = new ContactoMock();
    RepositorioAdministradores.getAdministradores().clear();
  }

  @Test
  public void constructor_DebeInicializarCampos() {
    EntregaFallida ef = new EntregaFallida(1L, 2L, "Choque en la ruta");

    assertNotNull(ef);
  }

  @Test
  public void asignacionDonaciones_DebeLanzarExcepcion() {
    EntregaFallida ef = new EntregaFallida(1L, 2L, "Choque");
    EntidadBeneficiaria entidad = new EntidadBeneficiaria();
    PersonaHumana donador = new PersonaHumana();
    donador.setContactos(List.of(contactoDonador));
    Donacion donacion = new Donacion();

    assertThrows(RuntimeException.class, () -> {
      ef.asignacionDonaciones(List.of(entidad), donacion);
    });
  }

  @Test
  public void notificar_DebeNotificarAdmins() {
    EntregaFallida ef = new EntregaFallida(10L, 5L, "Robo");
    ef.notificar();
  }
}
