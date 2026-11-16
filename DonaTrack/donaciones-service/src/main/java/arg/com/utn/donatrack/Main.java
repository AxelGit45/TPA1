package arg.com.utn.donatrack;

import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.web.controllers.*;
import arg.com.utn.donatrack.web.templates.JavalinHandlebars;
import arg.com.utn.donatrack.web.templates.JavalinRenderer;
import io.javalin.Javalin;
import io.javalin.http.ForbiddenResponse;
import java.util.Map;

public class Main {

  public static final int PUERTO_WEB = 9000;

  public static void main(String[] args) {
    Persistencia.factory();
    Javalin web = levantarWeb();

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      web.stop();
    }));
  }

  private static Javalin levantarWeb() {
    JavalinRenderer renderer = new JavalinRenderer()
        .register("hbs", new JavalinHandlebars());

    Javalin app = Javalin.create(config -> {
      config.fileRenderer(renderer);
      config.staticFiles.add("/public");
    }).start(PUERTO_WEB);

    app.exception(ForbiddenResponse.class, (e, ctx) -> {
      ctx.status(403)
          .contentType("text/plain; charset=utf-8")
          .result(e.getMessage());
    });

    HomeController home = new HomeController();
    DonanteController donante = new DonanteController();
    EntidadController entidad = new EntidadController();
    LandingController landing = new LandingController();
    AdminController admin = new AdminController();

    // Rutas públicas
    app.get("/", home::index);
    app.get("/mapa-entregas", home::mostrarMapaEntregas);
    app.get("/legal", home::mostrarLegal);

    // Login y Logout

    // Registros
    app.get("/registro/exito", ctx -> ctx.render("registro-exitoso.html.hbs", Map.of()));

    // Landings por rol
    app.get("/donante", landing::mostrar);
    app.get("/entidad", landing::mostrar);
    app.get("/admin", landing::mostrar);

    // Rutas Donante
    app.get("/donante/donaciones", donante::listarDonaciones);
    app.get("/donante/entidades", donante::listarEntidades);
    app.get("/donante/seguimiento", donante::seguirEntregas);

    // Rutas Entidad
    app.get("/entidad/necesidades/nueva", entidad::mostrarFormularioNecesidad);
    app.post("/entidad/necesidades", entidad::guardarNecesidad);
    app.get("/entidad/donaciones", entidad::listarDonacionesAsignadas);
    app.get("/entidad/confirmar/{id}", entidad::mostrarFormularioConfirmacion);
    app.post("/entidad/confirmar/{id}", entidad::confirmarRecepcion);
    app.get("/entidad/notificaciones", entidad::listarNotificaciones);
    app.get("/entidad/seguimiento", entidad::seguirEntregas);

    app.get("/admin/recepcion", admin::mostrarRecepcion);
    app.get("/admin/vencidas", admin::mostrarVencidas);
    app.get("/admin/matchmaking", admin::mostrarMatchmaking);
    app.get("/admin/camiones", admin::mostrarCamiones);
    app.get("/admin/importar-csv", admin::mostrarImportarCsv);

    System.out.println("[DONACIONES-SERVICE] Web (Javalin) levantada en http://localhost:" + PUERTO_WEB);
    return app;
  }
}
