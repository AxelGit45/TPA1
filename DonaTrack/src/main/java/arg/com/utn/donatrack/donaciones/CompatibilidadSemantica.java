package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import java.util.List;

public class CompatibilidadSemantica implements Algoritmo {

  @Override
  public List<EntidadBeneficiaria> ejecutar(Donacion donacion, List<EntidadBeneficiaria> entidades) {


    //donacion.getBienes().stream().map(bien -> bien.getSubcategoria() == necesidad.getSubcategoria());
    //entidades.stream().forEach();
    //entidades.stream().filter(entidad -> entidad.getNecesidades().stream().map(necesidad ->  donacion.getBienes().stream().forEach(bien-> bien.getSubcategoria() == necesidad.getSubcategoria()) );
    //[e1, e2, e3, e4] -> [e3 e4]

    //(lista de necesidades, lista de bienes)
    //(necesidad, lista de bienes)

    //entidad.aplicarCriterio()
    return List.of();
  }

}
