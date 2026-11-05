package arg.com.utn.donatrack;

import arg.com.utn.donatrack.controllers.HomeController;
import arg.com.utn.donatrack.controllers.SaludoController;
import arg.com.utn.donatrack.templates.JavalinHandlebars;
import arg.com.utn.donatrack.templates.JavalinRenderer;
import io.javalin.Javalin;

public class Main {

  public static void main(String[] args) {
    JavalinRenderer renderer = new JavalinRenderer()
        .register("hbs", new JavalinHandlebars());

    Javalin app = Javalin.create(config -> {
      config.fileRenderer(renderer);
    }).start(9000);

    HomeController home = new HomeController();
    SaludoController saludo = new SaludoController();

    app.get("/", home::index);
    app.get("/bienvenida", saludo::bienvenida);
    app.get("/despedida", saludo::despedida);
  }
}