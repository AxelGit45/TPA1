package arg.com.utn.donatrack.apis;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@Path("/health")
public class HealthCheck {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Map<String, Object> healthCheck() {
    Map<String, Object> status = new LinkedHashMap<>();
    status.put("status", "UP");
    status.put("servicio", "DonaTrack");
    status.put("timestamp", Instant.now().toString());
    return status;
  }
}