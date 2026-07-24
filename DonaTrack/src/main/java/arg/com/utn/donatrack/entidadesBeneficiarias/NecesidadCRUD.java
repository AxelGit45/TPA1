package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.donaciones.Subcategoria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/*
 CRUD de Necesidades (recurrentes y extraordinarias).
 Al todavía no tener persistencia, se mockea guardando en memoria.
 */
public class NecesidadCRUD {

  private final List<Necesidad> necesidades = new ArrayList<>();
  private final AtomicLong idCounter = new AtomicLong(1);

  // Traer todas las necesidades
  public List<Necesidad> listar() {
    return new ArrayList<>(necesidades);
  }

  // Traer una necesidad por id.
  public Necesidad obtener(Long id) {
    return buscarPorId(id);
  }

  // Crear una nueva necesidad extraordinaria
  public NecesidadExtraordinaria crearExtraordinaria(Subcategoria necesidad, Integer cantidadNecesitada,
                                                     String descripcion, Boolean satisfecha, Integer recibidos) {

    NecesidadExtraordinaria nueva = new NecesidadExtraordinaria(
        idCounter.getAndIncrement(),
        necesidad,
        cantidadNecesitada,
        descripcion,
        satisfecha != null ? satisfecha : false,
        recibidos != null ? recibidos : 0
    );

    necesidades.add(nueva);
    return nueva;
  }

  // Crear una nueva necesidad recurrente
  public NecesidadRecurrente crearRecurrente(Subcategoria necesidad, Integer cantidadNecesitada,
                                             String descripcion, Boolean satisfecha, Integer recibidos, LocalDate fechaLimite) {

    NecesidadRecurrente nueva = new NecesidadRecurrente(
        idCounter.getAndIncrement(),
        necesidad,
        cantidadNecesitada,
        descripcion,
        satisfecha != null ? satisfecha : false,
        recibidos != null ? recibidos : 0,
        fechaLimite
    );

    necesidades.add(nueva);
    return nueva;
  }

  // Eliminar una necesidad.
  public void eliminar(Long id) {
    Necesidad necesidad = buscarPorId(id);
    necesidades.remove(necesidad);
  }

  private Necesidad buscarPorId(Long id) {
    for (Necesidad necesidad : necesidades) {
      if (necesidad.getId().equals(id)) {
        return necesidad;
      }
    }
    throw new RuntimeException("No existe una necesidad con id " + id);
  }
}