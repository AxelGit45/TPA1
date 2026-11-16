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
  
  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getContrasenia() {
    return contrasenia;
  }

  public Rol getRol() {
    return rol;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setContrasenia(String contrasenia) {
    this.contrasenia = contrasenia;
  }

  public void setRol(Rol rol) {
    this.rol = rol;
  }
  
  

}
