package arg.com.utn.donatrack.clients;

import arg.com.utn.donatrack.dtos.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executors;

public class LogisticaClient {

    private final String baseUrl;
    private final HttpClient httpClient;

    public LogisticaClient() {
        this("http://127.0.0.1:8081");
    }

    public LogisticaClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(5))
            .executor(Executors.newCachedThreadPool(r -> {
                Thread t = new Thread(r, "logistica-http");
                t.setDaemon(true);
                return t;
            }))
            .build();
    }

    public EntregaDTO obtenerEntrega(Long id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/logistica/entregas/" + id))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return parseEntregaDTO(response.body());
            }
        } catch (Exception e) {
            System.err.println("[LogisticaClient] Error obtener entrega " + id + ": " + e.getMessage());
        }
        return null;
    }

    public CamionDTO obtenerCamion(Long id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/logistica/camiones/" + id))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return parseCamionDTO(response.body());
            }
        } catch (Exception e) {
            System.err.println("Error al obtener camion " + id + ": " + e.getMessage());
        }
        return null;
    }

    private EntregaDTO parseEntregaDTO(String json) {
        EntregaDTO dto = new EntregaDTO();
        dto.setId(extractLong(json, "id"));
        dto.setDireccionEntidadBeneficiaria(extractString(json, "direccionEntidadBeneficiaria"));
        dto.setFechaDeEntregaEsperada(extractString(json, "fechaDeEntregaEsperada"));
        dto.setEstadoEntrega(extractString(json, "estadoEntrega"));
        dto.setEntidadBeneficiariaId(extractLong(json, "entidadBeneficiariaId"));
        return dto;
    }

    private CamionDTO parseCamionDTO(String json) {
        CamionDTO dto = new CamionDTO();
        dto.setId(extractLong(json, "id"));
        dto.setPatente(extractString(json, "patente"));
        dto.setIdGps(extractString(json, "idGps"));
        dto.setLatitud(extractDouble(json, "latitud"));
        dto.setLongitud(extractDouble(json, "longitud"));
        return dto;
    }

    private Long extractLong(String json, String field) {
        String key = "\"" + field + "\":";
        int start = json.indexOf(key);
        if (start == -1) return null;
        start += key.length();
        while (start < json.length() && json.charAt(start) == ' ') start++;
        int end = start;
        while (end < json.length() && (Character.isDigit(json.charAt(end)) || json.charAt(end) == '-')) end++;
        if (end == start) return null;
        return Long.parseLong(json.substring(start, end));
    }

    private String extractString(String json, String field) {
        String key = "\"" + field + "\":\"";
        int start = json.indexOf(key);
        if (start == -1) return null;
        start += key.length();
        int end = json.indexOf("\"", start);
        if (end == -1) return null;
        return json.substring(start, end);
    }

    private Double extractDouble(String json, String field) {
        String key = "\"" + field + "\":";
        int start = json.indexOf(key);
        if (start == -1) return null;
        start += key.length();
        while (start < json.length() && json.charAt(start) == ' ') start++;
        int end = start;
        while (end < json.length() && (Character.isDigit(json.charAt(end)) || json.charAt(end) == '.' || json.charAt(end) == '-')) end++;
        if (end == start) return null;
        return Double.parseDouble(json.substring(start, end));
    }
}
