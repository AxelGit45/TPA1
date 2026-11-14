package arg.com.utn.donatrack.web.controllers;

import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.repositorios.RepositorioUsuarios;
import arg.com.utn.donatrack.usuarios.Usuario;
import io.javalin.http.Context;
import javax.persistence.EntityManager;
import java.util.Map;

public class SessionController {

  private final RepositorioUsuarios repositorio = new RepositorioUsuarios();

  public void mostrarLogin(Context ctx) {
    ctx.render("login.html.hbs", Map.of());
  }

  public void crearSesion(Context ctx) {
    String email = ctx.formParam("email") == null ? "" : ctx.formParam("email").trim();
    String contrasena = ctx.formParam("contrasena") == null ? "" : ctx.formParam("contrasena");

    EntityManager em = Persistencia.crearEntityManager();
    try {
      Usuario usuario = repositorio.buscarPorEmail(em, email);

      if (usuario == null || !usuario.contrasenaCoincide(contrasena)) {
        ctx.status(401).render("login.html.hbs",
            Map.of("error", "Email o contraseña incorrectos.", "email", email));
        return;
      }

      ctx.sessionAttribute("user_id", usuario.getId());
      
      String destino = "/donante";
      if (!usuario.roles().isEmpty()) {
        String rol = usuario.roles().get(0).name();
        ctx.sessionAttribute("rol", rol);
        if ("ENTIDAD".equals(rol)) {
          destino = "/entidad";
        } else if ("ADMINISTRADOR".equals(rol)) {
          destino = "/admin";
        }
      }

      ctx.redirect(destino);
    } catch (Exception e) {
      e.printStackTrace();
      ctx.status(500).render("login.html.hbs",
          Map.of("error", "Error interno al verificar credenciales.", "email", email));
    } finally {
      em.close();
    }
  }

  public void cerrarSesion(Context ctx) {
    ctx.consumeSessionAttribute("user_id");
    ctx.consumeSessionAttribute("rol");
    ctx.redirect("/login");
  }
}
