package arg.com.utn.donatrack.clients;

import arg.com.utn.donatrack.dtos.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class DonacionesClient {

    private static final String BASE_URL_DEFAULT = "http://127.0.0.1:8080";
    private final String baseUrl;
    private final HttpClient httpClient;

    public DonacionesClient() {
        this(BASE_URL_DEFAULT);
    }

    public DonacionesClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(5))
            .executor(Executors.newCachedThreadPool(r -> {
                Thread t = new Thread(r, "donaciones-http");
                t.setDaemon(true);
                return t;
            }))
            .build();
    }

    public DonacionDTO obtenerDonacion(Long id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/donaciones/" + id))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return parseDonacionDTO(response.body());
            }
        } catch (Exception e) {
            System.err.println("[DonacionesClient] Error obtener donacion " + id + ": " + e.getMessage());
        }
        return null;
    }

    public List<PersonaDTO> obtenerDonadores(Long donacionId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/donaciones/" + donacionId + "/donadores"))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return parsePersonaDTOList(response.body());
            }
        } catch (Exception e) {
            System.err.println("[DonacionesClient] Error obtener donadores: " + e.getMessage());
        }
        return List.of();
    }

    public EntidadBeneficiariaDTO obtenerEntidad(Long id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/entidades/" + id))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return parseEntidadDTO(response.body());
            }
        } catch (Exception e) {
            System.err.println("[DonacionesClient] Error obtener entidad " + id + ": " + e.getMessage());
        }
        return null;
    }

    public List<ContactoDTO> obtenerContactosEntidad(Long entidadId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/entidades/" + entidadId + "/contactos"))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return parseContactoDTOList(response.body());
            }
        } catch (Exception e) {
            System.err.println("[DonacionesClient] Error obtener contactos de entidad: " + e.getMessage());
        }
        return List.of();
    }

    public void cambiarEstadoDonacion(Long donacionId, String nuevoEstado, Long camionId, Long entregaId, String justificacion) {
        try {
            StringBuilder body = new StringBuilder();
            body.append("{\"estado\":\"").append(nuevoEstado).append("\"");
            if (camionId != null) body.append(",\"camionId\":").append(camionId);
            if (entregaId != null) body.append(",\"entregaId\":").append(entregaId);
            if (justificacion != null) body.append(",\"justificacion\":\"").append(justificacion).append("\"");
            body.append("}");

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/donaciones/" + donacionId + "/estado-nombre"))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(10))
                .PUT(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    System.out.println("[DonacionesClient] Estado donacion " + donacionId + " -> " + nuevoEstado + " (HTTP " + response.statusCode() + ")");
                })
                .exceptionally(ex -> {
                    System.err.println("[DonacionesClient] Error cambiar estado donacion " + donacionId + ": " + ex.getMessage());
                    return null;
                });
        } catch (Exception e) {
            System.err.println("[DonacionesClient] Error cambiar estado donacion " + donacionId + ": " + e.getMessage());
        }
    }

    private DonacionDTO parseDonacionDTO(String json) {
        DonacionDTO dto = new DonacionDTO();
        dto.setId(extractLong(json, "id"));
        dto.setEstado(extractString(json, "tipoEstado"));
        return dto;
    }

    private EntidadBeneficiariaDTO parseEntidadDTO(String json) {
        EntidadBeneficiariaDTO dto = new EntidadBeneficiariaDTO();
        dto.setId(extractLong(json, "id"));
        dto.setRazonSocial(extractString(json, "razonSocial"));
        dto.setDireccion(extractString(json, "direccion"));
        return dto;
    }

    private List<PersonaDTO> parsePersonaDTOList(String json) {
        List<PersonaDTO> result = new ArrayList<>();
        int idx = 0;
        while (true) {
            int objStart = json.indexOf("{", idx);
            if (objStart == -1) break;
            int objEnd = json.indexOf("}", objStart);
            if (objEnd == -1) break;
            String obj = json.substring(objStart, objEnd + 1);
            PersonaDTO dto = new PersonaDTO();
            dto.setId(extractLong(obj, "id"));
            dto.setNombre(extractString(obj, "nombre"));
            result.add(dto);
            idx = objEnd + 1;
        }
        return result;
    }

    private List<ContactoDTO> parseContactoDTOList(String json) {
        List<ContactoDTO> result = new ArrayList<>();
        int idx = 0;
        while (true) {
            int objStart = json.indexOf("{", idx);
            if (objStart == -1) break;
            int objEnd = json.indexOf("}", objStart);
            if (objEnd == -1) break;
            String obj = json.substring(objStart, objEnd + 1);
            ContactoDTO dto = new ContactoDTO();
            dto.setTipo(extractString(obj, "tipo"));
            dto.setValor(extractString(obj, "valor"));
            if (dto.getTipo() == null) {
                dto.setTipo("MAIL");
                dto.setValor(extractString(obj, "direccion"));
            }
            result.add(dto);
            idx = objEnd + 1;
        }
        return result;
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
}
