package Modelos;
import Enums.TipoOrganizacion;
import java.util.List;

public class PersonaJuridica {
  private String razonSocial;
  private TipoOrganizacion tipo;
  private String rubro;
  private List<PersonaFisica> representantes;
  private String email;

  public PersonaJuridica(String razonSocial, TipoOrganizacion tipo, String rubo, List<PersonaFisica> representantes, String email) {
    this.razonSocial = razonSocial;
    this.tipo = tipo;
    this.rubro = rubo;
    this.representantes = representantes;
    this.email = email;
  }

  public String getRazonSocial() {
    return razonSocial;
  }

  public void setRazonSocial(String razonSocial) {
    this.razonSocial = razonSocial;
  }

  public TipoOrganizacion getTipo() {
    return tipo;
  }

  public void setTipo(TipoOrganizacion tipo) {
    this.tipo = tipo;
  }

  public String getRubo() {
    return rubro;
  }

  public void setRubo(String rubo) {
    this.rubro = rubo;
  }

  public List<PersonaFisica> getRepresentantes() {
    return representantes;
  }

  public void setRepresentantes(List<PersonaFisica> representantes) {
    this.representantes = representantes;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}