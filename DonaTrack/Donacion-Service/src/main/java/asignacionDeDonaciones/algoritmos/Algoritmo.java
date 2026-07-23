package asignacionDeDonaciones.algoritmos;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import asignacionDeDonaciones.ResultadoAlgoritmo;
import donacion.Donacion;
import java.util.List;

public interface Algoritmo {
  public ResultadoAlgoritmo ejecutar(Donacion donacion, List<EntidadBeneficiaria> entidades);
}
