package arg.com.utn.donatrack.clients;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;


public class UsuariosClient {


  public record RegistroResultado(boolean ok, Long usuarioId, String error) {}

  private final String baseUrl;
  private final HttpClient httpClient;
  private final ObjectMapper mapper = new ObjectMapper();

  public UsuariosClient(String baseUrl) {
    this.baseUrl = baseUrl;
    this.httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_1_1)
        .connectTimeout(Duration.ofSeconds(5))
        .build();
  }

  public RegistroResultado registrarPersonaHumana(String nombre, String apellido, Integer edad,
                                                  Integer numeroDeDocumento, String genero, String direccion,
                                                  String email, String telefono, String whatsapp,
                                                  String medioPredeterminado, String contrasena) throws IOException {
    Map<String, Object> cuerpo = new LinkedHashMap<>();
    cuerpo.put("nombre", nombre);
    cuerpo.put("apellido", apellido);
    cuerpo.put("edad", edad);
    cuerpo.put("numeroDeDocumento", numeroDeDocumento);
    cuerpo.put("genero", genero);
    cuerpo.put("direccion", direccion);
    cuerpo.put("email", email);
    cuerpo.put("telefono", telefono);
    cuerpo.put("whatsapp", whatsapp);
    cuerpo.put("medioPredeterminado", medioPredeterminado);
    cuerpo.put("contrasena", contrasena);

    HttpResponse<String> respuesta = post("/registro/humanas", cuerpo);
    JsonNode json = leer(respuesta.body());

    if (respuesta.statusCode() / 100 == 2) {
      return new RegistroResultado(true, json.path("usuarioId").asLong(), null);
    }
    return new RegistroResultado(false, null, json.path("error").asText("No se pudo completar el registro"));
  }

  private HttpResponse<String> post(String ruta, Map<String, Object> cuerpo) throws IOException {
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(baseUrl + ruta))
          .header("Content-Type", "application/json")
          .header("Accept", "application/json")
          .timeout(Duration.ofSeconds(10))
          .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(cuerpo)))
          .build();
      return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IOException("Se interrumpió la llamada al servicio de donaciones", e);
    }
  }


  private JsonNode leer(String cuerpo) {
    try {
      return mapper.readTree(cuerpo);
    } catch (IOException e) {
      return mapper.createObjectNode();
    }
  }
}