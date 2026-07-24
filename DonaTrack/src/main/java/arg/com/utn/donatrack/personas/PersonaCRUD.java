package arg.com.utn.donatrack.personas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/*
 CRUD de Personas (humanas y jurídicas).
 Al todavía no tener persistencia, se mockea guardando en memoria.
 */
public class PersonaCRUD {

  private final List<Persona> personas = new ArrayList<>();
  private final AtomicLong idCounter = new AtomicLong(1);

  //READ
  // Traer todas las personas
  public List<Persona> listar() {
    return new ArrayList<>(personas);
  }

  // Traer una persona por id.  RuntimeException si no existe.
  public Persona obtener(Long id) {
    return buscarPorId(id);
  }
  //CREATE
  // Crear una persona
  public Persona crear(Persona persona) {
    persona.setId(idCounter.getAndIncrement());
    personas.add(persona);
    return persona;
  }

  //DELETE
  //eliminar persona por id
  public void eliminar(Long id) {
    Persona persona = buscarPorId(id);
    personas.remove(persona);
  }

  //Funcion auxiliar
  private Persona buscarPorId(Long id) {
    for (Persona persona : personas) {
      if (persona.getId().equals(id)) {
        return persona;
      }
    }
    throw new RuntimeException("No existe una persona con id " + id);
  }
}