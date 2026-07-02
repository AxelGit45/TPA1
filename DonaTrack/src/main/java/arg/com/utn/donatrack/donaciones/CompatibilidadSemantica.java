package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import java.util.Comparator;
import java.util.List;

public class CompatibilidadSemantica implements Algoritmo {

  @Override
  public ResultadoAlgoritmo ejecutar(Donacion donacion, List<EntidadBeneficiaria> entidades) {

//comparar por subcategoria
    //LA ENTIDAD BENEFICIARIA ES QUIEN DEBE SABER SI NECESITA O NO ESA DONACION
    //PUEDO CREAR DIRECTAMENTE RESULTADO ALGORITMO PARA CONTENER EL RESULTADO Y CON QUE ALGORITMO SE OBTUVO
    //new ResultadoAlgoritmo(this)
    List<MatchEntidad> ranking = entidades.stream()
        .map(entidad -> new MatchEntidad(entidad, entidad.cuantoNecesita(donacion)) )
        .sorted(Comparator.comparing(MatchEntidad::getPuntaje)
            .reversed()).limit(10).toList();
    ResultadoAlgoritmo resultadoDelAlgoritmo =  new ResultadoAlgoritmo(this, ranking);
    // ESTA LINEA POR DEFECTO ORDENA DE MENOR A MAYOR: Comparator.comparing(MatchEntidad::getPuntaje)
    // ASI QUE LA INVIERTO PARA OBTENER EL ORDEN DE MAYOR A MENOR CON reversed() -->  sorted(Comparator.comparing(MatchEntidad::getPuntaje).reversed())

    //entidad.cuantoNecesita(donacion)
    //donacion.getBienes().stream().map(bien -> bien.getSubcategoria() == necesidad.getSubcategoria());
    //entidades.stream().forEach();
    //entidades.stream().filter(entidad -> entidad.getNecesidades().stream().map(necesidad ->  donacion.getBienes().stream().forEach(bien-> bien.getSubcategoria() == necesidad.getSubcategoria()) );
    //[e1, e2, e3, e4] -> [e3 e4]

    //(lista de necesidades, lista de bienes)
    //(necesidad, lista de bienes)

    //entidad.aplicarCriterio()

    return resultadoDelAlgoritmo ; // PRIMER ALGORITMO LISTO
  }

}
