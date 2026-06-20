package arg.com.utn.donatrack.estados;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;

public class AsignacionRealizada extends EstadoDonacion{

  private EntidadBeneficiaria entidadBeneficiaria;

  public AsignacionRealizada(EntidadBeneficiaria entidadBeneficiaria){

    this.entidadBeneficiaria = entidadBeneficiaria;

  }

}
