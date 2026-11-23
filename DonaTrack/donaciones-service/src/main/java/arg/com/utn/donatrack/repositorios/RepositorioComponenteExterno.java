package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.donaciones.ResultadoComponenteAsignador;
import java.util.List;
import javax.persistence.EntityManager;

public class RepositorioComponenteExterno {

  public RepositorioComponenteExterno() {

  }

  public void guardarResultadosDeComponenteExterno(EntityManager em, List<ResultadoComponenteAsignador> resultadosComponenteAsignador) {
    resultadosComponenteAsignador.forEach( resultadoComponenteAsignador -> em.persist(resultadoComponenteAsignador));
  }
}
