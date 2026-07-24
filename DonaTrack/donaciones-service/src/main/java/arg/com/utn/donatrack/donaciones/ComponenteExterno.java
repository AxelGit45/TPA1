package arg.com.utn.donatrack.donaciones;

import java.util.List;

public class ComponenteExterno {

    List<Long> generarRuta(List<Donacion> donacionesAsignadas, List<Long> camionesEnDeposito) {
        //TODO - stub: en producción llamaría al microservicio de logística
        return List.of();
    }
}
