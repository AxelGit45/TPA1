package Modelos;
import java.util.List;

public class PersonaJuridica {
  private String razonSocial;
  private TipoOrganizacion tipo;
  private String rubo;
  private List<PersonaFisica> representantes;
  private String email;

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
    return rubo;
  }

  public void setRubo(String rubo) {
    this.rubo = rubo;
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