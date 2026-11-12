package arg.com.utn.donatrack.usuarios;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/*
* Decisiones de diseño.
* - Una cuenta para ingresar al sistema (el mail). Tiene roles asociados, cada id cargado es un rol.
* - Una persona tiene máximo un rol de cada tipo (por diseño)
* -
* */
@Entity
@Table(name = "usuarios")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String contrasena;

  private Long personaHumanaId;
  private Long juridicaId;
  private Long entidadId;

  protected Usuario() {
  }

  //Validaciones. Que tenga email y la contraseña, que las contraseñas coincidan.
  public Usuario(String email, String contrasena) {
    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("El email es obligatorio");
    }
    if (contrasena == null || contrasena.isBlank()) {
      throw new IllegalArgumentException("La contraseña es obligatoria");
    }
    this.email = email.trim().toLowerCase();
    this.contrasena = contrasena;
  }

  public boolean contrasenaCoincide(String intento) {
    return contrasena.equals(intento);
  }

  // Los roles que tiene la cuenta, en orden de código
  public List<TipoRol> roles() {
    List<TipoRol> roles = new ArrayList<>();
    if (personaHumanaId != null) {
      roles.add(TipoRol.DONANTE_HUMANO);
    }
    if (juridicaId != null) {
      roles.add(TipoRol.DONANTE_JURIDICO);
    }
    if (entidadId != null) {
      roles.add(TipoRol.ENTIDAD);
    }
    return roles;
  }

  public boolean tieneRol(TipoRol rol) {
    return roles().contains(rol);
  }

  public void asignarPersonaHumana(Long personaHumanaId) {
    if (this.personaHumanaId != null) {
      throw new IllegalStateException("La cuenta ya tiene una persona humana");
    }
    this.personaHumanaId = personaHumanaId;
  }

  public void asignarJuridica(Long juridicaId) {
    if (this.juridicaId != null) {
      throw new IllegalStateException("La cuenta ya está asociada a una persona jurídica");
    }
    this.juridicaId = juridicaId;
  }

  public void asignarEntidad(Long entidadId) {
    if (this.entidadId != null) {
      throw new IllegalStateException("La cuenta ya está asociada a una entidad");
    }
    this.entidadId = entidadId;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public Long getPersonaHumanaId() {
    return personaHumanaId;
  }

  public Long getJuridicaId() {
    return juridicaId;
  }

  public Long getEntidadId() {
    return entidadId;
  }
}