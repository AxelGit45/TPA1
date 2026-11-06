package arg.com.utn.donatrack.usuarios;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;


public class RepositorioUsuarios {

  private static final RepositorioUsuarios INSTANCE = new RepositorioUsuarios();

  public static RepositorioUsuarios instance() { return INSTANCE; }

  private final List<Usuario> usuarios = new CopyOnWriteArrayList<>(List.of(
      new Usuario(1L, "donante@donatrack.com", "1234", Rol.DONANTE),
      new Usuario(2L, "entidad@donatrack.com", "1234", Rol.ENTIDAD),
      new Usuario(3L, "admin@donatrack.com", "1234", Rol.ADMINISTRADOR)));

  private final AtomicLong ultimoId = new AtomicLong(3);

  public Optional<Usuario> buscarPorEmail(String email) {
    return usuarios.stream()
        .filter(u -> u.getEmail().equalsIgnoreCase(email))
        .findFirst();
  }

  public Usuario crear(String email, String contrasena, Rol rol) {
    Usuario usuario = new Usuario(ultimoId.incrementAndGet(), email, contrasena, rol);
    usuarios.add(usuario);
    return usuario;
  }


}
