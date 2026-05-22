package Modelos;
import Enums.TipoOrganizacion;
import java.util.List;

public class PersonaJuridica {
  private String razonSocial;
  private TipoOrganizacion tipo;
  private String rubro;
  private List<PersonaFisica> representantes;
  private String email;
  private UsuarioDonante user;

  public PersonaJuridica(String razonSocial, TipoOrganizacion tipo, String rubo, List<PersonaFisica> representantes, String email) {
    this.razonSocial = razonSocial;
    this.tipo = tipo;
    this.rubro = rubo;
    this.representantes = representantes;
    this.email = email;
  }

}