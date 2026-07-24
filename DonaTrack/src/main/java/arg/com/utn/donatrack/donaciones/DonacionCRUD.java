package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.estados.EstadoDonacion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/*
 CRUD de Donaciones.
 Al todavía no tener persistencia, se mockea guardando en memoria.
 */
public class DonacionCRUD {

  private final List<Donacion> donaciones = new ArrayList<>();
  private final AtomicLong idCounter = new AtomicLong(1);

  //READ
  //Traer todas las donaciones
  public List<Donacion> listar() {
    return new ArrayList<>(donaciones);
  }

  // Traer donacion por ID
  public Donacion obtener(Long id) {
    return buscarPorId(id);
  }

  //CREATE
  //Crear una nueva donacion
  public Donacion crear(List<Bien> bienes) {
    Donacion nueva = new Donacion(bienes != null ? bienes : new ArrayList<>());
    nueva.setId(idCounter.getAndIncrement());
    donaciones.add(nueva);
    return nueva;
  }
  //DELETE
  // Eliminar una donación
  public void eliminar(Long id) {
    Donacion donacion = buscarPorId(id);
    donaciones.remove(donacion);
  }
  //UPDATE
  // Actualizar el estado de una donación
  public Donacion actualizarEstado(Long id, EstadoDonacion nuevoEstado) {
    if (nuevoEstado == null) {
      throw new IllegalArgumentException("Debe indicar un estado válido");
    }

    Donacion donacion = buscarPorId(id);
    donacion.cambiarEstado(nuevoEstado);
    return donacion;
  }
  //Funcion auxiliar
  // Buscar donacion por id
  private Donacion buscarPorId(Long id) {
    for (Donacion donacion : donaciones) {
      if (donacion.getId().equals(id)) {
        return donacion;
      }
    }
    throw new RuntimeException("No existe una donación con id " + id);
  }
}