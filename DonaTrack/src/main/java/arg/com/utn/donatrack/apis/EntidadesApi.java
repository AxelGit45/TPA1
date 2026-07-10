package arg.com.utn.donatrack.apis;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.personas.contactos.Contacto;
import arg.com.utn.donatrack.personas.contactos.Mail;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/* CRUD PARA ENTIDADES BENEFICIARIAS
Al todavía no tener persistencia, se mockea guardando en memoria
* */
@Path("/entidades")
public class EntidadesApi {

  private static final List<EntidadBeneficiaria> entidades = new ArrayList<>();
  private static final AtomicLong idCounter = new AtomicLong(1);

  //Traer todas las entidades beneficiarias
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<EntidadBeneficiaria> listar() {
    return entidades;
  }

  //Traer una entidad beneficiaria por id
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response obtener(@PathParam("id") Long id) {
    EntidadBeneficiaria entidad = buscarPorId(id);

    if (entidad == null) {
      return errorNoEncontrada(id);
    }

    return Response.ok(entidad).build();
  }

  //Crear una nueva entidad beneficiaria
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response crear(EntidadBeneficiariaRequest request) {
    Long nuevoId = idCounter.getAndIncrement();

    List<Contacto> correos = mapearCorreos(request.correos);

    EntidadBeneficiaria nueva = new EntidadBeneficiaria(
        request.razonSocial,
        request.direccion,
        correos,
        new ArrayList<>()
    );
    nueva.setId(nuevoId);

    entidades.add(nueva);

    URI location = UriBuilder.fromResource(EntidadesApi.class)
        .path(EntidadesApi.class, "obtener")
        .build(nuevoId);

    return Response.created(location).entity(nueva).build();
  }

  //Eliminar una entidad beneficiaria
  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response eliminar(@PathParam("id") Long id) {
    EntidadBeneficiaria entidad = buscarPorId(id);
    if (entidad == null) {
      return errorNoEncontrada(id);
    }

    entidades.remove(entidad);

    return Response.ok().build();
  }

  // Funciones extra

  private EntidadBeneficiaria buscarPorId(Long id) {
    return entidades.stream()
        .filter(e -> e.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  private Response errorNoEncontrada(Long id) {
    Map<String, Object> error = new LinkedHashMap<>();
    error.put("error", "No existe una entidad beneficiaria con id " + id);
    return Response.status(Response.Status.NOT_FOUND).entity(error).build();
  }

  private List<Contacto> mapearCorreos(List<String> correos) {
    if (correos == null) {
      return new ArrayList<>();
    }
    return correos.stream()
        .<Contacto>map(Mail::new)
        .collect(Collectors.toList());
  }

  // Request

  public static class EntidadBeneficiariaRequest {
    public String razonSocial;
    public String direccion;
    public List<String> correos;
  }
}

/*
  Ejemplos para probar todas las rutas (Postman)

  o POST http://localhost:8080/entidades
    Content-Type: application/json

    {
      "razonSocial": "Comedor San José",
      "direccion": "Av. Siempre Viva 742",
      "telefono": 1145678900,
      "correos": ["contacto@comedorsanjose.org", "admin@comedorsanjose.org"]
    }

  o GET http://localhost:8080/entidades
  o GET http://localhost:8080/entidades/1
  o DELETE http://localhost:8080/entidades/1

 */