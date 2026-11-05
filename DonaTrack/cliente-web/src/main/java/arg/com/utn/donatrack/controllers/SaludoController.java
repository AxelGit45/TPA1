package arg.com.utn.donatrack.controllers;


import io.javalin.http.Context;

import java.util.Map;

public class SaludoController {

  public void bienvenida(Context ctx) {
    ctx.render("bienvenida.html.hbs", Map.of("nombre", leerNombre(ctx)));
  }

  public void despedida(Context ctx) {
    ctx.render("despedida.html.hbs", Map.of("nombre", leerNombre(ctx)));
  }

  public String leerNombre(Context ctx) {
    String nombre = ctx.queryParam("nombre");
    return nombre != null ? nombre : "Mundo";
  }
}