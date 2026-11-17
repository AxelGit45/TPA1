package arg.com.utn.donatrack.web.controllers;

import arg.com.utn.donatrack.repositorios.RepositorioUsuarios;
import arg.com.utn.donatrack.usuarios.Usuario;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class SessionController {

  public void mostrarLogin(Context ctx) {ctx.render("login.html.hbs", Map.of());
  }

  public void iniciarSesion(Context ctx) {
    String email = texto(ctx, "email");
    String contrasenia = texto(ctx, "contrasenia");

    Usuario usuario = new RepositorioUsuarios().buscarUsuarioPorEmail(email);

    if (usuario == null || !usuario.getContrasenia().equals(contrasenia)) {
      Map<String, Object> formulario = new HashMap<>();
      formulario.put("error", "Email o contraseña incorrectos.");
      formulario.put("email", email);
      ctx.status(401).render("login.html.hbs", formulario);
      return;
    }


    ctx.sessionAttribute("usuarioId", usuario.getId());
    ctx.sessionAttribute("rol", usuario.getRol().name());

    switch (usuario.getRol()) {
      case DONANTE -> ctx.redirect("/donante/donaciones");
      case ENTIDAD_BENEFICIARIA -> ctx.redirect("/entidad/donaciones");
      case ADMINISTRADOR -> ctx.redirect("/admin");
    }
  }

  public void cerrarSesion(Context ctx) {
    ctx.req().getSession().invalidate();
    ctx.redirect("/");
  }

  private String texto(Context ctx, String nombreCampo) {
    String valor = ctx.formParam(nombreCampo);
    if (valor == null) { return ""; } else {
      return valor = valor.trim();
    }
  }


}