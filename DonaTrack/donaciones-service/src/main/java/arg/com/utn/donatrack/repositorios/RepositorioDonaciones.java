package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.estados.EnDeposito;
import arg.com.utn.donatrack.estados.EnTraslado;
import arg.com.utn.donatrack.estados.Entregada;
import arg.com.utn.donatrack.persistencia.Persistencia;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import java.util.List;

public class RepositorioDonaciones {

  // private EntityManager entityManager;

  public RepositorioDonaciones() {
    // this.entityManager = Persistencia.crearEntityManager();
  }

  public void guardar(EntityManager em, Donacion donacion) {
    em.getTransaction().begin();
    em.persist(donacion);
    em.getTransaction().commit();
  }

  public void actualizar(EntityManager em, Donacion donacion) {
    em.getTransaction().begin();
    em.merge(donacion);
    em.getTransaction().commit();
  }

  /*public void guardar(EntityManager em, Donacion donacion) {
    if (donacion.getId() == null) {
      em.persist(donacion);
    } else {
      em.merge(donacion);
    }
  }*/

  public Donacion buscarPorId(EntityManager em, Long id) {
    if (id == null) {
      return null;
    }
    return em.find(Donacion.class, id);
  }

  public List<Donacion> listarTodas(EntityManager em) {
    return em.createQuery("select distinct d from Donacion d", Donacion.class)
        .getResultList();
  }

  public List<Donacion> listarAsignadasAEntidad(EntityManager em, Long entidadId) {
    if (entidadId == null) {
      return List.of();
    }
    String sql = "select distinct d.* from donacion d "
        + "join estadodonacion e on e.donacion_id = d.id "
        + "where e.tipo_estado = 'ASIGNACION_REALIZADA' "
        + "and e.entidad_beneficiaria_id = :entidadId";

    return em.createNativeQuery(sql, Donacion.class)
        .setParameter("entidadId", entidadId)
        .getResultList();
  }

  public List<Donacion> listarPorDonante(EntityManager em, Long personaId, String estado, Long categoriaId, Long subcategoriaId) {
    String sql = "select distinct d.* from donacion d "
        + "join estadodonacion e on e.id = d.estado_actual_id "
        + "join bien b on b.donacion_id = d.id "
        + "join subcategoria s on s.id = b.subcategoria_id "
        + "where b.persona_id = :personaId";

    if (estado != null && !estado.isBlank()) {
      sql += " and e.tipo_estado = :estado";
    }
    if (categoriaId != null) {
      sql += " and s.categoria_id = :categoriaId";
    }
    if (subcategoriaId != null) {
      sql += " and s.id = :subcategoriaId";
    }

    var query = em.createNativeQuery(sql, Donacion.class)
        .setParameter("personaId", personaId);
    if (estado != null && !estado.isBlank()) {
      query.setParameter("estado", estado);
    }
    if (categoriaId != null) {
      query.setParameter("categoriaId", categoriaId);
    }
    if (subcategoriaId != null) {
      query.setParameter("subcategoriaId", subcategoriaId);
    }
    return query.getResultList();
  }


  public List<Donacion> listarPorDonante(EntityManager em, Long personaId) {
    if (personaId == null) {
      return List.of();
    }
    return em.createQuery(
            "select distinct d from Donacion d join d.bienes b where b.donador.id = :personaId", Donacion.class)
        .setParameter("personaId", personaId)
        .getResultList();
  }

  public List<Donacion> listarEntregadas(EntityManager em) {
    return em.createQuery(
            "select distinct d from Donacion d join d.estadoDonacion e where TYPE(e) = Entregada", Donacion.class)
        .getResultList();
  }

  public List<Donacion> listarEnDeposito(EntityManager em) {
    return em.createQuery(
            "select distinct d from Donacion d join d.estadoDonacion e where TYPE(e) = EnDeposito", Donacion.class)
        .getResultList();
  }

  public List<Donacion> listarVencidasEnDeposito(EntityManager em) {
    return em.createQuery(
            "select distinct d from Donacion d "
                + "join d.estadoDonacion e "
                + "join d.bienes b "
                + "where TYPE(e) = EnDeposito "
                + "and b.perecedero = true and b.fechaVencimiento < :hoy", Donacion.class)
        .setParameter("hoy", LocalDate.now())
        .getResultList();
  }

  public List<Donacion> listarEnTraslado(EntityManager em) {
    return em.createQuery(
            "select distinct d from Donacion d join d.estadoDonacion e where TYPE(e) = EnTraslado", Donacion.class)
        .getResultList();
  }
}