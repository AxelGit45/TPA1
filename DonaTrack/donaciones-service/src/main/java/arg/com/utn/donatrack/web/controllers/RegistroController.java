package arg.com.utn.donatrack.web.controllers;

import io.javalin.http.Context;
import java.util.Map;

public class RegistroController {

 /* public void registrarUsuario(Usuario usuario) {
    // Lógica para registrar un nuevo usuario
  }*/

  public void index(Context ctx) {
    ctx.render("registro-juridica.html.hbs", Map.of());
  }

}