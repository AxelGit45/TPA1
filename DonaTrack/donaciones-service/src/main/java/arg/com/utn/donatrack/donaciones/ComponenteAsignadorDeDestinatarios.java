package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import java.util.ArrayList;
import java.util.List;

public class ComponenteAsignadorDeDestinatarios {
  /** A falta de un Componente Externo real, en esta clase se hace una simulación
   de su código interno. **/

  /*
  /** Resultados de asignar donaciones, lo que utiliza el componente externo junto
   * con el administrador para asignarle un destino
   * a la donacion
  public List<ResultadoMatchmaking> resultadosAsignacionDeDonaciones = new ArrayList<>();
  public List<ResultadoMatchmaking> getResultadosAsignacionDeDonaciones(){return resultadosAsignacionDeDonaciones;}
  /**Representa lo que nos devuelve el componente externo, las asignaciones en estado
   * Asignacion Realizada
  public List<Donacion> donacionesAsignadas;
  */

  public ComponenteAsignadorDeDestinatarios (){

  }

  /** Metodo proporcionado por el componente externo para que, desde nuestro sistema, lo llamemos
   y le pasemos el resultado de nuestros algoritmos. **/
  public ResultadoComponenteAsignador obtenerResultadoDeAlgoritmosPorDonacion(ResultadoMatchmaking resultado){
    // resultadosAsignacionDeDonaciones.add(resultado);
    // List<ResultadoAlgoritmo> entidades = resultado.getResultadosMatchmaking();

    // Código del componente externo.
    // ResultadoComponenteAsignador resultadoComponente = new ResultadoComponenteAsignador(List<entidad>, donacion);
    // return resultadoComponente;
    List<ResultadoAlgoritmo> resultadosAlgoritmos = resultado.getResultadosMatchmaking();
    ResultadoAlgoritmo unResultadoAlgoritmo = resultadosAlgoritmos.get(1);
    List<EntidadBeneficiaria> listaEntidades = unResultadoAlgoritmo.getResultadosAlgoritmo().stream().map(matchEntidad -> matchEntidad.getEntidad()).toList();

    List<EntidadBeneficiaria> entidades = new ArrayList<>();
    entidades.add(listaEntidades.get(0));

    return new ResultadoComponenteAsignador(entidades, resultado.getDonacion());
  }


  public List<EntidadBeneficiaria> obtenerEntidades(){

    return new ArrayList<>();
  }
}
