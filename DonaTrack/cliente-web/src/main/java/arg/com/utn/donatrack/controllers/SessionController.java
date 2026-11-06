package arg.com.utn.donatrack.controllers;

import arg.com.utn.donatrack.clientes.ClienteUsuarios;
import arg.com.utn.donatrack.grpc.usuarios.AutenticarResponse;
import io.grpc.StatusRuntimeException;
import io.javalin.http.Context;

import java.util.Map;

public class SessionController {

  private final ClienteUsuarios usuarios;

  public SessionController(ClienteUsuarios usuarios) {
    this.usuarios = usuarios;
  }

  public void mostrarLogin(Context ctx) {
    String rol = ctx.sessionAttribute("rol");
    if (rol != null) {
      ctx.redirect(landingPara(rol));
      return;
    }
    ctx.render("login.html.hbs", Map.of());
  }

  public void crearSesion(Context ctx) {
    String email = ctx.formParam("email") == null ? "" : ctx.formParam("email").trim();
    String contrasena = ctx.formParam("contrasena") == null ? "" : ctx.formParam("contrasena");

    try {
      AutenticarResponse respuesta = usuarios.autenticar(email, contrasena);

      if (!respuesta.getOk()) {
        ctx.status(401).render("login.html.hbs",
            Map.of("error", "Email o contraseña incorrectos.", "email", email));
        return;
      }

      ctx.sessionAttribute("user_id", respuesta.getUsuarioId());
      ctx.sessionAttribute("rol", respuesta.getRol());
      ctx.redirect(landingPara(respuesta.getRol()));
    } catch (StatusRuntimeException e) {
      ctx.status(503).render("login.html.hbs",
          Map.of("error", "No se pudo conectar con el servicio de donaciones.", "email", email));
    }
  }

  public void cerrarSesion(Context ctx) {
    ctx.req().getSession().invalidate();
    ctx.redirect("/login");
  }

  private String landingPara(String rol) {
    if ("ENTIDAD".equals(rol)) {
      return "/entidad";
    }
    if ("ADMINISTRADOR".equals(rol)) {
      return "/admin";
    }
    return "/donante";
  }
}