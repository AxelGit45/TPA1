package arg.com.utn.donatrack.controllers;

import io.javalin.http.Context;

import java.util.Map;

public class LandingController {

  public void mostrar(Context ctx) {
    Long id = ctx.sessionAttribute("user_id");
    String rol = ctx.sessionAttribute("rol");

    if (id == null) {
      ctx.redirect("/login");
      return;
    }

    ctx.render("landingusuario.html.hbs", Map.of("id", id, "rol", rol));
  }
}