package arg.com.utn.donatrack.donaciones;

import java.util.List;

public class ComponenteGeneradorDeRutas implements ServiciosExternoGenerador {
  ComponenteExterno adaptee;

  public ComponenteGeneradorDeRutas() {
    this.adaptee = new ComponenteExterno();
  }

  public ComponenteGeneradorDeRutas(ComponenteExterno adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public List<Long> planificacionDeRutas(List<Donacion> donacionesAsignadas, List<Long> camionesEnDeposito) {
    List<Long> resultado = adaptee.generarRuta(donacionesAsignadas, camionesEnDeposito);
    return resultado;
  }
}
