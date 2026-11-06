package arg.com.utn.donatrack.controllers;

import arg.com.utn.donatrack.AlmacenMemoria;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeController {

  public void index(Context ctx) {
    ctx.render("landing.html.hbs", Map.of());
  }

  public void mostrarMapaEntregas(Context ctx) {

    //List<Map<String, Object>> entregadas = new ArrayList<>();

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("entregadas", AlmacenMemoria.obtenerDonacionesEntregadas());
    //modelo.put("entregadas", entregadas);

    ctx.render("mapa-entregas.html.hbs", modelo);
  }

  public void mostrarLegal(Context ctx) {

    ctx.render("legal.html.hbs", Map.of());
  }

}