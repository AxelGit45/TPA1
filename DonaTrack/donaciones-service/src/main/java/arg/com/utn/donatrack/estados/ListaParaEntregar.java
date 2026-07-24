package arg.com.utn.donatrack.estados;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import java.util.List;

public class ListaParaEntregar extends EstadoDonacion{

  private String ruta;

  public ListaParaEntregar(){}

  public ListaParaEntregar(String ruta){

    this.ruta = ruta;

  }

  public String getRuta() {
    return ruta;
  }

  @Override
  public void asignacionDonaciones(List<EntidadBeneficiaria> entidades, Donacion donacion){
    throw new RuntimeException("No es posible realizar el matchmaking, estado incorrecto");
  }

}