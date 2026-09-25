package arg.com.utn.donatrack.personas.contactos;

import arg.com.utn.donatrack.personas.Persona;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_contacto")
@Table(name = "contactos")
public abstract class Contacto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @ManyToOne
  @JoinColumn(name = "persona_id"/*, nullable = false*/)
  protected Persona persona;

  public abstract void contactar(String mensaje);

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Persona getPersona() {
    return persona;
  }

  public void setPersona(Persona persona) {
    this.persona = persona;
  }
}
