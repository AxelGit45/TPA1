package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.personas.Persona;

import javax.persistence.EntityManager;
import java.util.List;

public class RepositorioPersonas {

  public void guardar(EntityManager em, Persona persona) {
    if (persona.getId() == null) {
      em.persist(persona);
    } else {
      em.merge(persona);
    }
  }

  public Persona buscarPorId(EntityManager em, Long id) {
    if (id == null) {
      return null;
    }
    return em.find(Persona.class, id);
  }

  public Persona buscarPorUsuarioId(EntityManager em, Long usuarioId) {
    if (usuarioId == null) {
      return null;
    }
    List<Persona> resultados = em
        .createQuery("select p from Persona p where p.usuarioId = :usuarioId", Persona.class)
        .setParameter("usuarioId", usuarioId)
        .getResultList();
    return resultados.isEmpty() ? null : resultados.get(0);
  }

  public List<Persona> listarTodas(EntityManager em) {
    return em.createQuery("select p from Persona p", Persona.class)
        .getResultList();
  }

  public static List<Persona> getPersonas() {
    EntityManager em = Persistencia.crearEntityManager();
    try {
      return em.createQuery("select p from Persona p", Persona.class).getResultList();
    } finally {
      em.close();
    }
  }
}