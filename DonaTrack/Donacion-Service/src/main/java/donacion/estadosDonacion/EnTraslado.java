package donacion.estadosDonacion;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.logistica.Camion;
import java.util.List;

public class EnTraslado extends EstadoDonacion{

  private Camion camion;

  //public EnTraslado(){}

  public EnTraslado(Camion camion){

    this.camion = camion;

  }

  public Camion getCamion() {
    return camion;
  }

  @Override
  public void asignacionDonaciones(List<EntidadBeneficiaria> entidades, Donacion donacion){
    throw new RuntimeException("No es posible realizar el matchmaking, estado incorrecto");
  }

}