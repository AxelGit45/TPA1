package arg.com.utn.donatrack.usuarios;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String contrasena;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Rol rol;

  public Usuario() {}

  public Usuario(String email, String contrasena, Rol rol) {
    this.email = email.trim().toLowerCase();
    this.contrasena = contrasena;
    this.rol = rol;
  }

  public boolean contrasenaCoincide(String intento) {
    return this.contrasena.equals(intento);
  }

  public Long getId() { return id; }
  public String getEmail() { return email; }
  public Rol getRol() { return rol; }
}