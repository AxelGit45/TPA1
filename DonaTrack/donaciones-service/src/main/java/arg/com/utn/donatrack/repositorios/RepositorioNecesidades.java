package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.entidadesBeneficiarias.Necesidad;

import javax.persistence.EntityManager;
import java.util.List;

public class RepositorioNecesidades {

  public void guardar(EntityManager em, Necesidad necesidad) {
    if (necesidad.getId() == null) {
      em.persist(necesidad);
    } else {
      em.merge(necesidad);
    }
  }

  public Necesidad buscarPorId(EntityManager em, Long id) {
    if (id == null) {
      return null;
    }
    return em.find(Necesidad.class, id);
  }

  public List<Necesidad> listarTodas(EntityManager em) {
    return em.createQuery("select n from Necesidad n", Necesidad.class)
        .getResultList();
  }

  public List<Necesidad> listarPendientes(EntityManager em) {
    return em.createQuery(
            "select n from Necesidad n where n.satisfecha = false or n.satisfecha is null", Necesidad.class)
        .getResultList();
  }

  public List<Necesidad> listarPorEntidad(EntityManager em, Long entidadId) {
    if (entidadId == null) {
      return List.of();
    }
    return em.createQuery(
            "select n from EntidadBeneficiaria e join e.necesidades n where e.id = :entidadId", Necesidad.class)
        .setParameter("entidadId", entidadId)
        .getResultList();
  }
}