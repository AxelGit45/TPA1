package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import java.util.List;

public interface Algoritmo {
  public List<EntidadBeneficiaria> ejecutar(Donacion donacion, List<EntidadBeneficiaria> entidades);
}
