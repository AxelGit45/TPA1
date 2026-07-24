package arg.com.utn.donatrack.personas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PersonaCRUDTest {

  private PersonaCRUD crud;

  @BeforeEach
  void setUp() {
    crud = new PersonaCRUD();
  }

  @Test
  @DisplayName("Crear una persona humana la agrega a la lista con su id")
  void crearPersonaHumana() {
    int cantidadAntes = crud.listar().size();

    Persona creada = crud.crear(nuevaPersonaHumana());

    assertEquals(cantidadAntes + 1, crud.listar().size(),
        "Al crear una persona la cantidad de personas tiene que aumentar uno");
    assertEquals(creada.getId(), crud.listar().get(crud.listar().size() - 1).getId(),
        "La última persona de la lista tiene que tener el mismo id de la donación creada");
  }

  @Test
  @DisplayName("Crear una persona jurídica la agrega a la lista con su id")
  void crearPersonaJuridica() {
    int cantidadAntes = crud.listar().size();

    Persona creada = crud.crear(nuevaPersonaJuridica());

    assertEquals(cantidadAntes + 1, crud.listar().size(),
        "Al crear una persona juridica la cantidad de personas tiene que aumentar uno");
    assertEquals(creada.getId(), crud.listar().get(crud.listar().size() - 1).getId(),
        "La última persona juridica de la lista tiene que tener el mismo id de la donación creada");
  }

  @Test
  @DisplayName("Obtener por id devuelve la persona creada con ese id")
  void obtenerPersonaCreada() {
    Persona creada = crud.crear(nuevaPersonaHumana());

    Persona obtenida = crud.obtener(creada.getId());

    assertEquals(creada.getId(), obtenida.getId(),
        "obtener debería devolver la misma persona que se creó con ese id");
  }

  @Test
  @DisplayName("Al eliminar una persona ya no puede obtenerse la misma")
  void eliminarPersonaHumana() {
    Persona creada = crud.crear(nuevaPersonaHumana());

    crud.eliminar(creada.getId());

    assertThrows(RuntimeException.class, () -> crud.obtener(creada.getId()),
        "Después de eliminar la persona al intentar buscarla ocurre una excepción");
  }

  @Test
  @DisplayName("Al eliminar una persona ya no puede obtenerse la misma")
  void eliminarPersonaJuridica() {
    Persona creada = crud.crear(nuevaPersonaJuridica());

    crud.eliminar(creada.getId());

    assertThrows(RuntimeException.class, () -> crud.obtener(creada.getId()),
        "Después de eliminar la persona al intentar buscarla ocurre una excepción");
  }

  private PersonaHumana nuevaPersonaHumana() {
    return new PersonaHumana(
        "Leo",
        "Messi",
        39,
        12345678,
        "M",
        "Av. del libertador 132",
        new ArrayList<>(),
        null
    );
  }

  private PersonaJuridica nuevaPersonaJuridica() {
    return new PersonaJuridica(
        "Green peace",
        TipoRazonSocial.ONG,
        "Protección ambiental",
        new ArrayList<>()
    );
  }
}