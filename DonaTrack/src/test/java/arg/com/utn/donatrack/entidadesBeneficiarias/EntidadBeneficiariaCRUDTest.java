package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.personas.contactos.Contacto;
import arg.com.utn.donatrack.personas.contactos.Mail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EntidadBeneficiariaCRUDTest {

  private EntidadBeneficiariaCRUD crud;

  @BeforeEach
  void setUp() {
    crud = new EntidadBeneficiariaCRUD();
  }

  @Test
  @DisplayName("Crear una entidad beneficiaria")
  void crearEntidadBeneficiaria() {
    int cantidadAntes = crud.listar().size();

    EntidadBeneficiaria creada = nuevaEntidad();

    assertEquals(cantidadAntes + 1, crud.listar().size(),
        "Al crear una entidad la cantidad de entidades tiene que aumentar uno");
    assertEquals(creada.getId(), crud.listar().get(crud.listar().size() - 1).getId(),
        "La última entidad de la lista tiene que tener el mismo id de la donación creada");
  }

  @Test
  @DisplayName("Obtener por id devuelve la entidad creada con ese id")
  void obtenerEntidadBeneficiaria() {
    EntidadBeneficiaria creada = nuevaEntidad();

    EntidadBeneficiaria obtenida = crud.obtener(creada.getId());

    assertEquals(creada.getId(), obtenida.getId(),
        "Al obtener una entidad por id después de crearse los ids deben coincidir");
  }

  @Test
  @DisplayName("Al eliminar una entidad ya no puede obtenerse la misma")
  void eliminarEntidad() {
    EntidadBeneficiaria creada = nuevaEntidad();

    crud.eliminar(creada.getId());

    assertThrows(RuntimeException.class, () -> crud.obtener(creada.getId()),
        "Después de eliminar la entidad al intentar buscarla ocurre una excepción");
  }

  private EntidadBeneficiaria nuevaEntidad() {
    List<Contacto> correos = new ArrayList<>();
    correos.add(new Mail("contacto@comedorsanjose.org"));

    return crud.crear("Comedor San José", "Av. Siempre Viva 742", correos, new ArrayList<>());
  }
}