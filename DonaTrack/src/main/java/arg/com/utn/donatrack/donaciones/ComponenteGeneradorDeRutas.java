package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.logistica.Camion;
import arg.com.utn.donatrack.logistica.Ruta;

import java.util.ArrayList;
import java.util.List;

public class ComponenteGeneradorDeRutas implements  ServiciosExternoGenerador {
  ComponenteExterno adaptee;
  @Override
  public List<Ruta> planificacionDeRutas(List<Donacion> donacionesAsignadas, List<Camion> camionesEnDeposito) {
    ////SIMULAMOS ASINCRONISMO
    List<Ruta> resultado = adaptee.generarRuta(donacionesAsignadas,  camionesEnDeposito);
    //TODO
    return resultado;
  }
}
