package arg.com.utn.donatrack.apis;

import arg.com.utn.donatrack.logistica.Camion;
import arg.com.utn.donatrack.logistica.Entrega;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
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

    Camion camion = new Camion("AB123CD", 100, 200, 1000);
    camion.setIdGps("TRK-99");

    LogisticaAPI.RutaRequest rutaRequest = new LogisticaAPI.RutaRequest();
    rutaRequest.camion = camion;
    rutaRequest.entregas = List.of(new Entrega(1L, "Dirección Test", LocalDate.now(), null));
    api.crearRuta(rutaRequest);

    LogisticaAPI.GpsRequest gpsRequest = new LogisticaAPI.GpsRequest();
    gpsRequest.idGps = "TRK-99";
    gpsRequest.latitud = -34.6037;
    gpsRequest.longitud = -58.3816;

    Response respuesta = api.reportarUbicacionGps(gpsRequest);

    assertEquals(200, respuesta.getStatus());

    assertEquals(-34.6037, camion.getLatitud());
    assertEquals(-58.3816, camion.getLongitud());
  }
}
