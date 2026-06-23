package arg.com.utn.donatrack.logistica;

import arg.com.utn.donatrack.donaciones.Donacion;
import java.util.List;

public class Entrega {
  private String direccionEntidadBeneficiaria;
  private List<Donacion> donacionesAEntregar;

  public List<Donacion> getDonaciones() {
    return this.donacionesAEntregar;
  }
}
