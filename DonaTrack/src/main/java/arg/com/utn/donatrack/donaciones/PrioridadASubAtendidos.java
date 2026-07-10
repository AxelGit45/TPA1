package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import java.util.Comparator;
import java.util.List;

public class PrioridadASubAtendidos implements Algoritmo {

/*  @Override
  public ResultadoAlgoritmo ejecutar(Donacion donacion, List<EntidadBeneficiaria> entidades){

    List<MatchEntidad> resultadoDelAlgoritmo = entidades.stream()
        .map(entidad -> new MatchEntidad(entidad, entidad.getDonacionesRecibidasUltimoTrimestre()))
        .sorted(Comparator.comparing(MatchEntidad::getPuntaje)).limit(10).toList();
    ResultadoAlgoritmo resultado = new ResultadoAlgoritmo(this,resultadoDelAlgoritmo);
    return resultado;
  } */
}
