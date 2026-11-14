package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.estados.EnDeposito;
import arg.com.utn.donatrack.estados.EnTraslado;
import arg.com.utn.donatrack.estados.Entregada;

import javax.persistence.EntityManager;
import java.util.List;

public class RepositorioDonaciones {

  public void guardar(EntityManager em, Donacion donacion) {
    if (donacion.getId() == null) {
      em.persist(donacion);
    } else {
      em.merge(donacion);
    }
  }

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
            "select distinct d from Donacion d where TYPE(d.estadoDonacion) = Entregada", Donacion.class)
        .getResultList();
  }

  public List<Donacion> listarEnDeposito(EntityManager em) {
    return em.createQuery(
            "select distinct d from Donacion d where TYPE(d.estadoDonacion) = EnDeposito", Donacion.class)
        .getResultList();
  }

  public List<Donacion> listarEnTraslado(EntityManager em) {
    return em.createQuery(
            "select distinct d from Donacion d where TYPE(d.estadoDonacion) = EnTraslado", Donacion.class)
        .getResultList();
  }
}