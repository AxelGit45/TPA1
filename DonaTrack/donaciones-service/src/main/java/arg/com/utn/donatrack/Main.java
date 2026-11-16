package arg.com.utn.donatrack;

import arg.com.utn.donatrack.clients.UsuariosClient;
import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.web.controllers.*;
import arg.com.utn.donatrack.web.templates.JavalinHandlebars;
import arg.com.utn.donatrack.web.templates.JavalinRenderer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.http.ForbiddenResponse;
import jakarta.ws.rs.ext.ContextResolver;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.Map;

public class Main {

  public static final String BASE_URI = "http://0.0.0.0:8080/";
  public static final String URL_BACK = "http://localhost:8080";
  public static final int PUERTO_WEB = 9000;

  public static void main(String[] args) {
    Persistencia.factory();

    HttpServer back = levantarBack();
    Javalin web = levantarWeb();

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      web.stop();
      back.shutdownNow();
    }));
  }

  private static HttpServer levantarBack() {
    final ResourceConfig config = new ResourceConfig();

    config.packages("arg.com.utn.donatrack.apis");
    config.register(JacksonFeature.class);

    config.register((ContextResolver<ObjectMapper>) type -> {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
      return mapper;
    });

    HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);

    System.out.println("[DONACIONES-SERVICE] Back (Jersey) levantado en " + BASE_URI);
    System.out.println("Proba: http://localhost:8080/health");
    return server;
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

    UsuariosClient usuariosClient = new UsuariosClient(URL_BACK);
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
