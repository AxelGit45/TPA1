package arg.com.utn.donatrack.donaciones;

import java.util.List;

public interface ServiciosExternoGenerador {
     List<Long> planificacionDeRutas(List<Donacion> donacionesAsignadas, List<Long> camionesEnDeposito);
}
