package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.personas.contactos.Mail;
import java.util.List;

public class EntidadBeneficiaria {

  private String razonSocial;
  private String direccion;
  private Integer telefono;
  private List<Mail> correos;
  private List<Necesidad> necesidades;

  public EntidadBeneficiaria(String razonSocial, String direccion, Integer telefono,
                             List<Mail> correos, List<Necesidad> necesidades){

    this.razonSocial = razonSocial;
    this.direccion = direccion;
    this.telefono = telefono;
    this.correos = correos;
    this.necesidades = necesidades;

  }

}
