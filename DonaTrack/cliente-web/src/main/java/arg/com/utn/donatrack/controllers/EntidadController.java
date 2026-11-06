package arg.com.utn.donatrack.controllers;

import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;

public class EntidadController {

  public void mostrarFormularioNecesidad(Context ctx) {

    Map<String, Object> modelo = new HashMap<>();
    ctx.render("beneficiaria-necesidad.html.hbs", modelo);

  }

  public void guardarNecesidad(Context ctx) {

    String tipo = ctx.formParam("tipo");
    String subcategoria = ctx.formParam("subcategoria");
    String cantidad = ctx.formParam("cantidad");
    String fechaLimite = ctx.formParam("fechaLimite");
    String descripcion = ctx.formParam("descripcion");

    System.out.println("Necesidad recibida: " + tipo + " - " + subcategoria + "(Cant: " + cantidad + ")");

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("exito", true);

    ctx.render("beneficiaria-necesidad.html.hbs", modelo);

  }
}
