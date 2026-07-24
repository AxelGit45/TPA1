package arg.com.utn.donatrack.donaciones;

import java.util.ArrayList;
import java.util.List;

public class ComponenteAsignadorDeDestinatarios {

  public void obtenerResultadoDeAlgoritmosPorDonacion(ResultadoMatchmaking resultado){
    resultadosAsignacionDeDonaciones.add(resultado);
  }

  /** Resultados de asignar donaciones, lo que utiliza el componente externo junto
   * con el administrador para asignarle un destino
   * a la donacion**/
  public List<ResultadoMatchmaking> resultadosAsignacionDeDonaciones = new ArrayList<>();
  public List<ResultadoMatchmaking> getResultadosAsignacionDeDonaciones(){return resultadosAsignacionDeDonaciones;}
  /**Representa lo que nos devuelve el componente externo, las asignaciones en estado
   * Asignacion Realizada **/
  public List<Donacion> donacionesAsignadas;
}
