package asignacionDeDonaciones;

import asignacionDeDonaciones.algoritmos.Algoritmo;
import java.util.List;

public class ResultadoAlgoritmo {
  private Algoritmo algoritmo;
  private List<MatchEntidad> resultadosAlgoritmo;

  public ResultadoAlgoritmo(Algoritmo algoritmo, List<MatchEntidad> resultadosDelAlgoritmo){
    this.algoritmo = algoritmo;
    this.resultadosAlgoritmo = resultadosDelAlgoritmo;
  }

  public List<MatchEntidad> getResultadosAlgoritmo(){return resultadosAlgoritmo;}

}
