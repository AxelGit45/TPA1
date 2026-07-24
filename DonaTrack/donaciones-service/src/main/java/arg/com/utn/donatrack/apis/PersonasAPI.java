package arg.com.utn.donatrack.apis;

import arg.com.utn.donatrack.personas.PersonaHumana;
import arg.com.utn.donatrack.personas.PersonaJuridica;
import arg.com.utn.donatrack.personas.TipoRazonSocial;
import arg.com.utn.donatrack.personas.contactos.Contacto;
import arg.com.utn.donatrack.personas.contactos.Mail;
import arg.com.utn.donatrack.personas.contactos.Telefono;
import arg.com.utn.donatrack.personas.contactos.WhatsApp;
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

/* CRUD PARA PERSONAS
Al todavía no tener persistencia, se mockea guardando en memoria
* */
@Path("/personas")
public class PersonasAPI {

  private static final List<PersonaHumana> personasHumanas = new ArrayList<>();
  private static final List<PersonaJuridica> personasJuridicas = new ArrayList<>();

  private static final AtomicLong idHumanaCounter = new AtomicLong(1);
  private static final AtomicLong idJuridicaCounter = new AtomicLong(1);

  // PERSONA HUMANA

  @GET
  @Path("/humanas")
  @Produces(MediaType.APPLICATION_JSON)
  public List<PersonaHumana> listarHumanas() {
    return personasHumanas;
  }

  @GET
  @Path("/humanas/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response obtenerHumana(@PathParam("id") Long id) {
    PersonaHumana persona = buscarHumanaPorId(id);
    if (persona == null) {
      return errorNoEncontrada(id);
    }
    return Response.ok(persona).build();
  }

  @POST
  @Path("/humanas")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response crearHumana(PersonaHumanaRequest request) {
    Long nuevoId = idHumanaCounter.getAndIncrement();

    List<Contacto> contactos = mapearContactos(request.contactos);
    Contacto predeterminado = request.predeterminado != null ? mapearContacto(request.predeterminado) : null;

    PersonaHumana nueva = new PersonaHumana(
        request.nombre,
        request.apellido,
        request.edad,
        request.numeroDeDocumento,
        request.genero,
        request.direccion,
        contactos,
        predeterminado
    );
    nueva.setId(nuevoId);

    personasHumanas.add(nueva);

    URI location = UriBuilder.fromResource(PersonasAPI.class)
        .path(PersonasAPI.class, "obtenerHumana")
        .build(nuevoId);

    return Response.created(location).entity(nueva).build();
  }

  @DELETE
  @Path("/humanas/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response eliminarHumana(@PathParam("id") Long id) {
    PersonaHumana persona = buscarHumanaPorId(id);
    if (persona == null) {
      return errorNoEncontrada(id);
    }
    personasHumanas.remove(persona);
    return Response.ok().build();
  }

  // PERSONA JURIDICA

  @GET
  @Path("/juridicas")
  @Produces(MediaType.APPLICATION_JSON)
  public List<PersonaJuridica> listarJuridicas() {
    return personasJuridicas;
  }

  @GET
  @Path("/juridicas/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response obtenerJuridica(@PathParam("id") Long id) {
    PersonaJuridica persona = buscarJuridicaPorId(id);
    if (persona == null) {
      return errorNoEncontrada(id);
    }
    return Response.ok(persona).build();
  }

  @POST
  @Path("/juridicas")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response crearJuridica(PersonaJuridicaRequest request) {
    Long nuevoId = idJuridicaCounter.getAndIncrement();

    List<Contacto> contactos = mapearContactos(request.contactos);

    PersonaJuridica nueva = new PersonaJuridica(
        request.razonSocial,
        request.tipoRazonSocial,
        request.rubro,
        contactos
    );
    nueva.setId(nuevoId);

    personasJuridicas.add(nueva);

    URI location = UriBuilder.fromResource(PersonasAPI.class)
        .path(PersonasAPI.class, "obtenerJuridica")
        .build(nuevoId);

    return Response.created(location).entity(nueva).build();
  }

  @DELETE
  @Path("/juridicas/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response eliminarJuridica(@PathParam("id") Long id) {
    PersonaJuridica persona = buscarJuridicaPorId(id);
    if (persona == null) {
      return errorNoEncontrada(id);
    }
    personasJuridicas.remove(persona);
    return Response.ok().build();
  }

  // Funciones extra

  private PersonaHumana buscarHumanaPorId(Long id) {
    return personasHumanas.stream()
        .filter(p -> p.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  private PersonaJuridica buscarJuridicaPorId(Long id) {
    return personasJuridicas.stream()
        .filter(p -> p.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  private Response errorNoEncontrada(Long id) {
    Map<String, Object> error = new LinkedHashMap<>();
    error.put("error", "No existe una persona con id " + id);
    return Response.status(Response.Status.NOT_FOUND).entity(error).build();
  }


  private List<Contacto> mapearContactos(List<ContactoRequest> requests) {
    if (requests == null) {
      return new ArrayList<>();
    }
    return requests.stream()
        .map(this::mapearContacto)
        .collect(Collectors.toList());
  }

  private Contacto mapearContacto(ContactoRequest cr) {
    return switch (cr.tipo) {
      case MAIL -> new Mail(cr.valor);
      case TELEFONO -> new Telefono(cr.valor);
      case WHATSAPP -> new WhatsApp(cr.valor);
    };
  }

  // Request

  public enum TipoContacto {
    MAIL, TELEFONO, WHATSAPP
  }

  public static class ContactoRequest {
    public TipoContacto tipo;
    public String valor;
  }

  public static class PersonaHumanaRequest {
    public String nombre;
    public String apellido;
    public Integer edad;
    public Integer numeroDeDocumento;
    public String genero;
    public String direccion;
    public List<ContactoRequest> contactos;
    public ContactoRequest predeterminado;
  }

  public static class PersonaJuridicaRequest {
    public String razonSocial;
    public TipoRazonSocial tipoRazonSocial;
    public String rubro;
    public List<ContactoRequest> contactos;
  }
}

/*
  Ejemplos para probar todas las rutas (Postman)

  PERSONA HUMANA

  o POST http://localhost:8080/personas/humanas
    {
      "nombre": "Juan",
      "apellido": "Perez",
      "edad": 30,
      "numeroDeDocumento": 12345678,
      "genero": "M",
      "direccion": "Calle Falsa 123",
      "contactos": [
        { "tipo": "MAIL", "valor": "juan@mail.com" },
        { "tipo": "TELEFONO", "valor": "1122334455" }
      ],
      "predeterminado": { "tipo": "MAIL", "valor": "juan@mail.com" }
    }

  o GET http://localhost:8080/personas/humanas
  o GET http://localhost:8080/personas/humanas/1
  o DELETE http://localhost:8080/personas/humanas/1

  PERSONA JURIDICA

  o POST http://localhost:8080/personas/juridicas
    {
      "razonSocial": "Fundación Ejemplo",
      "tipoRazonSocial": "ONG",
      "rubro": "Asistencia social",
      "contactos": [
        { "tipo": "WHATSAPP", "valor": "1155667788" }
      ]
    }

  o GET http://localhost:8080/personas/juridicas
  o GET http://localhost:8080/personas/juridicas/1
  o DELETE http://localhost:8080/personas/juridicas/1
*/