package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;

import javax.persistence.EntityManager;
import java.util.List;

public class RepositorioEntidadesBeneficiarias {

  public void guardar(EntityManager em, EntidadBeneficiaria entidad) {
    if (entidad.getId() == null) {
      em.persist(entidad);
    } else {
      em.merge(entidad);
    }
  }

  public EntidadBeneficiaria buscarPorId(EntityManager em, Long id) {
    if (id == null) {
      return null;
    }
    return em.find(EntidadBeneficiaria.class, id);
  }

  public EntidadBeneficiaria buscarPorUsuarioId(EntityManager em, Long usuarioId) {
    if (usuarioId == null) {
      return null;
    }
    List<EntidadBeneficiaria> resultados = em
        .createQuery("select e from EntidadBeneficiaria e where e.usuarioId = :usuarioId", EntidadBeneficiaria.class)
        .setParameter("usuarioId", usuarioId)
        .getResultList();
    return resultados.isEmpty() ? null : resultados.get(0);
  }

  public List<EntidadBeneficiaria> listarTodas(EntityManager em) {
    return em.createQuery("select e from EntidadBeneficiaria e", EntidadBeneficiaria.class)
        .getResultList();
  }

  public static List<EntidadBeneficiaria> getEntidadesBeneficiarias() {
    EntityManager em = arg.com.utn.donatrack.persistencia.Persistencia.crearEntityManager();
    try {
      return em.createQuery("select e from EntidadBeneficiaria e", EntidadBeneficiaria.class).getResultList();
    } finally {
      em.close();
    }
  }
}