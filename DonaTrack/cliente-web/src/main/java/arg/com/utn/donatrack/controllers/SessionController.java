package arg.com.utn.donatrack.controllers;

import arg.com.utn.donatrack.usuarios.RepositorioUsuarios;
import arg.com.utn.donatrack.usuarios.Usuario;
import io.javalin.http.Context;

import java.util.Map;
import java.util.Optional;

public class SessionController {

  public void mostrarLogin(Context ctx) {
    ctx.render("login.html.hbs", Map.of());
  }

  public void crearSesion(Context ctx) {
    String email = ctx.formParam("email");
    String contrasena = ctx.formParam("contrasena");

    Optional<Usuario> usuario = RepositorioUsuarios.instance()
        .buscarPorEmail(email)
        .filter(u -> u.contrasenaCoincide(contrasena));

    if (usuario.isEmpty()) {
      ctx.redirect("/login");
      return;
    }

    Usuario u = usuario.get();
    ctx.sessionAttribute("user_id", u.getId());
    ctx.sessionAttribute("rol", u.getRol().name());
    ctx.redirect(u.getRol().getLanding());
  }

}
