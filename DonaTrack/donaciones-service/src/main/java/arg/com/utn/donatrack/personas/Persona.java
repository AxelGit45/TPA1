package arg.com.utn.donatrack.personas;

import arg.com.utn.donatrack.personas.contactos.Contacto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Persona")
public abstract class Persona {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
  protected List<Contacto> contactos = new ArrayList<>();

  private LocalDate ultimaConexion;

  public void ingresarDonacion(){}

  public void agregarContacto(Contacto contacto) {
    if (this.contactos == null) {
      this.contactos = new ArrayList<>();
    }
    this.contactos.add(contacto);
    contacto.setPersona(this);
  }

  public Contacto obtenerMedio(Contacto contactoAObtener){
    return this.contactos.stream()
        .filter(contacto -> contacto.equals(contactoAObtener))
        .findFirst()
        .orElse(null);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<Contacto> getContactos() {
    return contactos;
  }

  public LocalDate getUltimaConexion(){ return this.ultimaConexion;}

  public void setContactos(List<Contacto> contactos) {this.contactos = contactos;}

  public void setUltimaConexion(LocalDate ultimaConexion){ this.ultimaConexion = ultimaConexion;}
}