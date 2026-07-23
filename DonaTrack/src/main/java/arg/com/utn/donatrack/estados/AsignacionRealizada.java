package arg.com.utn.donatrack.estados;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.personas.Persona;
import arg.com.utn.donatrack.personas.contactos.Contacto;

public class AsignacionRealizada extends EstadoDonacion{

  private EntidadBeneficiaria entidadBeneficiaria;
  private Donacion donacion;

  public AsignacionRealizada(EntidadBeneficiaria entidadBeneficiaria, Donacion donacion){

    this.entidadBeneficiaria = entidadBeneficiaria;
    this.donacion = donacion;

  }

  @Override
  public void notificar() {

    for (Contacto medioDeLaEntidad : this.entidadBeneficiaria.getContactos()) {

      medioDeLaEntidad.contactar("Se le ha asignado una donación.");

    }

    for (Persona donador : donacion.getDonadores()) {

      for (Contacto medioDelDonante : donador.getContactos()) {

        medioDelDonante.contactar("Una de sus donaciones ha sido asignada, muchas gracias!.");

      }

    }

  }

}
