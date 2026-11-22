package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.Necesidad;
import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.usuarios.Usuario;

import javax.persistence.EntityManager;
import java.util.List;

public class RepositorioNecesidades {

  private EntityManager entityManager; // = Persistencia.crearEntityManager();

  public RepositorioNecesidades() {
    this.entityManager = Persistencia.crearEntityManager();
  }

  public void guardarNecesidad(Necesidad necesidad) {
    entityManager.getTransaction().begin();
    entityManager.persist(necesidad);
    entityManager.getTransaction().commit();
  }

  /*public void guardar(EntityManager em, Necesidad necesidad) {
    if (necesidad.getId() == null) {
      em.persist(necesidad);
    } else {
      em.merge(necesidad);
    }
  }*/

  public Necesidad buscarNecesidadPorId(Long id) {
    return entityManager.find(Necesidad.class, id);
  }
  /*public Necesidad buscarPorId(EntityManager em, Long id) {
    if (id == null) {
      return null;
    }
    return em.find(Necesidad.class, id);
  }*/

  public List<Necesidad> listarTodas() {
    return entityManager.createQuery("select n from Necesidad n", Necesidad.class)
        .getResultList();
  }

  public List<Necesidad> listarPendientes() {
    return entityManager.createQuery(
            "select n from Necesidad n where n.satisfecha = false or n.satisfecha is null", Necesidad.class)
        .getResultList();
  }

  public List<Necesidad> listarPorEntidad(Long entidadId) {
    if (entidadId == null) {
      return List.of();
    }
    return entityManager.createQuery(
            "select n from EntidadBeneficiaria e join e.necesidades n where e.id = :entidadId", Necesidad.class)
        .setParameter("entidadId", entidadId)
        .getResultList();
  }
}