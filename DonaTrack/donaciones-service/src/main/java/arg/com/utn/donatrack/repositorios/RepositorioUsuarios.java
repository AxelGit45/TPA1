package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.usuarios.Usuario;
import javax.persistence.EntityManager;

public class RepositorioUsuarios {

  private EntityManager entityManager = Persistencia.crearEntityManager();

  public RepositorioUsuarios(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardarUsuario(Usuario usuario) {
    entityManager.getTransaction().begin();
    entityManager.persist(usuario);
    entityManager.getTransaction().commit();
  }

  public Usuario buscarUsuarioPorEmail(String email) {
    return entityManager.createQuery("SELECT u FROM usuarios u WHERE u.email = :email", Usuario.class).
            setParameter("email", email)
            .getResultStream().findFirst().orElse(null);
  }

}