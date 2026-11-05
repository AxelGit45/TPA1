package arg.com.utn.donatrack.usuarios;

import java.util.List;
import java.util.Optional;


public class RepositorioUsuarios {

  private static final RepositorioUsuarios INSTANCE = new RepositorioUsuarios();

  public static RepositorioUsuarios instance() { return INSTANCE; }

  private final List<Usuario> usuarios = List.of(
      new Usuario(1L, "donante@donatrack.com", "1234", Rol.DONANTE),
      new Usuario(2L, "entidad@donatrack.com", "1234", Rol.ENTIDAD),
      new Usuario(3L, "admin@donatrack.com", "1234", Rol.ADMINISTRADOR));

  public Optional<Usuario> buscarPorEmail(String email) {
    return usuarios.stream()
        .filter(u -> u.getEmail().equals(email))
        .findFirst();
  }
}
