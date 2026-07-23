package asignacionDeDonaciones;

import donacion.Donacion;
import java.util.List;

public class ResultadoMatchmaking {
  private Donacion donacion;
  private List<ResultadoAlgoritmo> resultadosMatchmaking;

  public ResultadoMatchmaking(Donacion donacion, List<ResultadoAlgoritmo> resultadosMatchmaking){
    this.donacion = donacion;
    this.resultadosMatchmaking = resultadosMatchmaking;
  }
}
