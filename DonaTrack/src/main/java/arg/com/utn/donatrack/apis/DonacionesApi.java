package arg.com.utn.donatrack.apis;

import arg.com.utn.donatrack.donaciones.Bien;
import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.estados.EstadoDonacion;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/* CRUD PARA DONACIONES
Al todavía no tener persistencia, se mockea guardando en memoria
* */
@Path("/donaciones")
public class DonacionesApi {

  private static final List<Donacion> donaciones = new ArrayList<>();
  private static final AtomicLong idCounter = new AtomicLong(1);

  //Traer todas las donaciones
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Donacion> listar() {
    return donaciones;
  }

  //Traer una donación por id
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response obtener(@PathParam("id") Long id) {
    Donacion donacion = buscarPorId(id);

    if (donacion == null) {
      return errorNoEncontrada(id);
    }

    return Response.ok(donacion).build();
  }

  //Crear una nueva donación
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response crear(DonacionRequest request) {
    Long nuevoId = idCounter.getAndIncrement();

    Donacion nueva = new Donacion(request.bienes != null ? request.bienes : new ArrayList<>());
    nueva.setId(nuevoId);

    donaciones.add(nueva);

    URI location = UriBuilder.fromResource(DonacionesApi.class)
        .path(DonacionesApi.class, "obtener")
        .build(nuevoId);

    return Response.created(location).entity(nueva).build();
  }

  //Eliminar una donación
  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response eliminar(@PathParam("id") Long id) {
    Donacion donacion = buscarPorId(id);
    if (donacion == null) {
      return errorNoEncontrada(id);
    }

    donaciones.remove(donacion);

    return Response.ok().build();
  }

  // Actualizar estados

  @PUT
  @Path("/{id}/estado")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response actualizarEstado(@PathParam("id") Long id, EstadoDonacion nuevoEstado) {
    Donacion donacion = buscarPorId(id);
    if (donacion == null) {
      return errorNoEncontrada(id);
    }

    if (nuevoEstado == null) {
      Map<String, Object> error = new LinkedHashMap<>();
      error.put("error", "Debe indicar un estado válido");
      return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }

    donacion.cambiarEstado(nuevoEstado);

    return Response.ok(donacion).build();
  }

  // Funciones extra

  private Donacion buscarPorId(Long id) {
    return donaciones.stream()
        .filter(d -> d.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  private Response errorNoEncontrada(Long id) {
    Map<String, Object> error = new LinkedHashMap<>();
    error.put("error", "No existe una donación con id " + id);
    return Response.status(Response.Status.NOT_FOUND).entity(error).build();
  }

  // Request

  public static class DonacionRequest {
    public List<Bien> bienes;
  }
}

/*
  Ejemplos para probar todas las rutas (Postman)

  o POST http://localhost:8080/donaciones
    Content-Type: application/json

    {
      "bienes": [
        {
          "descripcion": "Arroz 1kg",
          "foto": "",
          "subcategoria": {
            "nombre": "ALIMENTOS_NO_PERECEDEROS",
            "categoriaPadre": { "nombre": "ALIMENTOS" },
            "exigeFechaVencimiento": true,
            "exigeEstadoUso": false
          },
          "cantidad": 5,
          "unidad": "KILOGRAMO",
          "fechaVencimiento": "2027-01-01",
          "estadoUso": "NUEVO",
          "perecedero": true
        }
      ]
    }

  o GET http://localhost:8080/donaciones
  o GET http://localhost:8080/donaciones/1
  o DELETE http://localhost:8080/donaciones/1

  PUT http://localhost:8080/donaciones/1/estado
  Content-Type: application/json

  Ejemplos de body según el estado:

  { "tipo": "EN_DEPOSITO" }

  { "tipo": "EN_TRASLADO", "camion": { ... } }

  { "tipo": "LISTA_PARA_ENTREGAR", "ruta": "Ruta 9 km 45" }

  { "tipo": "ENTREGADA", "entrega": { ... }, "camion": { ... } }

  { "tipo": "VENCIDA" }

 */