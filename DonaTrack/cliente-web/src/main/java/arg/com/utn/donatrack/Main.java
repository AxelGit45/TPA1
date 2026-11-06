package arg.com.utn.donatrack;

import arg.com.utn.donatrack.clientes.ClienteUsuarios;
import arg.com.utn.donatrack.controllers.*;
import arg.com.utn.donatrack.seguridad.ControlDeAcceso;
import arg.com.utn.donatrack.templates.JavalinHandlebars;
import arg.com.utn.donatrack.templates.JavalinRenderer;
import io.javalin.Javalin;
import io.javalin.http.ForbiddenResponse;

public class Main {

    public static void main(String[] args) {
        JavalinRenderer renderer = new JavalinRenderer()
            .register("hbs", new JavalinHandlebars());

        Javalin app = Javalin.create(config -> {
            config.fileRenderer(renderer);
            config.staticFiles.add("/public");
        }).start(9000);

        app.exception(ForbiddenResponse.class, (e, ctx) -> {
            ctx.status(403)
                .contentType("text/plain; charset=utf-8")
                .result(e.getMessage());
        });

        ClienteUsuarios clienteUsuarios = new ClienteUsuarios("localhost", 9091);

        SessionController session = new SessionController(clienteUsuarios);
        RegistroController registro = new RegistroController(clienteUsuarios);
        RegistroJuridicaController registroJuridica = new RegistroJuridicaController(clienteUsuarios);
        RegistroEntidadController registroEntidad = new RegistroEntidadController(clienteUsuarios);
        ControlDeAcceso acceso = new ControlDeAcceso();
        HomeController home = new HomeController();
        LandingController landing = new LandingController();

        DonanteController donante = new DonanteController();
        EntidadController entidad = new EntidadController();

        app.get("/", home::index);
        app.get("/login", session::mostrarLogin);
        app.post("/login", session::crearSesion);

        app.get("/registro/humana", registro::mostrarFormularioHumana);
        app.post("/registro", registro::crearPersonaHumana);
        app.get("/registro/juridica", registroJuridica::mostrarFormulario);
        app.post("/registro/juridica", registroJuridica::crear);
        app.get("/registro/entidad", registroEntidad::mostrarFormulario);
        app.post("/registro/entidad", registroEntidad::crear);
        app.before("/donante", acceso.requiere("DONANTE"));
        app.before("/donante/*", acceso.requiere("DONANTE"));
        app.before("/entidad", acceso.requiere("ENTIDAD"));
        app.before("/entidad/*", acceso.requiere("ENTIDAD"));
        app.before("/admin", acceso.requiere("ADMINISTRADOR"));
        app.before("/admin/*", acceso.requiere("ADMINISTRADOR"));

        app.post("/logout", session::cerrarSesion);


        app.get("/donante", landing::mostrar);
        app.get("/entidad", landing::mostrar);
        app.get("/admin", landing::mostrar);

        app.get("/donante/donaciones", donante::listarDonaciones);
        app.get("/donante/entidades", donante::listarEntidades);
        app.get("/donante/seguimiento", donante::seguirEntregas);

        app.get("/entidad/necesidades/nueva", entidad::mostrarFormularioNecesidad);
        app.post("/entidad/necesidades", entidad::guardarNecesidad);
        app.get("/entidad/donaciones", entidad::listarDonacionesAsignadas);
        app.get("/entidad/confirmar/{id}", entidad::mostrarFormularioConfirmacion);
        app.post("/entidad/confirmar/{id}", entidad::confirmarRecepcion);
        app.get("/entidad/notificaciones", entidad::listarNotificaciones);
        app.get("/entidad/seguimiento", entidad::seguirEntregas);
    }
}