package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.logistica.Camion;
import arg.com.utn.donatrack.logistica.Ruta;
import java.util.List;

public class ComponenteGeneradorDeRutas {
  /** Donaciones con asignación realizada (Resultado del componente asignador) **/
  public List<Donacion> donacionesAsignadas;
  /** Aparte de las donaciones asignadas, se le manda los camiones disponibles**/
  public List<Camion> camionesDisponibles;

  /** El resultado del componente (las entregas a realizar por cada camión disponible)**/
  public List<Ruta> resultadoRutas;
}
