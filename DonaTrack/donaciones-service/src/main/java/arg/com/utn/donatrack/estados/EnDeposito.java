package arg.com.utn.donatrack.estados;

import arg.com.utn.donatrack.donaciones.*;
import arg.com.utn.donatrack.entidadesBeneficiarias.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("EN_DEPOSITO")
public class EnDeposito extends EstadoDonacion{

  @Transient
  public List<Algoritmo> algoritmos = new ArrayList<>();
  @Transient
  public ComponenteAsignadorDeDestinatarios componenteExterno = new ComponenteAsignadorDeDestinatarios();

  public EnDeposito() { }

  @Override
  public void asignacionDonaciones(List<EntidadBeneficiaria> entidades, Donacion donacion){

      List<ResultadoAlgoritmo> resultadosDeAlgoritmos = algoritmos.stream().map(algoritmo -> algoritmo.ejecutar(donacion,entidades)).toList();

      ResultadoMatchmaking ranking = new ResultadoMatchmaking(donacion, resultadosDeAlgoritmos);

      componenteExterno.obtenerResultadoDeAlgoritmosPorDonacion(ranking);
  }

  @Override
  public void setAlgoritmo(Algoritmo algoritmo) {
    this.algoritmos.add(algoritmo);
  }

  @Override
  public void setComponenteExterno(ComponenteAsignadorDeDestinatarios componenteExterno){
    this.componenteExterno = componenteExterno;
  }
}
