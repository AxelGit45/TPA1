package arg.com.utn.donatrack;

import arg.com.utn.donatrack.clients.UsuariosClient;
import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.web.controllers.RegistroController;
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
    // Conecta con Postgres al arrancar (si la base no está corriendo, falla acá y no a mitad de un registro)
    Persistencia.factory();

    HttpServer back = levantarBack();
    Javalin web = levantarWeb();

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      web.stop();
      back.shutdownNow();
    }));
  }

  // Back: API REST con Jersey (acá viven los endpoints que le pegan a la base)
  private static HttpServer levantarBack() {
    final ResourceConfig config = new ResourceConfig();

    // Jersey va a escanear este paquete buscando clases con @Path
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

  // Web: Javalin, le pega al back por HTTP
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
    RegistroController registro = new RegistroController(usuariosClient);

    app.get("/", ctx -> ctx.render("landing.html.hbs", Map.of()));

    app.get("/registro/humana", registro::mostrarFormularioHumana);
    app.post("/registro/humana", registro::crearPersonaHumana);
    app.get("/registro/exito", ctx -> ctx.render("registro-exitoso.html.hbs", Map.of()));

    System.out.println("[DONACIONES-SERVICE] Web (Javalin) levantada en http://localhost:" + PUERTO_WEB);
    return app;
  }
}