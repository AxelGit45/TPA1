package arg.com.utn.donatrack.apis;

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
  private static final List<Camion> camiones = new ArrayList<>();
  private static final AtomicLong idRutaCounter = new AtomicLong(1);
  private static final AtomicLong idEntregaCounter = new AtomicLong(1);
  private static final AtomicLong idCamionCounter = new AtomicLong(1);

  // === RUTAS ===

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
      if (request == null || request.camionId == null) {
        return errorValidacion("El ID del camión es obligatorio");
      }
      if (request.entregaIds == null || request.entregaIds.isEmpty()) {
        return errorValidacion("Debe incluir al menos una entrega");
      }

      Long nuevoId = idRutaCounter.getAndIncrement();
      Ruta nueva = new Ruta(nuevoId, request.camionId, request.entregaIds);
      rutas.add(nueva);

      nueva.Iniciarse(entregas);

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
      if (request == null || request.camionId == null) {
        return errorValidacion("El ID del camión es obligatorio");
      }
      if (request.entregaIds == null || request.entregaIds.isEmpty()) {
        return errorValidacion("Debe incluir al menos una entrega");
      }

      ruta.setCamionId(request.camionId);
      ruta.setEntregaIds(request.entregaIds);
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
      if (request == null || (request.camionId == null && request.entregaIds == null)) {
        return errorValidacion("Debe enviar al menos un campo a actualizar");
      }
      if (request.camionId != null) ruta.setCamionId(request.camionId);
      if (request.entregaIds != null) ruta.setEntregaIds(request.entregaIds);
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

  // === ENTREGAS ===

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
      if (request.entidadBeneficiariaId == null) {
        return errorValidacion("El ID de la entidad beneficiaria es obligatorio");
      }

      LocalDate fecha;
      try {
        fecha = LocalDate.parse(request.fechaDeEntregaEsperada);
      } catch (DateTimeParseException e) {
        return errorValidacion("Formato de fecha inválido. Use YYYY-MM-DD");
      }

      Long nuevoId = idEntregaCounter.getAndIncrement();
      Entrega nueva = new Entrega(nuevoId, request.direccionEntidadBeneficiaria, fecha, request.entidadBeneficiariaId);
      if (request.donacionIds != null) {
        nueva.setDonacionIds(request.donacionIds);
      }
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
          switch (nuevoEstado) {
            case ENTRASLADO -> entrega.iniciarTraslado(request.camionId);
            case ENTREGADA -> entrega.confirmarRecepcionDeEntrega(request.camionId);
            case NORECIBIDA -> entrega.informarNoRecepcion(request.camionId);
            default -> entrega.cambiarEstado(nuevoEstado);
          }
        } catch (IllegalArgumentException e) {
          return errorValidacion("Estado de entrega inválido. Valores válidos: PENDIENTE, ENTRASLADO, ENTREGADA, NORECIBIDA");
        }
      }

      return Response.ok(entrega).build();
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

  // === CAMIONES ===

  @GET
  @Path("/camiones/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response obtenerCamion(@PathParam("id") Long id) {
    try {
      Camion camion = buscarCamionPorId(id);
      if (camion == null) {
        return errorNoEncontrado("No existe un camión con id " + id);
      }
      return Response.ok(camion).build();
    } catch (Exception e) {
      return errorServidor(e);
    }
  }

  @POST
  @Path("/camiones")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response crearCamion(Camion request) {
    try {
      if (request == null || request.getPatente() == null) {
        return errorValidacion("La patente del camión es obligatoria");
      }
      Long nuevoId = idCamionCounter.getAndIncrement();
      Camion nuevo = new Camion(request.getPatente(), request.getVolumen(), request.getAltura(), request.getCapacidadDeCarga());
      nuevo.setId(nuevoId);
      nuevo.setIdGps(request.getIdGps());
      camiones.add(nuevo);

      URI location = UriBuilder.fromResource(LogisticaAPI.class)
          .path("/camiones/{id}")
          .build(nuevoId);

      return Response.created(location).entity(nuevo).build();
    } catch (Exception e) {
      return errorServidor(e);
    }
  }

  // === MONITOREO GPS ===

  @POST
  @Path("/monitoreo")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response reportarUbicacionGps(GpsRequest request) {
    try {
      if (request == null || request.idGps == null || request.latitud == null || request.longitud == null) {
        return errorValidacion("Faltan datos del GPS");
      }

      Camion camionEncontrado = camiones.stream()
          .filter(c -> request.idGps.equals(c.getIdGps()))
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

   // === CALLBACK Y LOTES (Requerimientos de Integración Externa) ===

   @POST
   @Path("/rutas/callback")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response callbackPlanificacionRutas(CallbackRutasRequest request) {
     try {
       if (request == null || request.rutasGeneradas == null) {
         return errorValidacion("Estructura de callback inválida");
       }

       for (Ruta nuevaRuta : request.rutasGeneradas) {
         Long nuevoId = idRutaCounter.getAndIncrement();
         nuevaRuta.setId(nuevoId);
         rutas.add(nuevaRuta);
         nuevaRuta.Iniciarse(entregas);
       }

       if (request.donacionesSinAsignar != null && !request.donacionesSinAsignar.isEmpty()) {
         System.out.println("[Callback] Donaciones sin asignar recibidas: " + request.donacionesSinAsignar.size());
       }

       return Response.ok(Map.of(
           "status", "OK",
           "rutasRegistradas", request.rutasGeneradas.size()
       )).build();
     } catch (Exception e) {
       return errorServidor(e);
     }
   }

   private <T> List<List<T>> dividirEnLotes(List<T> listaOriginal, int tamanoLote) {
     List<List<T>> lotes = new ArrayList<>();
     for (int i = 0; i < listaOriginal.size(); i += tamanoLote) {
       lotes.add(listaOriginal.subList(i, Math.min(i + tamanoLote, listaOriginal.size())));
     }
     return lotes;
   }

  // === DTOs internos ===

  public static class RutaRequest {
    public Long camionId;
    public List<Long> entregaIds;
  }

  public static class RutaPatchRequest {
    public Long camionId;
    public List<Long> entregaIds;
  }

  public static class EntregaRequest {
    public String direccionEntidadBeneficiaria;
    public String fechaDeEntregaEsperada;
    public Long entidadBeneficiariaId;
    public List<Long> donacionIds;
  }

  public static class EntregaPatchRequest {
    public String direccionEntidadBeneficiaria;
    public String fechaDeEntregaEsperada;
    public String estadoEntrega;
    public Long camionId;
  }

   public static class GpsRequest {
     public String idGps;
     public Double latitud;
     public Double longitud;
   }

   public static class CallbackRutasRequest {
     public List<Ruta> rutasGeneradas;
     public List<Long> donacionesSinAsignar;
   }

  // === Helpers ===

  private Ruta buscarRutaPorId(Long id) {
    return rutas.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
  }

  private Entrega buscarEntregaPorId(Long id) {
    return entregas.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
  }

  private Camion buscarCamionPorId(Long id) {
    return camiones.stream().filter(c -> id.equals(c.getId())).findFirst().orElse(null);
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
