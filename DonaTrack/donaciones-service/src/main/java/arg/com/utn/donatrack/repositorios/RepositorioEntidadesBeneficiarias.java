package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.usuarios.Usuario;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.time.LocalDate;

public class RepositorioEntidadesBeneficiarias {

  // private EntityManager entityManager; // = Persistencia.crearEntityManager();

  public RepositorioEntidadesBeneficiarias() {
    // this.entityManager = Persistencia.crearEntityManager();
  }

  public void guardarEntidad(EntityManager em, EntidadBeneficiaria entidad) {
    em.getTransaction().begin();
    em.persist(entidad);
    em.getTransaction().commit();
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
    // return em.createQuery("select e from EntidadBeneficiaria e", EntidadBeneficiaria.class)
    return em.createQuery("select e from EntidadBeneficiaria e order by e.id asc", EntidadBeneficiaria.class)
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

  public List<EntidadBeneficiaria> listarConNecesidades(EntityManager em) {
    return em.createQuery(
            "select e from EntidadBeneficiaria e where e.necesidades is not empty", EntidadBeneficiaria.class)
        .getResultList();
  }

  public int contarDonacionesRecibidasDesde(EntityManager em, Long entidadId, LocalDate desde) {
    Long cantidad = em.createQuery(
            "select count(e) from AsignacionRealizada e "
                + "where e.entidadBeneficiaria.id = :entidadId and e.fechaDeAsignacion >= :desde", Long.class)
        .setParameter("entidadId", entidadId)
        .setParameter("desde", desde)
        .getSingleResult();
    return cantidad.intValue();
  }

}