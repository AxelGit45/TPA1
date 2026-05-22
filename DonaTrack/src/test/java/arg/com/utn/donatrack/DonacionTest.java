package arg.com.utn.donatrack;

import Modelos.Bien;
import Modelos.Donacion;
import Modelos.Perecedero;
import Enums.EstadoDonacion;
import Enums.TipoCategoria;
import Services.DonacionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DonacionTest {

  private DonacionService donacionService;
  private Donacion donacionOriginal;

  @BeforeEach
  void setUp() {
    donacionService = new DonacionService();  // sin Spring, a mano

    Bien arroz1 = new Bien("GalloOro - 1kg", "foto.jpg", 5, TipoCategoria.ALIMENTO, "arroz");
    arroz1.setTipo(new Perecedero("GalloOro - 1 kg", "foto.jpg", 5, TipoCategoria.ALIMENTO, "arroz", "2025-12-01", 1));

    Bien arroz2 = new Bien("Dos hermanos - 500g", "foto.jpg", 3, TipoCategoria.ALIMENTO, "arroz");
    arroz2.setTipo(new Perecedero("Arroz 500g", "foto.jpg", 3, TipoCategoria.ALIMENTO, "arroz", "2025-12-01", 1));

    Bien arroz3 = new Bien("Dos hermanos - 1kg", "foto.jpg", 2, TipoCategoria.ALIMENTO, "arroz");
    arroz3.setTipo(new Perecedero("Dos hermanos - 1kg", "foto.jpg", 2, TipoCategoria.ALIMENTO, "arroz", "2026-06-01", 1));

    donacionOriginal = new Donacion("Juan", List.of(arroz1, arroz2, arroz3), 1, "", EstadoDonacion.ENDEPOSITO);
  }

  @Test
  void deberiaGenerarDosDonacionesPorFechaDistinta() {
    List<Donacion> resultado = donacionService.dividirPorSubcategoria(donacionOriginal);
    assertEquals(2, resultado.size());
  }

  @Test
  void deberiaAgruparPerecederosDeMismaFecha1() {
    List<Donacion> resultado = donacionService.dividirPorSubcategoria(donacionOriginal);

    Donacion grupoDosArroz = resultado.stream()
        .filter(d -> d.getBienes().size() == 2)
        .findFirst()
        .orElse(null);

    assertNotNull(grupoDosArroz);


    List<String> fechas = grupoDosArroz.getBienes().stream()
        .map(b -> ((Perecedero) b.getTipoDelBien()).getFechaVencimiento())
        .distinct()
        .toList();

    assertEquals(1, fechas.size());
    assertEquals("2025-12-01", fechas.get(0));
  }
}