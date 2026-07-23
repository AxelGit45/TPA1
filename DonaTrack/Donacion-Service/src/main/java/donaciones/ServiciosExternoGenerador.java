package donaciones;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.logistica.Camion;
import arg.com.utn.donatrack.logistica.Ruta;

import java.util.List;

public interface ServiciosExternoGenerador {
     List<Ruta> planificacionDeRutas(List<Donacion> donacionesAsignadas, List<Camion> camionesEnDeposito);
}
