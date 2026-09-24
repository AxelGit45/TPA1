package arg.com.utn.donatrack.estados;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("VENCIDA")
public class Vencida extends EstadoDonacion{

  @Override
  public void asignacionDonaciones(List<EntidadBeneficiaria> entidades, Donacion donacion){
    throw new RuntimeException("No es posible realizar el matchmaking, estado incorrecto");
  }
}