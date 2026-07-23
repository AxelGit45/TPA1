package donantes;

import arg.com.utn.donatrack.personas.contactos.Contacto;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public abstract class Persona {

  protected Long id;
  protected List<Contacto> contactos;
  private LocalDate ultimaConexion;

  public void ingresarDonacion(){}

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