package arg.com.utn.donatrack.apis;

import arg.com.utn.donatrack.logistica.Camion;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogisticaAPITest {

  private LogisticaAPI api;

  @BeforeEach
  public void init() {
    api = new LogisticaAPI();
  }

  @Test
  public void reportarUbicacionGps_DebeActualizarLatitudYLongitudDelCamion() {

    LogisticaAPI.GpsRequest gpsRequestCrear = new LogisticaAPI.GpsRequest();
    Camion camion = new Camion("AB123CD", 100, 200, 1000);
    camion.setIdGps("TRK-99");
    api.crearCamion(camion);

    LogisticaAPI.GpsRequest gpsRequest = new LogisticaAPI.GpsRequest();
    gpsRequest.idGps = "TRK-99";
    gpsRequest.latitud = -34.6037;
    gpsRequest.longitud = -58.3816;

    Response respuesta = api.reportarUbicacionGps(gpsRequest);

    assertEquals(200, respuesta.getStatus());
  }
}
