package arg.com.utn.donatrack.usuarios;

public class Usuario {
  private final Long id;
  private final String email;
  private final String contrasena;
  private final Rol rol;

  public Usuario(Long id, String email, String contrasena, Rol rol) {
    this.id = id;
    this.email = email;
    this.contrasena = contrasena;
    this.rol = rol;
  }

  public Long getId() { return id; }
  public String getEmail() { return email; }
  public Rol getRol() { return rol; }

  public boolean contrasenaCoincide(String intento) {
    return this.contrasena.equals(intento);
  }
}