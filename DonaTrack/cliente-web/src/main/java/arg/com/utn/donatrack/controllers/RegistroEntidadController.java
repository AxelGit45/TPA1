package arg.com.utn.donatrack.controllers;

import arg.com.utn.donatrack.clientes.ClienteUsuarios;
import arg.com.utn.donatrack.grpc.usuarios.RegistrarEntidadResponse;
import io.grpc.StatusRuntimeException;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroEntidadController {

  private static final String FORMATO_EMAIL = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";

  private final ClienteUsuarios usuarios;

  public RegistroEntidadController(ClienteUsuarios usuarios) {
    this.usuarios = usuarios;
  }

  public void mostrarFormulario(Context ctx) {
    ctx.render("registro-entidad.html.hbs", modelo(ctx, List.of()));
  }

  public void crear(Context ctx) {
    List<String> errores = validar(ctx);
    if (!errores.isEmpty()) {
      ctx.status(400).render("registro-entidad.html.hbs", modelo(ctx, errores));
      return;
    }

    try {
      RegistrarEntidadResponse respuesta = usuarios.registrarEntidadBeneficiaria(
          texto(ctx, "razonSocial"),
          texto(ctx, "direccion"),
          texto(ctx, "email"),
          campo(ctx, "contrasena"));

      if (respuesta.getOk()) {
        ctx.redirect("/login");
      } else {
        ctx.status(400).render("registro-entidad.html.hbs", modelo(ctx, List.of(respuesta.getError())));
      }
    } catch (StatusRuntimeException e) {
      ctx.status(503).render("registro-entidad.html.hbs",
          modelo(ctx, List.of("No se pudo conectar con el servicio de donaciones")));
    }
  }

  private List<String> validar(Context ctx) {
    List<String> errores = new ArrayList<>();

    if (texto(ctx, "razonSocial").isBlank()) {
      errores.add("La razón social es obligatoria.");
    }
    if (texto(ctx, "direccion").isBlank()) {
      errores.add("La dirección es obligatoria.");
    }
    if (!texto(ctx, "email").matches(FORMATO_EMAIL)) {
      errores.add("El correo electrónico no tiene un formato válido.");
    }
    if (campo(ctx, "contrasena").length() < 8) {
      errores.add("La contraseña debe tener al menos 8 caracteres.");
    }
    if (!campo(ctx, "contrasena").equals(campo(ctx, "confirmacion"))) {
      errores.add("Las contraseñas no coinciden.");
    }

    return errores;
  }

  // Lo que se le vuelve a mostrar a la persona: errores y lo que ya había escrito (sin contraseñas)
  private Map<String, Object> modelo(Context ctx, List<String> errores) {
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("errores", errores);
    modelo.put("razonSocial", texto(ctx, "razonSocial"));
    modelo.put("direccion", texto(ctx, "direccion"));
    modelo.put("email", texto(ctx, "email"));
    return modelo;
  }

  // Valor tal cual llegó (para contraseñas, que no se recortan)
  private String campo(Context ctx, String nombre) {
    String valor = ctx.formParam(nombre);
    return valor == null ? "" : valor;
  }

  // Valor sin espacios al principio y al final
  private String texto(Context ctx, String nombre) {
    return campo(ctx, nombre).trim();
  }
}