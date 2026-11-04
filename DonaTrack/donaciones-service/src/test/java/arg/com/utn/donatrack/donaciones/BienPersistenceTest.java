package arg.com.utn.donatrack.donaciones;


import arg.com.utn.donatrack.personas.PersonaHumana;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BienPersistenceTest implements SimplePersistenceTest {

  @Test
   /*
  1- Creamos el donante
  2- Creamos la subcategoría y su categoría
  3- Creamos la fecha de vencimiento
  4- Creamos el bien
  5- Persistimos donador y bien dentro de una transacción
  6- Verificamos que se haya generado id
  7- Consultamos el bien
  8- Consultamos la subcategoria
  9- Consultamos el donador
   */

  void crearYPersistirUnBienPerecedero() {

      PersonaHumana donante = new PersonaHumana("Julieta", "Gonzalez", 21, 45320415, "Femenino", "Avellaneda 1810", null, null);

      Categoria categoria = new Categoria("Alimentos");
      Subcategoria subcategoria = new Subcategoria("Enlatados", categoria, true, false);
      Date fechaVencimiento = new Date();
      Bien arvejas = new Bien ("Arvejas en lata", "arvejas.jpg", subcategoria, 10, Unidad.UNIDAD, fechaVencimiento, null, true, donante);

      withTransaction(() -> {
        entityManager().persist(donante);
        entityManager().persist(categoria);
        entityManager().persist(subcategoria);
        entityManager().persist(arvejas);
      });

      assertNotNull(arvejas.getId());
      entityManager().clear();

      Bien bienRecuperado = entityManager().find(Bien.class, arvejas.getId());
      assertNotNull(bienRecuperado);
      assertEquals("Arvejas en lata", bienRecuperado.getDescripcion());
      assertEquals("arvejas.jpg", bienRecuperado.getFoto());
      assertEquals(10, bienRecuperado.getCantidad());
      assertEquals(Unidad.UNIDAD, bienRecuperado.getUnidad());
      assertEquals(true, bienRecuperado.isPerecedero());
      assertNotNull(bienRecuperado.getFechaVencimiento());

      assertNotNull(bienRecuperado.getSubcategoria());
      assertEquals("Enlatados", bienRecuperado.getSubcategoria().getNombre());

      assertNotNull(bienRecuperado.getDonador());
      assertEquals(donante.getId(), bienRecuperado.getDonador().getId());
      assertEquals("Alimentos", bienRecuperado.getSubcategoria().getCategoriaPadre().getNombre());


  }

  /*
1- Creamos el donante
2- Creamos la subcategoría y su categoría
3- Creamos el bien
4- Persistimos donador y bien dentro de una transacción
5- Verificamos que se haya generado el id
6- Consultamos el bien
7- Consultamos la subcategoria
8- Consultamos el donador
 */

  @Test
  void crearYPersistirUnBienNoPerecedero(){
    PersonaHumana donante = new PersonaHumana("Rocio", "Lopez", 25,  40320415, "Femenino", "Moreno 1810", null, null);

    Categoria categoria = new Categoria("Ropa");
    Subcategoria subcategoria = new Subcategoria("Remera", categoria, false, true);
    Bien remera = new Bien ("Remera de algodón", "remera.jpg", subcategoria, 5, Unidad.UNIDAD, null, EstadoUso.USADO, false, donante);

    withTransaction(() -> {
      entityManager().persist(donante);
      entityManager().persist(categoria);
      entityManager().persist(subcategoria);
      entityManager().persist(remera);
    });

    assertNotNull (remera.getId());
    entityManager().clear();

    Bien bienRecuperado = entityManager().find(Bien.class, remera.getId());
    assertNotNull(bienRecuperado);
    assertEquals("Remera de algodón", bienRecuperado.getDescripcion());
    assertEquals("remera.jpg", bienRecuperado.getFoto());
    assertEquals(5, bienRecuperado.getCantidad());
    assertEquals(Unidad.UNIDAD, bienRecuperado.getUnidad());
    assertEquals(false, bienRecuperado.isPerecedero());
    assertEquals (EstadoUso.USADO, bienRecuperado.getEstadoUso());
    assertEquals("Remera", bienRecuperado.getSubcategoria().getNombre());
    assertEquals("Ropa", bienRecuperado.getSubcategoria().getCategoriaPadre().getNombre());

    assertNotNull(bienRecuperado.getSubcategoria());

    assertNotNull(bienRecuperado.getDonador());
    assertEquals(donante.getId(), bienRecuperado.getDonador().getId());

  }
}
