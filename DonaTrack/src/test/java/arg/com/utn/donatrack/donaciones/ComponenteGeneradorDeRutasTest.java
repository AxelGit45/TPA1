package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.logistica.Camion;
import arg.com.utn.donatrack.logistica.Entrega;
import arg.com.utn.donatrack.logistica.Ruta;
import arg.com.utn.donatrack.personas.PersonaHumana;
import arg.com.utn.donatrack.personas.contactos.ContactoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ComponenteGeneradorDeRutasTest {

  private ComponenteGeneradorDeRutas adapter;
  private ComponenteExternoMock mockAdaptee;
  private List<Donacion> donacionesAsignadas;
  private List<Camion> camionesEnDeposito;

  @BeforeEach
  public void init() {
    donacionesAsignadas = new ArrayList<>();
    camionesEnDeposito = new ArrayList<>();
  }

  @Test
  public void planificacionDeRutas_DebeDelegarAlAdaptee() {
    mockAdaptee = new ComponenteExternoMock(List.of());
    adapter = new ComponenteGeneradorDeRutas(mockAdaptee);

    adapter.planificacionDeRutas(donacionesAsignadas, camionesEnDeposito);

    assertTrue(mockAdaptee.wasGenerarRutaInvocado(),
        "El adapter debió invocar generarRuta() en el adaptee");
  }

  @Test
  public void planificacionDeRutas_DebeRetornarLoQueElAdapteeDevuelve() {
    PersonaHumana donador = new PersonaHumana();
    donador.setContactos(List.of(new ContactoMock()));

    EntidadBeneficiaria entidad = new EntidadBeneficiaria();
    entidad.setContactos(List.of(new ContactoMock()));

    Camion camion = new Camion("AB123CD", 100, 200, 1000);
    Entrega entrega = new Entrega(1L, "Calle Falsa 123", LocalDate.now(), entidad);
    Ruta rutaEsperada = new Ruta(10L, camion, List.of(entrega));

    List<Ruta> rutasSimuladas = List.of(rutaEsperada);
    mockAdaptee = new ComponenteExternoMock(rutasSimuladas);
    adapter = new ComponenteGeneradorDeRutas(mockAdaptee);

    List<Ruta> resultado = adapter.planificacionDeRutas(donacionesAsignadas, camionesEnDeposito);

    assertEquals(1, resultado.size());
    assertEquals(rutaEsperada, resultado.get(0));
  }

  @Test
  public void planificacionDeRutas_ConListasVacias_DebeFuncionar() {
    mockAdaptee = new ComponenteExternoMock(List.of());
    adapter = new ComponenteGeneradorDeRutas(mockAdaptee);

    List<Ruta> resultado = adapter.planificacionDeRutas(donacionesAsignadas, camionesEnDeposito);

    assertNotNull(resultado);
    assertTrue(resultado.isEmpty());
  }

  @Test
  public void planificacionDeRutas_ConMultiplesRutas_DebeRetornarTodas() {
    Camion camion1 = new Camion("AB123CD", 100, 200, 1000);
    Camion camion2 = new Camion("CD456EF", 150, 250, 1500);

    EntidadBeneficiaria entidad1 = new EntidadBeneficiaria();
    entidad1.setContactos(List.of(new ContactoMock()));
    EntidadBeneficiaria entidad2 = new EntidadBeneficiaria();
    entidad2.setContactos(List.of(new ContactoMock()));

    Entrega entrega1 = new Entrega(1L, "Calle Falsa 123", LocalDate.now(), entidad1);
    Entrega entrega2 = new Entrega(2L, "Avenida Siempre Viva 742", LocalDate.now(), entidad2);

    Ruta ruta1 = new Ruta(10L, camion1, List.of(entrega1));
    Ruta ruta2 = new Ruta(20L, camion2, List.of(entrega2));

    mockAdaptee = new ComponenteExternoMock(List.of(ruta1, ruta2));
    adapter = new ComponenteGeneradorDeRutas(mockAdaptee);

    List<Ruta> resultado = adapter.planificacionDeRutas(donacionesAsignadas, camionesEnDeposito);

    assertEquals(2, resultado.size());
  }

  @Test
  public void planificacionDeRutas_DebePasarLasMismasListasAlAdaptee() {
    PersonaHumana donador = new PersonaHumana();
    donador.setContactos(List.of(new ContactoMock()));
    Bien bien = new Bien("Arroz", "foto.jpg", null, 1, Unidad.KILOGRAMO,
        null, EstadoUso.NUEVO, true, donador);
    Donacion donacion = new Donacion(List.of(bien));
    donacionesAsignadas.add(donacion);

    Camion camion = new Camion("AB123CD", 100, 200, 1000);
    camionesEnDeposito.add(camion);

    mockAdaptee = new ComponenteExternoMock(List.of());
    adapter = new ComponenteGeneradorDeRutas(mockAdaptee);

    adapter.planificacionDeRutas(donacionesAsignadas, camionesEnDeposito);

    assertSame(donacionesAsignadas, mockAdaptee.getDonacionesRecibidas(),
        "El adaptee debió recibir la misma referencia de donaciones");
    assertSame(camionesEnDeposito, mockAdaptee.getCamionesRecibidos(),
        "El adaptee debió recibir la misma referencia de camiones");
  }

  @Test
  public void planificacionDeRutas_ConConstructorPorDefecto_NoLanzaExcepciones() {
    adapter = new ComponenteGeneradorDeRutas();

    List<Ruta> resultado = adapter.planificacionDeRutas(donacionesAsignadas, camionesEnDeposito);

    assertNotNull(resultado);
  }
}
