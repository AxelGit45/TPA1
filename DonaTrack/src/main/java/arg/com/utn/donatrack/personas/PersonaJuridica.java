package arg.com.utn.donatrack.personas;

import arg.com.utn.donatrack.personas.contactos.Contacto;
import java.util.List;

public class PersonaJuridica extends Persona {

  private String razonSocial;
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
}