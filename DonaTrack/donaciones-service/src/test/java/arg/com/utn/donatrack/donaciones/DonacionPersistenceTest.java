package arg.com.utn.donatrack.donaciones;
import arg.com.utn.donatrack.personas.PersonaHumana;
import arg.com.utn.donatrack.estados.EnDeposito;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DonacionPersistenceTest implements SimplePersistenceTest {

  @Test

  void crearYPersistirUnaDonacion() {

    PersonaHumana donante = new PersonaHumana("Hernan", "Gonzalez", 28, 38193023, "Masculino", "AV.Libertador 210", null, null);

    Subcategoria subcategoria = new Subcategoria("Sábanas", new Categoria("Blanqueria"), null, true);

    Bien sabanas = new Bien("Sábanas de algodón", "sabanas.jpg", subcategoria, 2, Unidad.UNIDAD, null, EstadoUso.NUEVO, false, donante);
    Bien toallas = new Bien("Toallas", "toallas.jpg", subcategoria, 3, Unidad.UNIDAD, null, EstadoUso.NUEVO, false, donante);

    List<Bien> bienes = new ArrayList<>();
    bienes.add(sabanas);
    bienes.add(toallas);

    Donacion donacion = new Donacion(bienes);

    withTransaction(() -> {
      entityManager().persist(donante);
      entityManager().persist(sabanas);
      entityManager().persist(toallas);
      entityManager().persist(donacion.getEstado());
      entityManager().persist(donacion);
    });

    assertNotNull(donacion.getId());

    Donacion donacionRecuperada = entityManager().find(Donacion.class, donacion.getId());
    assertNotNull(donacionRecuperada);

    assertNotNull(donacionRecuperada.getBienes());
    assertEquals(2, donacionRecuperada.getBienes().size());

    assertEquals("Sábanas de algodón", donacionRecuperada.getBienes().get(0).getDescripcion());
    assertEquals("Toallas", donacionRecuperada.getBienes().get(1).getDescripcion());

    assertNotNull(donacionRecuperada.getEstado());
    assertTrue(donacionRecuperada.getEstado() instanceof EnDeposito);

    assertNotNull(donacionRecuperada.getHistorialDeCambiosDeEstado());
    assertEquals(1, donacionRecuperada.getHistorialDeCambiosDeEstado().size());
    assertTrue(donacionRecuperada.getHistorialDeCambiosDeEstado().get(0) instanceof EnDeposito);


  }}
