package arg.com.utn.donatrack.controllers;

import io.javalin.http.Context;
import org.hibernate.type.ObjectType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

  public void listarDonacionesAsignadas(Context ctx) {

    List<Map<String, Object>> donacionesAsignadas = new ArrayList<>();

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("donaciones", donacionesAsignadas);

    ctx.render("beneficiaria-donaciones.html.hbs", modelo);
  }

  public void mostrarFormularioConfirmacion(Context ctx) {

    String donacionId = ctx.pathParam("id");

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("donacionId", donacionId);

    ctx.render("beneficiaria-confirmar.html.hbs", modelo);
  }

  public void confirmarRecepcion(Context ctx) {

    String donacionId = ctx.formParam("id");
    String fecha = ctx.formParam("fechaRecepcion");
    String estadoBienes = ctx.formParam("estadoBienes");
    String observaciones = ctx.formParam("observaciones");

    int cantidadFotos = ctx.uploadedFiles("fotos").size();
    System.out.println("Confirmada donación #" + donacionId + " con " + cantidadFotos + " fotos subidas.");
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("donacionId", donacionId);
    modelo.put("exito", true);
    ctx.render("beneficiaria-confirmar.html.hbs", modelo);
  }

  public void listarNotificaciones(Context ctx) {

    List<Map<String, Object>> notificaciones = new ArrayList<>();

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("notificaciones", notificaciones);

    ctx.render("beneficiaria-notificaciones.html.hbs");
  }

  public void seguirEntregas(Context ctx) {

    List<Map<String, Object>> entregasActivas = new ArrayList<>();

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("entregas", entregasActivas);

    ctx.render("beneficiaria-seguimiento.html.hbs", modelo);
  }
}
