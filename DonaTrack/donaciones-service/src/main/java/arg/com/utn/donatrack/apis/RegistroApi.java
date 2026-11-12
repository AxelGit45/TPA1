package arg.com.utn.donatrack.apis;

import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.usuarios.ServicioDeUsuarios;
import arg.com.utn.donatrack.usuarios.Usuario;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.persistence.EntityManager;
import java.util.Map;

@Path("/registro")
public class RegistroApi {

  private final ServicioDeUsuarios servicio = new ServicioDeUsuarios();

  public static class HumanaRequest {
    public String nombre;
    public String apellido;
    public Integer edad;
    public Integer numeroDeDocumento;
    public String genero;
    public String direccion;
    public String email;
    public String telefono;
    public String whatsapp;
    public String medioPredeterminado;
    public String contrasena;
  }

  // 201 {usuarioId}; 400 si falta o es inválido algún dato; 409 si el email ya tiene cuenta
  @POST
  @Path("/humanas")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response registrarHumana(HumanaRequest r) {
    if (r == null) {
      return error(Response.Status.BAD_REQUEST, "Faltan datos");
    }

    EntityManager em = Persistencia.crearEntityManager();
    try {
      em.getTransaction().begin();
      Usuario usuario = servicio.registrarPersonaHumana(em, r.nombre, r.apellido, r.edad,
          r.numeroDeDocumento, r.genero, r.direccion, r.email, r.telefono, r.whatsapp,
          r.medioPredeterminado, r.contrasena);
      em.getTransaction().commit();

      return Response.status(Response.Status.CREATED)
          .entity(Map.of("usuarioId", usuario.getId()))
          .build();
    } catch (IllegalStateException e) {
      deshacer(em);
      return error(Response.Status.CONFLICT, e.getMessage());
    } catch (IllegalArgumentException e) {
      deshacer(em);
      return error(Response.Status.BAD_REQUEST, e.getMessage());
    } catch (RuntimeException e) {
      e.printStackTrace();
      deshacer(em);
      return error(Response.Status.INTERNAL_SERVER_ERROR, "No se pudo completar el registro");
    } finally {
      em.close();
    }
  }

  private void deshacer(EntityManager em) {
    if (em.getTransaction().isActive()) {
      em.getTransaction().rollback();
    }
  }

  private Response error(Response.Status estado, String mensaje) {
    return Response.status(estado).entity(Map.of("error", mensaje)).build();
  }
}