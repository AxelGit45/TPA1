package Modelos;
import Enums.TipoOrganizacion;
import Errors.ErrorParametrosConstructor;

import java.util.List;

public class PersonaJuridica {
  private String razonSocial;
  private TipoOrganizacion tipo;
  private String rubro;
  private List<PersonaFisica> representantes;
  private String email;
  private UsuarioDonante user;

  public PersonaJuridica(String razonSocial, TipoOrganizacion tipo, String rubro, List<PersonaFisica> representantes, String email) {
    validarConstructor(razonSocial, tipo, rubro, representantes, email);
    this.razonSocial = razonSocial;
    this.tipo = tipo;
    this.rubro = rubro;
    this.representantes = representantes;
    this.email = email;
  }

  private void validarConstructor(String razonSocial, TipoOrganizacion tipo, String rubo, List<PersonaFisica> representantes, String email) {
    if(razonSocial == null|| tipo ==null|| rubro == null || representantes ==null || email == null){
      throw  new ErrorParametrosConstructor("Parametros nulos en el constructor");
    }
  }
}