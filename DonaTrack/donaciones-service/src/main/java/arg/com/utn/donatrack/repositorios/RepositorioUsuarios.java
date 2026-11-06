package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.usuarios.Usuario;

import javax.persistence.EntityManager;
import java.util.List;

public class RepositorioUsuarios {

  public Usuario buscarPorEmail(EntityManager em, String email) {
    if (email == null) {
      return null;
    }
    List<Usuario> resultado = em
        .createQuery("select u from Usuario u where u.email = :email", Usuario.class)
        .setParameter("email", email.trim().toLowerCase())
        .getResultList();
    return resultado.isEmpty() ? null : resultado.get(0);
  }

  public boolean existeEmail(EntityManager em, String email) {
    return buscarPorEmail(em, email) != null;
  }

  public void guardar(EntityManager em, Usuario usuario) {
    em.persist(usuario);
  }
}