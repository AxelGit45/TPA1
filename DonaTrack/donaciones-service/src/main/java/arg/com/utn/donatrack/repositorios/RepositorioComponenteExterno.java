package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.donaciones.ResultadoComponenteAsignador;
import java.util.List;
import javax.persistence.EntityManager;

public class RepositorioComponenteExterno {

  public RepositorioComponenteExterno() {

  }

  public void guardarResultadosDeComponenteExterno(EntityManager em, List<ResultadoComponenteAsignador> resultadosComponenteAsignador) {
    em.getTransaction().begin();
    resultadosComponenteAsignador.forEach( resultadoComponenteAsignador -> em.persist(resultadoComponenteAsignador));
    em.getTransaction().commit();
  }

  public List<ResultadoComponenteAsignador> obtenerResultados(EntityManager em) {
    return em.createQuery(
            "select r from ResultadoComponenteAsignador r join r.donacion d join d.estadoDonacion e "
                + "where r.entidad is null and TYPE(e) = EnDeposito "
                + "order by r.id asc", ResultadoComponenteAsignador.class)
        .getResultList();
  }

  public ResultadoComponenteAsignador buscarPorId(EntityManager em, Long id) {
    if (id == null) {
      return null;
    }
    return em.find(ResultadoComponenteAsignador.class, id);
  }

  public void actualizar(EntityManager em, ResultadoComponenteAsignador resultado) {
    em.getTransaction().begin();
    em.merge(resultado);
    em.getTransaction().commit();
  }



}
