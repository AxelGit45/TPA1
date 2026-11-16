package arg.com.utn.donatrack.usuarios;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="usuarios")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  private String contrasenia;
  @Enumerated(EnumType.STRING)
  private Rol rol;

  public Usuario() {
  }

  public Usuario(String email, String contrasenia, Rol rol) {
    this.email = email;
    this.contrasenia = contrasenia;
    this.rol = rol;
  }
}
