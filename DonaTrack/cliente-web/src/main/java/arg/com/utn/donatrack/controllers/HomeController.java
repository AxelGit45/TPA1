package arg.com.utn.donatrack.controllers;

import io.javalin.http.Context;
import java.util.Map;

public class HomeController {

  public void index(Context ctx) {
    ctx.render("landing.html.hbs", Map.of());
  }
}