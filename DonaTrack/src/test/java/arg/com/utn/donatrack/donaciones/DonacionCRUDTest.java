package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.estados.EstadoDonacion;
import arg.com.utn.donatrack.estados.Vencida;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DonacionCRUDTest {

  private DonacionCRUD crud;

  @BeforeEach
  void setUp() {
    crud = new DonacionCRUD();
  }

  @Test
  @DisplayName("Crear una donación la agrega a la lista con su id")
  void crearDonacion() {
    int cantidadDonaciones = crud.listar().size();

    Donacion creada = crud.crear(new ArrayList<>());

    assertEquals(cantidadDonaciones + 1, crud.listar().size(),
        "Al crear una donación la cantidad de donaciones tiene que aumentar uno");
    assertEquals(creada.getId(), crud.listar().get(crud.listar().size() - 1).getId(),
        "La última donación de la lista tiene que tener el mismo id de la donación creada");
  }

  @Test
  @DisplayName("Obtener por id devuelve la donación creada con ese id")
  void obtenerDonacion() {
    Donacion creada = crud.crear(new ArrayList<>());

    Donacion obtenida = crud.obtener(creada.getId());

    assertEquals(creada.getId(), obtenida.getId(),
        "Al obtener una donación por id después de crearse los ids deben coincidir");
  }

  @Test
  @DisplayName("Al eliminar una donación ya no puede obtenerse la misma")
  void eliminarDonacion() {
    Donacion creada = crud.crear(new ArrayList<>());

    crud.eliminar(creada.getId());

    assertThrows(RuntimeException.class, () -> crud.obtener(creada.getId()),
        "Después de eliminar la donación al intentar buscarla ocurre una excepción");
  }

  @Test
  @DisplayName("Actualizar el estado cambia el estado real de la donación")
  void actualizarElEstado() {
    Donacion creada = crud.crear(new ArrayList<>());
    EstadoDonacion nuevoEstado = new Vencida();

    crud.actualizarEstado(creada.getId(), nuevoEstado);

    assertEquals(nuevoEstado, creada.getEstado(),
        "Después de actualizarEstado la donación debe poseer el estado nuevo actualizado.)");
  }
}
