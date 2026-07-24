package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.personas.PersonaHumana;
import arg.com.utn.donatrack.personas.contactos.ContactoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ComponenteGeneradorDeRutasTest {

  private ComponenteGeneradorDeRutas adapter;
  private ComponenteExternoMock mockAdaptee;
  private List<Donacion> donacionesAsignadas;
  private List<Long> camionesEnDeposito;

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

    Bien bien = new Bien("Arroz", "foto.jpg", null, 1, Unidad.KILOGRAMO,
        null, EstadoUso.NUEVO, true, donador);
    Donacion donacion = new Donacion(List.of(bien));
    donacionesAsignadas.add(donacion);

    Long rutaEsperada = 10L;
    List<Long> rutasSimuladas = List.of(rutaEsperada);
    mockAdaptee = new ComponenteExternoMock(rutasSimuladas);
    adapter = new ComponenteGeneradorDeRutas(mockAdaptee);

    List<Long> resultado = adapter.planificacionDeRutas(donacionesAsignadas, camionesEnDeposito);

    assertEquals(1, resultado.size());
    assertEquals(rutaEsperada, resultado.get(0));
  }

  @Test
  public void planificacionDeRutas_ConListasVacias_DebeFuncionar() {
    mockAdaptee = new ComponenteExternoMock(List.of());
    adapter = new ComponenteGeneradorDeRutas(mockAdaptee);

    List<Long> resultado = adapter.planificacionDeRutas(donacionesAsignadas, camionesEnDeposito);

    assertNotNull(resultado);
    assertTrue(resultado.isEmpty());
  }

  @Test
  public void planificacionDeRutas_ConMultiplesRutas_DebeRetornarTodas() {
    Long ruta1 = 10L;
    Long ruta2 = 20L;

    mockAdaptee = new ComponenteExternoMock(List.of(ruta1, ruta2));
    adapter = new ComponenteGeneradorDeRutas(mockAdaptee);

    List<Long> resultado = adapter.planificacionDeRutas(donacionesAsignadas, camionesEnDeposito);

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

    Long camionId = 1L;
    camionesEnDeposito.add(camionId);

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

    List<Long> resultado = adapter.planificacionDeRutas(donacionesAsignadas, camionesEnDeposito);

    assertNotNull(resultado);
  }
}
