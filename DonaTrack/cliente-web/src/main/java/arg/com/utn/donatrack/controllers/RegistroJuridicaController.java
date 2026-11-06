package arg.com.utn.donatrack.controllers;

import arg.com.utn.donatrack.clientes.ClienteUsuarios;
import arg.com.utn.donatrack.grpc.usuarios.RegistrarDonanteResponse;
import io.grpc.StatusRuntimeException;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroJuridicaController {

  private static final List<String> TIPOS = List.of("GUBERNAMENTAL", "ONG", "EMPRESA", "INSTITUCION");
  private static final String FORMATO_EMAIL = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";

  private final ClienteUsuarios usuarios;

  public RegistroJuridicaController(ClienteUsuarios usuarios) {
    this.usuarios = usuarios;
  }

  public void mostrarFormulario(Context ctx) {
    ctx.render("registro-juridica.html.hbs", modelo(ctx, List.of()));
  }

  public void crear(Context ctx) {
    List<String> errores = validar(ctx);
    if (!errores.isEmpty()) {
      ctx.status(400).render("registro-juridica.html.hbs", modelo(ctx, errores));
      return;
    }

    try {
      RegistrarDonanteResponse respuesta = usuarios.registrarDonanteJuridica(
          texto(ctx, "razonSocial"),
          texto(ctx, "tipoRazonSocial"),
          texto(ctx, "rubro"),
          texto(ctx, "email"),
          campo(ctx, "contrasena"));

      if (respuesta.getOk()) {
        ctx.redirect("/login");
      } else {
        ctx.status(400).render("registro-juridica.html.hbs", modelo(ctx, List.of(respuesta.getError())));
      }
    } catch (StatusRuntimeException e) {
      ctx.status(503).render("registro-juridica.html.hbs",
          modelo(ctx, List.of("No se pudo conectar con el servicio de donaciones")));
    }
  }

  private List<String> validar(Context ctx) {
    List<String> errores = new ArrayList<>();

    if (texto(ctx, "razonSocial").isBlank()) {
      errores.add("La razón social es obligatoria.");
    }
    if (!TIPOS.contains(texto(ctx, "tipoRazonSocial"))) {
      errores.add("Elegí un tipo de organización de la lista.");
    }
    if (texto(ctx, "rubro").isBlank()) {
      errores.add("El rubro es obligatorio.");
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
    String elegido = texto(ctx, "tipoRazonSocial");
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("errores", errores);
    modelo.put("razonSocial", texto(ctx, "razonSocial"));
    modelo.put("rubro", texto(ctx, "rubro"));
    modelo.put("email", texto(ctx, "email"));
    modelo.put("tipos", List.of(
        opcion("GUBERNAMENTAL", "Gubernamental", elegido),
        opcion("ONG", "ONG", elegido),
        opcion("EMPRESA", "Empresa", elegido),
        opcion("INSTITUCION", "Institución", elegido)));
    return modelo;
  }

  private Map<String, Object> opcion(String codigo, String etiqueta, String elegido) {
    return Map.of("codigo", codigo, "etiqueta", etiqueta, "seleccionado", codigo.equals(elegido));
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