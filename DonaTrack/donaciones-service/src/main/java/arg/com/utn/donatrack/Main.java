package arg.com.utn.donatrack;

import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.repositorios.RepositorioUsuarios;
import arg.com.utn.donatrack.seguridad.SeguridadPassword;
import arg.com.utn.donatrack.usuarios.Rol;
import arg.com.utn.donatrack.usuarios.Usuario;
import arg.com.utn.donatrack.web.controllers.*;
import arg.com.utn.donatrack.web.templates.JavalinHandlebars;
import arg.com.utn.donatrack.web.templates.JavalinRenderer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.javalin.Javalin;
import io.javalin.http.ForbiddenResponse;
import javax.persistence.EntityManager;
import java.util.Map;
import io.javalin.http.staticfiles.Location;

public class Main {

  public static final int PUERTO_WEB = Integer.parseInt(System.getenv().getOrDefault("PORT", "9000"));

  public static void main(String[] args) {
    Persistencia.factory();
    inicializarAdmin();
    ManagedChannel channel = ManagedChannelBuilder
        .forAddress("localhost", 50051)
        .usePlaintext()
        .build();
    Javalin web = levantarWeb();



    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      web.stop();
    }));
  }


  // Podés llamar a este método en Main.java antes de levantar Javalin
  private static void inicializarAdmin() {
    EntityManager em = Persistencia.crearEntityManager();
    RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
    String emailAdmin = "admin@dona.com";
    // Verifica si ya existe para no duplicarlo cada vez que reiniciás el servidor
    if (repoUsuarios.buscarUsuarioPorEmail(em, emailAdmin) == null) {
      String passwordHasheada = SeguridadPassword.hashear("admin123");
      Usuario admin = new Usuario(emailAdmin, passwordHasheada, Rol.ADMINISTRADOR);
      repoUsuarios.guardarUsuario(em, admin);
      System.out.println("Usuario administrador creado: " + emailAdmin);
    }
  }

  private static Javalin levantarWeb() {
    JavalinRenderer renderer = new JavalinRenderer()
        .register("hbs", new JavalinHandlebars());

    new java.io.File("uploads").mkdirs();
    Javalin app = Javalin.create(config -> {
      config.fileRenderer(renderer);
      config.staticFiles.add("/public");
      config.staticFiles.add(staticFiles -> {
        staticFiles.hostedPath = "/uploads";
        staticFiles.directory = "uploads";
        staticFiles.location = Location.EXTERNAL;
      });
    }).start(PUERTO_WEB);
    app.exception(ForbiddenResponse.class, (e, ctx) -> {
      ctx.status(403)
          .contentType("text/plain; charset=utf-8")
          .result(e.getMessage());
    });
    app.error(404, ctx -> {
      ctx.render("404.html.hbs", Map.of());
    });


// Manejo de sesiones

app.before(ctx -> {
  String path = ctx.path();
  String rolRequerido = null;

  if(path.startsWith("/admin")) {
    rolRequerido = "ADMINISTRADOR";
  } else if(path.startsWith("/donante")) {
    rolRequerido = "DONANTE";
  } else if(path.startsWith("/entidad")) {
    rolRequerido = "ENTIDAD_BENEFICIARIA";
  }

  if (rolRequerido == null){
    return;
  }

  String rol = ctx.sessionAttribute("rol");
  if (rol == null || !rol.equals(rolRequerido)) {
    ctx.status(404).render("404.html.hbs", Map.of());
    ctx.skipRemainingHandlers();
  }
});


    HomeController home = new HomeController();
    DonanteController donante = new DonanteController();
    EntidadController entidad = new EntidadController();
    LandingController landing = new LandingController();
    AdminController admin = new AdminController();
    RegistroController registro = new RegistroController();
    SessionController sesion = new SessionController();
    ComponenteAsignadorController externo = new ComponenteAsignadorController();

    //Registros
    app.get("/registro/juridica", registro::registroJuridica);
    app.post("/registro/juridica", registro::crearPersonaJuridica);
    app.get("/registro/entidad", registro::registroEntidad);
    app.post("/registro/entidad", registro::crearEntidadBeneficiaria);
    app.get("/registro/humana", registro::registroPersonaHumana);
    app.post("/registro/humana", registro::crearPersonaHumana);
    app.get("/registro/exito", ctx -> ctx.render("exito.html.hbs", Map.of()));
    app.get("/registro", ctx -> ctx.render("registro-opciones.html.hbs", Map.of()));
    //Inicio y cierre de sesion
    app.get("/login", sesion::mostrarLogin);
    app.post("/login", sesion::iniciarSesion);
    app.post("/logout", sesion::cerrarSesion);

    // Rutas públicas
    app.get("/", home::index);
    app.get("/mapa-entregas", home::mostrarMapaEntregas);
    app.get("/legal", home::mostrarLegal);
    app.get("/dev", ctx -> ctx.render("dev.html.hbs", Map.of()));
    app.get("/panel", ctx -> {
      String rol = ctx.sessionAttribute("rol");
      if ("ADMINISTRADOR".equals(rol)) {
        ctx.redirect("/admin");
      } else if ("ENTIDAD_BENEFICIARIA".equals(rol)) {
        ctx.redirect("/entidad");
      } else if ("DONANTE".equals(rol)) {
        ctx.redirect("/donante");
      } else {
        ctx.redirect("/login");
      }
    });

    // Landings por rol
    app.get("/donante", landing::mostrar);
    app.get("/entidad", landing::mostrar);
    app.get("/admin", admin::mostrarHome);

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

    // Rutas Admin
    app.get("/admin/donantes/nuevo", admin::mostrarAltaDonante);
    app.post("/admin/donantes", admin::crearDonante);
    app.get("/admin/recepcion", admin::mostrarRecepcion);
    app.get("/admin/vencidas", admin::mostrarVencidas);
    app.post("/admin/vencidas/{id}", admin::marcarVencida);
    app.get("/admin/matchmaking", admin::mostrarMatchmaking);
    app.get("/admin/camiones", admin::mostrarCamiones);
    app.get("/admin/importar-csv", admin::mostrarImportarCsv);
    app.post("/admin/importar-csv", admin::importarCsv);


    /*Rutas para cargar una donacion*/
    // Recepción de donaciones en almacén (Paso 1: crear donación / Paso 2: cargar bienes)
    //app.get("/admin/recepcion", admin::mostrarRecepcion);
    /*app.post("/admin/recepcion", admin::crearDonacion);
    app.get("/admin/recepcion/{id}/bienes", admin::mostrarCargaBienes);
    app.post("/admin/recepcion/{id}/bienes", admin::agregarBien);
    app.post("/admin/recepcion/finalizar", admin::); */

    app.post("/admin/recepcion", admin::crearDonacion);
    app.get("/admin/recepcion/bienes", admin::mostrarCargaBienes);
    app.post("/admin/recepcion/bienes", admin::agregarBien);
    app.post("/admin/recepcion/finalizar", admin::finalizarDonacion);

    app.post("/admin/matchmaking", externo::guardarResultadosDeComponenteExterno);
    app.post("/admin/matchmaking/{id}/confirmar", admin::confirmarAsignacion);

    System.out.println("[DONACIONES-SERVICE] Web (Javalin) levantada en http://localhost:" + PUERTO_WEB);
    return app;
  }
}
