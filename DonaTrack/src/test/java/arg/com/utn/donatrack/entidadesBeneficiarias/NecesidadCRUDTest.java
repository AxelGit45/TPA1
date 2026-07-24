package arg.com.utn.donatrack.entidadesBeneficiarias;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NecesidadCRUDTest {

  private NecesidadCRUD crud;

  @BeforeEach
  void setUp() {
    crud = new NecesidadCRUD();
  }

  // --- crear ---

  @Test
  @DisplayName("Crear una necesidad extraordinaria la agrega a la lista con su id")
  void crearExtraordinaria_agregaLaNecesidadALaLista() {
    int cantidadAntes = crud.listar().size();

    Necesidad creada = crearExtraordinaria();

    assertEquals(cantidadAntes + 1, crud.listar().size(),
        "Al crear una necesidad extraordinaria la cantidad de necesidades tiene que aumentar uno");
    assertEquals(creada.getId(), crud.listar().get(crud.listar().size() - 1).getId(),
        "La última necesidad de la lista tiene que tener el mismo id de la necesidad creada");
  }

  @Test
  @DisplayName("Al crear una necesidad recurrente la cantidad de necesidades tiene que aumentar uno")
  void crearRecurrente_agregaLaNecesidadALaLista() {
    int cantidadAntes = crud.listar().size();

    Necesidad creada = crearRecurrente();

    assertEquals(cantidadAntes + 1, crud.listar().size(),
        "Después de crear una necesidad recurrente, la cantidad de necesidades debería incrementarse en 1");
    assertEquals(creada.getId(), crud.listar().get(crud.listar().size() - 1).getId(),
        "La última necesidad de la lista tiene que tener el mismo id de la necesidad creada");

  }



  @Test
  @DisplayName("Obtener por id devuelve la necesidad creada con ese id")
  void obtener_devuelveLaNecesidadCreada() {
    Necesidad creada = crearExtraordinaria();

    Necesidad obtenida = crud.obtener(creada.getId());

    assertEquals(creada.getId(), obtenida.getId(),
        "obtener(id) debería devolver la misma necesidad que se creó con ese id");
  }


  @Test
  @DisplayName("Al eliminar una necesidad ya no puede obtenerse la misma")
  void eliminar_sacaLaNecesidadDeLaLista() {
    Necesidad creada = crearExtraordinaria();

    crud.eliminar(creada.getId());

    assertThrows(RuntimeException.class, () -> crud.obtener(creada.getId()),
        "Después de eliminar la persona al intentar buscarla ocurre una excepción");
  }

  private NecesidadExtraordinaria crearExtraordinaria() {
    return crud.crearExtraordinaria(null, 10, "Sueter", false, 0);
  }

  private NecesidadRecurrente crearRecurrente() {
    return crud.crearRecurrente(null, 20, "Conservas", false, 0, LocalDate.now().plusMonths(1));
  }
}