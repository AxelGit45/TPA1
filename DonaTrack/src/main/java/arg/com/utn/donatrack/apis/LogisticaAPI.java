package arg.com.utn.donatrack.apis;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.logistica.Camion;
import arg.com.utn.donatrack.logistica.Entrega;
import arg.com.utn.donatrack.logistica.EstadoEntrega;
import arg.com.utn.donatrack.logistica.Ruta;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Path("/logistica")
public class LogisticaAPI {

  private static final List<Ruta> rutas = new ArrayList<>();
  private static final List<Entrega> entregas = new ArrayList<>();
  private static final AtomicLong idRutaCounter = new AtomicLong(1);
  private static final AtomicLong idEntregaCounter = new AtomicLong(1);

  @GET
  @Path("/rutas")
  @Produces(MediaType.APPLICATION_JSON)
  public Response listarRutas() {
    try {
      return Response.ok(rutas).build();
    } catch (Exception e) {
      return errorServidor(e);
    }
  }

  @GET
  @Path("/rutas/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response obtenerRuta(@PathParam("id") Long id) {
    try {
      Ruta ruta = buscarRutaPorId(id);
      if (ruta == null) {
        return errorNoEncontrado("No existe una ruta con id " + id);
      }
      return Response.ok(ruta).build();
    } catch (Exception e) {
      return errorServidor(e);
    }
  }

  @POST
  @Path("/rutas")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response crearRuta(RutaRequest request) {
    try {
      if (request == null || request.camion == null) {
        return errorValidacion("El camión es obligatorio");
      }
      if (request.camion.getPatente() == null || request.camion.getPatente().isBlank()) {
        return errorValidacion("La patente del camión es obligatoria");
      }
      if (request.entregas == null || request.entregas.isEmpty()) {
        return errorValidacion("Debe incluir al menos una entrega");
      }

      Long nuevoId = idRutaCounter.getAndIncrement();
      Ruta nueva = new Ruta(nuevoId, request.camion, request.entregas);
      rutas.add(nueva);

      URI location = UriBuilder.fromResource(LogisticaAPI.class)
          .path("/rutas/{id}")
          .build(nuevoId);

      return Response.created(location).entity(nueva).build();
    } catch (Exception e) {
      return errorServidor(e);
    }
  }

  @PUT
  @Path("/rutas/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response reemplazarRuta(@PathParam("id") Long id, RutaRequest request) {
    try {
      Ruta ruta = buscarRutaPorId(id);
      if (ruta == null) {
        return errorNoEncontrado("No existe una ruta con id " + id);
      }
      if (request == null || request.camion == null) {
        return errorValidacion("El camión es obligatorio");
      }
      if (request.camion.getPatente() == null || request.camion.getPatente().isBlank()) {
        return errorValidacion("La patente del camión es obligatoria");
      }
      if (request.entregas == null || request.entregas.isEmpty()) {
        return errorValidacion("Debe incluir al menos una entrega");
      }

      ruta.setCamion(request.camion);
      ruta.setEntregas(request.entregas);
      return Response.ok(ruta).build();
    } catch (Exception e) {
      return errorServidor(e);
    }
  }

  @PATCH
  @Path("/rutas/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response actualizarRuta(@PathParam("id") Long id, RutaPatchRequest request) {
    try {
      Ruta ruta = buscarRutaPorId(id);
      if (ruta == null) {
        return errorNoEncontrado("No existe una ruta con id " + id);
      }
      if (request == null || (request.camion == null && request.entregas == null)) {
        return errorValidacion("Debe enviar al menos un campo a actualizar");
      }
      return Response.ok(ruta).build();
    } catch (Exception e) {
      return errorServidor(e);
    }
  }

  @DELETE
  @Path("/rutas/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response eliminarRuta(@PathParam("id") Long id) {
    try {
      Ruta ruta = buscarRutaPorId(id);
      if (ruta == null) {
        return errorNoEncontrado("No existe una ruta con id " + id);
      }
      rutas.remove(ruta);
      return Response.ok().build();
    } catch (Exception e) {
      return errorServidor(e);
    }
  }

  @GET
  @Path("/entregas")
  @Produces(MediaType.APPLICATION_JSON)
  public Response listarEntregas() {
    try {
      return Response.ok(entregas).build();
    } catch (Exception e) {
      return errorServidor(e);
    }
  }

  @GET
  @Path("/entregas/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response obtenerEntrega(@PathParam("id") Long id) {
    try {
      Entrega entrega = buscarEntregaPorId(id);
      if (entrega == null) {
        return errorNoEncontrado("No existe una entrega con id " + id);
      }
      return Response.ok(entrega).build();
    } catch (Exception e) {
      return errorServidor(e);
    }
  }

  @POST
  @Path("/entregas")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response crearEntrega(EntregaRequest request) {
    try {
      if (request == null) {
        return errorValidacion("El cuerpo de la solicitud es obligatorio");
      }
      if (request.entidadBeneficiaria == null) {
        return errorValidacion("La entidad beneficiaria es obligatoria");
      }

      LocalDate fecha;
      try {
        fecha = LocalDate.parse(request.fechaDeEntregaEsperada);
      } catch (DateTimeParseException e) {
        return errorValidacion("Formato de fecha inválido. Use YYYY-MM-DD");
      }

      Long nuevoId = idEntregaCounter.getAndIncrement();
      Entrega nueva = new Entrega(nuevoId, request.direccionEntidadBeneficiaria, fecha, request.entidadBeneficiaria);
      entregas.add(nueva);

      URI location = UriBuilder.fromResource(LogisticaAPI.class)
          .path("/entregas/{id}")
          .build(nuevoId);

      return Response.created(location).entity(nueva).build();
    } catch (Exception e) {
      return errorServidor(e);
    }
  }

