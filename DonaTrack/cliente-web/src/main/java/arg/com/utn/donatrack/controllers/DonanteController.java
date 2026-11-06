package arg.com.utn.donatrack.controllers;

import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonanteController {

  public void listarDonaciones(Context ctx){

    List<Map<String, Object>> donaciones = new ArrayList<>();

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("donaciones", donaciones);

    ctx.render("donante-donaciones.html.hbs", modelo);
  }

  public void listarEntidades(Context ctx){

    List<Map<String, Object>> entidades = new ArrayList<>();

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("entidades", entidades);

    ctx.render("donante-entidades.html.hbs", modelo);
  }
  public void seguirEntregas(Context ctx){

    List<Map<String, Object>> entregasActivas = new ArrayList<>();

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("entregas", entregasActivas);

    ctx.render("donante-seguimiento.html.hbs", modelo);
  }

}
