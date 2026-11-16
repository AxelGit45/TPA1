package arg.com.utn.donatrack.web.controllers;

import io.javalin.http.Context;
import java.util.Map;

public class AdminController {

  public void mostrarRecepcion(Context ctx) {
    ctx.render("admin-recepcion.html.hbs", Map.of());
  }

  public void mostrarVencidas(Context ctx) {
    ctx.render("admin-vencidas.html.hbs", Map.of());
  }

  public void mostrarMatchmaking(Context ctx) {
    ctx.render("admin-matchmaking.html.hbs", Map.of());
  }

  public void mostrarCamiones(Context ctx) {
    ctx.render("admin-camiones.html.hbs", Map.of());
  }

  public void mostrarImportarCsv(Context ctx) {
    ctx.render("admin-importar-csv.html.hbs", Map.of());
  }
}