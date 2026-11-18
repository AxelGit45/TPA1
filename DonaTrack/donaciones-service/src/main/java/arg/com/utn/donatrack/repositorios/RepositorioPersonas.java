package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.personas.Persona;
import arg.com.utn.donatrack.usuarios.Usuario;

import javax.persistence.EntityManager;
import java.util.List;

public class RepositorioPersonas {

  private EntityManager entityManager;

  public  RepositorioPersonas() {
    this.entityManager = Persistencia.crearEntityManager();
  }

  public void guardarPersona(Persona persona) {
    entityManager.getTransaction().begin();
    entityManager.persist(persona);
    entityManager.getTransaction().commit();
  }

  public Persona buscarPorEmail(String email) {
    if (email == null || email.isBlank()) {
      return null;
    }
    return entityManager
        .createQuery(
            "SELECT p FROM Persona p, Usuario u WHERE p.usuarioId = u.id AND u.email = :email",
            Persona.class)
        .setParameter("email", email)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }

  /*public void guardar(Persona persona) {
    if (persona.getId() == null) {
      entityManager.persist(persona);
    } else {
      entityManager.merge(persona);
    }
  }*/

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