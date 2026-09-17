package arg.com.utn.donatrack.personas;

import arg.com.utn.donatrack.personas.contactos.Contacto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "PersonaJuridica")
public class PersonaJuridica extends Persona {

  private String razonSocial;
  @Enumerated(EnumType.STRING)
  private TipoRazonSocial tipoRazonSocial;
  private String rubro;

  public PersonaJuridica(){}

  public PersonaJuridica(String razonSocial, TipoRazonSocial tipoRazonSocial, String rubro, List<Contacto> contactos){
    this.razonSocial = razonSocial;
    this.tipoRazonSocial = tipoRazonSocial;
    this.rubro = rubro;
    this.contactos = contactos;
  }

  public String getRazonSocial() { return razonSocial; }
  public TipoRazonSocial getTipoRazonSocial() { return tipoRazonSocial; }
  public String getRubro() { return rubro; }

  public void setRazonSocial(String razonSocial) {this.razonSocial = razonSocial;}

}