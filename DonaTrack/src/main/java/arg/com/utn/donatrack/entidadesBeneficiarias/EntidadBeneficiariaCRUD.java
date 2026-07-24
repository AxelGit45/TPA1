package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.personas.contactos.Contacto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/*
 CRUD de Entidades Beneficiarias.
 Al todavía no tener persistencia, se mockea guardando en memoria.
 */
public class EntidadBeneficiariaCRUD {

  private final List<EntidadBeneficiaria> entidades = new ArrayList<>();
  private final AtomicLong idCounter = new AtomicLong(1);

  //READ
  // Traer todas las entidades beneficiarias
  public List<EntidadBeneficiaria> listar() {
    return new ArrayList<>(entidades);
  }

  // Traer una entidad beneficiaria por id.
  public EntidadBeneficiaria obtener(Long id) {
    return buscarPorId(id);
  }
  //CREATE
  // Crear una nueva entidad beneficiaria
  public EntidadBeneficiaria crear(String razonSocial, String direccion,
                                   List<Contacto> contactos, List<Necesidad> necesidades) {
    EntidadBeneficiaria nueva = new EntidadBeneficiaria(
        razonSocial,
        direccion,
        contactos != null ? contactos : new ArrayList<>(),
        necesidades != null ? necesidades : new ArrayList<>()
    );
    nueva.setId(idCounter.getAndIncrement());
    entidades.add(nueva);
    return nueva;
  }

  //DELETE
  // Eliminar una entidad beneficiaria.
  public void eliminar(Long id) {
    EntidadBeneficiaria entidad = buscarPorId(id);
    entidades.remove(entidad);
  }

  private EntidadBeneficiaria buscarPorId(Long id) {
    for (EntidadBeneficiaria entidad : entidades) {
      if (entidad.getId().equals(id)) {
        return entidad;
      }
    }
    throw new RuntimeException("No existe una entidad beneficiaria con id " + id);
  }
}