  @PUT
  @Path("/entregas/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response reemplazarEntrega(@PathParam("id") Long id, EntregaRequest request) {
    try {
      Entrega entrega = buscarEntregaPorId(id);
      if (entrega == null) {
        return errorNoEncontrado("No existe una entrega con id " + id);
      }
      if (request.direccionEntidadBeneficiaria == null || request.direccionEntidadBeneficiaria.isBlank()) {
        return errorValidacion("La dirección de la entidad beneficiaria es obligatoria");
      }

      LocalDate fecha;
      try {
        fecha = LocalDate.parse(request.fechaDeEntregaEsperada);
      } catch (DateTimeParseException e) {
        return errorValidacion("Formato de fecha inválido. Use YYYY-MM-DD");
      }

      entrega.setDireccionEntidadBeneficiaria(request.direccionEntidadBeneficiaria);
      entrega.setFechaDeEntregaEsperada(fecha);
      return Response.ok(entrega).build();
    } catch (Exception e) {
      return errorServidor(e);
    }
  }

  @PATCH
  @Path("/entregas/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response actualizarEntrega(@PathParam("id") Long id, EntregaPatchRequest request) {
    try {
      Entrega entrega = buscarEntregaPorId(id);
      if (entrega == null) {
        return errorNoEncontrado("No existe una entrega con id " + id);
      }
      if (request == null || (request.direccionEntidadBeneficiaria == null && request.fechaDeEntregaEsperada == null && request.estadoEntrega == null)) {
        return errorValidacion("Debe enviar al menos un campo a actualizar");
      }

      if (request.direccionEntidadBeneficiaria != null) {
        if (request.direccionEntidadBeneficiaria.isBlank()) {
          return errorValidacion("La dirección no puede estar vacía");
        }
        entrega.setDireccionEntidadBeneficiaria(request.direccionEntidadBeneficiaria);
      }

      if (request.fechaDeEntregaEsperada != null) {
        try {
          LocalDate fecha = LocalDate.parse(request.fechaDeEntregaEsperada);
          entrega.setFechaDeEntregaEsperada(fecha);
        } catch (DateTimeParseException e) {
          return errorValidacion("Formato de fecha inválido. Use YYYY-MM-DD");
        }
      }

      if (request.estadoEntrega != null) {
        try {
          EstadoEntrega nuevoEstado = EstadoEntrega.valueOf(request.estadoEntrega.toUpperCase());
          entrega.cambiarEstado(nuevoEstado);
        } catch (IllegalArgumentException e) {
          return errorValidacion("Estado de entrega inválido. Valores válidos: PENDIENTE, ENTRASLADO, ENTREGADA, NORECIBIDA");
        }
      }

      return Response.ok(entrega).build();
    } catch (Exception e) {
      return errorServidor(e);
    }
  }

  @POST
  @Path("/monitoreo")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response reportarUbicacionGps(GpsRequest request) {
    try {
      if (request == null || request.idGps == null || request.latitud == null || request.longitud == null) {
        return errorValidacion("Faltan datos del GPS");
      }

      Camion camionEncontrado = rutas.stream()
          .map(Ruta::getCamion)
          .filter(camion -> request.idGps.equals(camion.getIdGps()))
          .findFirst()
          .orElse(null);

      if (camionEncontrado == null) {
        return errorNoEncontrado("No hay camiones registrados con el GPS ID: " + request.idGps);
      }

      camionEncontrado.setLatitud(request.latitud);
      camionEncontrado.setLongitud(request.longitud);

      return Response.ok("Ubicación actualizada correctamente").build();

    } catch (Exception e) {
      return errorServidor(e);
    }
  }


  @DELETE
  @Path("/entregas/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response eliminarEntrega(@PathParam("id") Long id) {
    try {
      Entrega entrega = buscarEntregaPorId(id);
      if (entrega == null) {
        return errorNoEncontrado("No existe una entrega con id " + id);
      }
      entregas.remove(entrega);
      return Response.ok().build();
    } catch (Exception e) {
      return errorServidor(e);
    }
  }

  public static class RutaRequest {
    public Camion camion;
    public List<Entrega> entregas;
  }

  public static class RutaPatchRequest {
    public Camion camion;
    public List<Entrega> entregas;
  }

  public static class EntregaRequest {
    public String direccionEntidadBeneficiaria;
    public String fechaDeEntregaEsperada;
    public EntidadBeneficiaria entidadBeneficiaria;
  }

  public static class EntregaPatchRequest {
    public String direccionEntidadBeneficiaria;
    public String fechaDeEntregaEsperada;
    public String estadoEntrega;
    public EntidadBeneficiaria entidadBeneficiaria;
  }

  public static class GpsRequest {
    public String idGps;
    public Double latitud;
    public Double longitud;
  }

  private Ruta buscarRutaPorId(Long id) {
    return rutas.stream()
        .filter(r -> r.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  private Entrega buscarEntregaPorId(Long id) {
    return entregas.stream()
        .filter(e -> e.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  private Response errorNoEncontrado(String mensaje) {
    Map<String, Object> error = new LinkedHashMap<>();
    error.put("error", mensaje);
    return Response.status(Response.Status.NOT_FOUND).entity(error).build();
  }

  private Response errorValidacion(String mensaje) {
    Map<String, Object> error = new LinkedHashMap<>();
    error.put("error", mensaje);
    return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
  }

  private Response errorServidor(Exception e) {
    Map<String, Object> error = new LinkedHashMap<>();
    error.put("error", "Error interno del servidor");
    error.put("detalle", e.getMessage());
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
  }
}