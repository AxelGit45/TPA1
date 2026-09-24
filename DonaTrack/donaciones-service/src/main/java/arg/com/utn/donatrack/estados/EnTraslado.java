package arg.com.utn.donatrack.estados;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("EN_TRASLADO")
public class EnTraslado extends EstadoDonacion{

  private Long camionId;

  public EnTraslado(){}

  public EnTraslado(Long camionId){
    this.camionId = camionId;
  }

  public Long getCamionId() {
    return camionId;
  }

  @Override
  public void asignacionDonaciones(List<EntidadBeneficiaria> entidades, Donacion donacion){
    throw new RuntimeException("No es posible realizar el matchmaking, estado incorrecto");
  }

}
