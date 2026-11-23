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
  private CompatibilidadSemantica compatibilidadSemantica = new CompatibilidadSemantica();
  @Transient
  private PrioridadASubAtendidos prioridadASubAtendidos = new PrioridadASubAtendidos();

  @Transient
  public ComponenteAsignadorDeDestinatarios componenteExterno = new ComponenteAsignadorDeDestinatarios();

  public EnDeposito() { }

  @Override
  public ResultadoMatchmaking asignacionDonaciones(List<EntidadBeneficiaria> entidades, Donacion donacion){
      algoritmos.add(compatibilidadSemantica);
      algoritmos.add(prioridadASubAtendidos);

      List<ResultadoAlgoritmo> resultadosDeAlgoritmos = algoritmos.stream().map(algoritmo -> algoritmo.ejecutar(donacion,entidades)).toList();

      ResultadoMatchmaking ranking = new ResultadoMatchmaking(donacion, resultadosDeAlgoritmos);
      return ranking; // Resultado de la ejecución de NUESTROS algoritmos

    //EntidadBeneficiaria entidad = componenteExterno.obtenerResultadoDeAlgoritmosPorDonacion(ranking);
                        // Entidad/ NULL
    //Si hay concincidencias con la entiad
    // MostrarAlAdmin(entidad, Donacion)
    //Si no hay
    //MostrarAlAdmin(ranking, Donacion)
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
