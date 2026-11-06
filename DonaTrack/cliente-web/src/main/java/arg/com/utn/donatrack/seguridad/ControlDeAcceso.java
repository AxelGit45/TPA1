package arg.com.utn.donatrack.seguridad;

import io.javalin.http.ForbiddenResponse;
import io.javalin.http.Handler;

public class ControlDeAcceso {

  // Solo deja entrar quien tiene el rol permitido.
  public Handler requiere(String rolPermitido) {
    return ctx -> {
      String rol = ctx.sessionAttribute("rol");
      if (rol == null) {
        ctx.redirect("/login");
        return;
      }
      if (!rol.equals(rolPermitido)) {
        throw new ForbiddenResponse("No tenés permiso para ver esta página.");
      }
    };
  }
}