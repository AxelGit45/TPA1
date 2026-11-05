package arg.com.utn.donatrack.controllers;

import io.javalin.http.Context;

public class HomeController {

  public void index(Context ctx) {
    ctx.redirect("/bienvenida");
  }
}