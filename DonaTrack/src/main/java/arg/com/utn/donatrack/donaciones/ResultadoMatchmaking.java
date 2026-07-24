package arg.com.utn.donatrack.donaciones;

import java.util.List;

public class ResultadoMatchmaking {
  private Donacion donacion;
  private List<ResultadoAlgoritmo> resultadosMatchmaking;

  public ResultadoMatchmaking(Donacion donacion, List<ResultadoAlgoritmo> resultadosMatchmaking) {
    this.donacion = donacion;
    this.resultadosMatchmaking = resultadosMatchmaking;
  }

  public Donacion getDonacion() { return donacion; }

  public List<ResultadoAlgoritmo> getResultadosMatchmaking() { return resultadosMatchmaking; }
}