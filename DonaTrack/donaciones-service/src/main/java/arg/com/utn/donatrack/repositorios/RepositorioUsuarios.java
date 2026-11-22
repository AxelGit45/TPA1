package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.usuarios.Usuario;
import javax.persistence.EntityManager;

public class RepositorioUsuarios {

  // private EntityManager entityManager; // = Persistencia.crearEntityManager();

  public RepositorioUsuarios() {
    // this.entityManager = Persistencia.crearEntityManager();
  }

  public void guardarUsuario(EntityManager em, Usuario usuario) {
    em.getTransaction().begin();
    em.persist(usuario);
    em.getTransaction().commit();
  }

  public Usuario buscarUsuarioPorEmail(EntityManager em, String email) {
    return em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class).
            setParameter("email", email)
            .getResultStream().findFirst().orElse(null);
  }
}