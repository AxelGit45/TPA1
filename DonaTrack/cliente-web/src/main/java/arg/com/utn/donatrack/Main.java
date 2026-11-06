package arg.com.utn.donatrack;

import arg.com.utn.donatrack.clientes.ClienteUsuarios;
import arg.com.utn.donatrack.controllers.HomeController;
import arg.com.utn.donatrack.controllers.LandingController;
import arg.com.utn.donatrack.controllers.RegistroController;
import arg.com.utn.donatrack.controllers.SessionController;
import arg.com.utn.donatrack.templates.JavalinHandlebars;
import arg.com.utn.donatrack.templates.JavalinRenderer;
import io.javalin.Javalin;

public class Main {

  public static void main(String[] args) {
    JavalinRenderer renderer = new JavalinRenderer()
        .register("hbs", new JavalinHandlebars());

    Javalin app = Javalin.create(config -> {
      config.fileRenderer(renderer);
      config.staticFiles.add("/public");
    }).start(9000);



    ClienteUsuarios clienteUsuarios = new ClienteUsuarios("localhost", 9091);
    SessionController session = new SessionController(clienteUsuarios);
    RegistroController registro = new RegistroController(clienteUsuarios);
    HomeController home = new HomeController();
    LandingController landing = new LandingController();

    app.get("/login", session::mostrarLogin);
    app.post("/login", session::crearSesion);

    app.get("/", home::index);
    app.get("/donante", landing::mostrar);
    app.get("/entidad", landing::mostrar);
    app.get("/admin", landing::mostrar);
    app.get("/registro/humana", registro::mostrarFormularioHumana);
    app.post("/registro", registro::crearPersonaHumana);


  }
}