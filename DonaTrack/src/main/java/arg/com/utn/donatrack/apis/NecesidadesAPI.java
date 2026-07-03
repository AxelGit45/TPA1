package arg.com.utn.donatrack.apis;

import arg.com.utn.donatrack.donaciones.Subcategoria;
import arg.com.utn.donatrack.entidadesBeneficiarias.NecesidadExtraordinaria;
import arg.com.utn.donatrack.entidadesBeneficiarias.NecesidadRecurrente;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/* CRUD PARA NECESIDADES
Al todavía no tener persistencia, se mockea guardando en memoria
* */
@Path("/necesidades")
public class NecesidadesAPI {

  //Arrays de necesidades para mockear a falta de persistencia
  private static final List<NecesidadExtraordinaria> necesidadesExtraordinarias = new ArrayList<>();
  private static final List<NecesidadRecurrente> necesidadesRecurrentes = new ArrayList<>();

  //Contadores de id
  private static final AtomicLong idExtraordinariaCounter = new AtomicLong(1);
  private static final AtomicLong idRecurrenteCounter = new AtomicLong(1);

  // NECESIDADES EXTRAORDINARIAS

  //Traer todas las necesidades extraordinarias
  @GET
  @Path("/extraordinarias")
  @Produces(MediaType.APPLICATION_JSON)
  public List<NecesidadExtraordinaria> listar() {
    return necesidadesExtraordinarias;
  }

  //Traer una necesidad extraordinaria por id
  @GET
  @Path("/extraordinarias/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response obtener(@PathParam("id") Long id) {
    NecesidadExtraordinaria necesidadExtraordinaria = buscarExtraordinariaPorId(id);

    if (necesidadExtraordinaria == null) {
      return errorNoEncontrada(id);
    }

    return Response.ok(necesidadExtraordinaria).build();
  }

  //Crear una nueva necesidad Extraordinaria
  @POST
  @Path("/extraordinarias")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response crear(NecesidadExtraordinariaRequest request) {
    Long nuevoId = idExtraordinariaCounter.getAndIncrement();

    NecesidadExtraordinaria nueva = new NecesidadExtraordinaria(
        nuevoId,
        request.necesidad,
        request.cantidadNecesitada,
        request.descripcion,
        request.satisfecha != null ? request.satisfecha : false,
        request.recibidos != null ? request.recibidos : 0
    );

    necesidadesExtraordinarias.add(nueva);

    URI location = UriBuilder.fromResource(NecesidadesAPI.class)
        .path(NecesidadesAPI.class, "obtener")
        .build(nuevoId);

    return Response.created(location).entity(nueva).build();
  }

  //Eliminar una necesidad Extraordinaria
  @DELETE
  @Path("/extraordinarias/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response eliminar(@PathParam("id") Long id) {
    NecesidadExtraordinaria necesidadExtraordinaria = buscarExtraordinariaPorId(id);
    if (necesidadExtraordinaria == null) {
      return errorNoEncontrada(id);
    }

    necesidadesExtraordinarias.remove(necesidadExtraordinaria);

    return Response.ok().build();
  }

  //  NECESIDADES RECURRENTES

  //Traer todas las necesidades recurrentes
  @GET
  @Path("/recurrentes")
  @Produces(MediaType.APPLICATION_JSON)
  public List<NecesidadRecurrente> listarRecurrentes() {
    return necesidadesRecurrentes;
  }

  //Traer una necesidad recurrente por id
  @GET
  @Path("/recurrentes/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response obtenerRecurrente(@PathParam("id") Long id) {
    NecesidadRecurrente necesidadRecurrente = buscarRecurrentePorId(id);

    if (necesidadRecurrente == null) {
      return errorNoEncontrada(id);
    }

    return Response.ok(necesidadRecurrente).build();
  }

  //Crear una nueva necesidad Recurrente
  @POST
  @Path("/recurrentes")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response crearRecurrente(NecesidadRecurrenteRequest request) {
    Long nuevoId = idRecurrenteCounter.getAndIncrement();


    NecesidadRecurrente nueva = new NecesidadRecurrente(
        nuevoId,
        request.necesidad,
        request.cantidadNecesitada,
        request.descripcion,
        request.satisfecha != null ? request.satisfecha : false,
        request.recibidos != null ? request.recibidos : 0,
        request.fechaLimite
    );

    necesidadesRecurrentes.add(nueva);

    URI location = UriBuilder.fromResource(NecesidadesAPI.class)
        .path(NecesidadesAPI.class, "obtenerRecurrente")
        .build(nuevoId);

    return Response.created(location).entity(nueva).build();
  }

  //Eliminar una necesidad Recurrente
  @DELETE
  @Path("/recurrentes/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response eliminarRecurrente(@PathParam("id") Long id) {
    NecesidadRecurrente necesidadRecurrente = buscarRecurrentePorId(id);
    if (necesidadRecurrente == null) {
      return errorNoEncontrada(id);
    }

    necesidadesRecurrentes.remove(necesidadRecurrente);

    return Response.ok().build();
  }

  // Funciones extra

  private NecesidadExtraordinaria buscarExtraordinariaPorId(Long id) {
    return necesidadesExtraordinarias.stream()
        .filter(p -> p.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  private NecesidadRecurrente buscarRecurrentePorId(Long id) {
    return necesidadesRecurrentes.stream()
        .filter(p -> p.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  private Response errorNoEncontrada(Long id) {
    Map<String, Object> error = new LinkedHashMap<>();
    error.put("error", "No existe una necesidad con id " + id);
    return Response.status(Response.Status.NOT_FOUND).entity(error).build();
  }

  // ==================== DTOs DE REQUEST ====================

  public static class NecesidadRecurrenteRequest {
    public Subcategoria necesidad;
    public Integer cantidadNecesitada;
    public String descripcion;
    public Boolean satisfecha;
    public Integer recibidos;
    public ChronoLocalDate fechaLimite;
  }

  public static class NecesidadExtraordinariaRequest {
    public Subcategoria necesidad;
    public Integer cantidadNecesitada;
    public String descripcion;
    public Boolean satisfecha;
    public Integer recibidos;
  }
}

/*
  Ejemplos para probar todas las rutas (Postman)

  NECESIDADES EXTRAORDINARIAS

  o POST http://localhost:8080/necesidades/extraordinarias
    Content-Type: application/json

    Cuerpo:

    {
    "necesidad": {
      "nombre": "ROPA_ABRIGO",
      "categoriaPadre": {
        "nombre": "INDUMENTARIA"
      },
      "exigeFechaVencimiento": false,
      "exigeEstadoUso": true
    },
    "cantidadNecesitada": 10,
    "descripcion": "Sueter",
    "recibidos": 0
  }

  o GET http://localhost:8080/necesidades/extraordinarias
  o GET http://localhost:8080/necesidades/extraordinarias/1
  o DELETE http://localhost:8080/necesidades/extraordinarias/1

  o POST
  o GET
  o GET
  o DELETE

 */