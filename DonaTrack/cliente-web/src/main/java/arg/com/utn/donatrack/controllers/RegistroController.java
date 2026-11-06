package arg.com.utn.donatrack.controllers;

import arg.com.utn.donatrack.clientes.ClienteUsuarios;
import arg.com.utn.donatrack.grpc.usuarios.RegistrarDonanteResponse;
import io.grpc.StatusRuntimeException;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroController {

  private static final List<String> GENEROS =
      List.of("Femenino", "Masculino", "No binario", "Otro", "Prefiero no decir");
  private static final String FORMATO_EMAIL = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";

  private final ClienteUsuarios usuarios;

  public RegistroController(ClienteUsuarios usuarios) {
    this.usuarios = usuarios;
  }

  public void mostrarFormularioHumana(Context ctx) {
    ctx.render("registro-humana.html.hbs", modelo(ctx, List.of()));
  }

  public void crearPersonaHumana(Context ctx) {
    List<String> errores = validar(ctx);
    if (!errores.isEmpty()) {
      ctx.status(400).render("registro-humana.html.hbs", modelo(ctx, errores));
      return;
    }

    try {
      RegistrarDonanteResponse respuesta = usuarios.registrarDonante(
          texto(ctx, "nombre"),
          texto(ctx, "apellido"),
          entero(texto(ctx, "edad")),
          entero(texto(ctx, "numeroDeDocumento")),
          texto(ctx, "genero"),
          texto(ctx, "direccion"),
          texto(ctx, "email"),
          texto(ctx, "telefono"),
          texto(ctx, "whatsapp"),
          texto(ctx, "predeterminado"),
          campo(ctx, "contrasena"));

      if (respuesta.getOk()) {
        ctx.redirect("/login");
      } else {
        ctx.status(400).render("registro-humana.html.hbs", modelo(ctx, List.of(respuesta.getError())));
      }
    } catch (StatusRuntimeException e) {
      ctx.status(503).render("registro-humana.html.hbs",
          modelo(ctx, List.of("No se pudo conectar con el servicio de donaciones")));
    }
  }

  private List<String> validar(Context ctx) {
    List<String> errores = new ArrayList<>();

    if (texto(ctx, "nombre").isBlank()) {
      errores.add("El nombre es obligatorio.");
    }
    if (texto(ctx, "apellido").isBlank()) {
      errores.add("El apellido es obligatorio.");
    }
    if (!texto(ctx, "numeroDeDocumento").matches("\\d{7,8}")) {
      errores.add("El número de documento debe tener 7 u 8 dígitos.");
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
    modelo.put("nombre", texto(ctx, "nombre"));
    modelo.put("apellido", texto(ctx, "apellido"));
    modelo.put("edad", texto(ctx, "edad"));
    modelo.put("numeroDeDocumento", texto(ctx, "numeroDeDocumento"));
    modelo.put("direccion", texto(ctx, "direccion"));
    modelo.put("email", texto(ctx, "email"));
    modelo.put("telefono", texto(ctx, "telefono"));
    modelo.put("whatsapp", texto(ctx, "whatsapp"));
    modelo.put("generos", generos(texto(ctx, "genero")));
    modelo.put("medios", medios(texto(ctx, "predeterminado")));
    return modelo;
  }

  private List<Map<String, Object>> generos(String elegido) {
    List<Map<String, Object>> opciones = new ArrayList<>();
    for (String genero : GENEROS) {
      opciones.add(Map.of("valor", genero, "seleccionado", genero.equals(elegido)));
    }
    return opciones;
  }

  private List<Map<String, Object>> medios(String elegido) {
    String actual = elegido.isBlank() ? "EMAIL" : elegido;
    return List.of(
        opcionMedio("EMAIL", "Email", actual),
        opcionMedio("TELEFONO", "Teléfono", actual),
        opcionMedio("WHATSAPP", "WhatsApp", actual));
  }

  private Map<String, Object> opcionMedio(String codigo, String etiqueta, String actual) {
    return Map.of("codigo", codigo, "etiqueta", etiqueta, "seleccionado", codigo.equals(actual));
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

  private int entero(String texto) {
    try {
      return Integer.parseInt(texto);
    } catch (NumberFormatException e) {
      return 0;
    }
  }
}