package arg.com.utn.donatrack.web.controllers;

import arg.com.utn.donatrack.clients.UsuariosClient;
import arg.com.utn.donatrack.clients.UsuariosClient.RegistroResultado;
import io.javalin.http.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroController {

  private static final List<String> GENEROS =
      List.of("Femenino", "Masculino", "No binario", "Otro", "Prefiero no decir");

  private final UsuariosClient usuarios;

  public RegistroController(UsuariosClient usuarios) {
    this.usuarios = usuarios;
  }

  public void mostrarFormularioHumana(Context ctx) {
    ctx.render("registro-humana.html.hbs", modelo(ctx, List.of()));
  }

  // Acá solo se valida lo que es de la pantalla (números y contraseña repetida);
  // el resto de las reglas las valida el back y su mensaje se muestra tal cual.
  public void crearPersonaHumana(Context ctx) {
    Integer edad = aEntero(texto(ctx, "edad"));
    Integer documento = aEntero(texto(ctx, "numeroDeDocumento"));

    List<String> errores = new ArrayList<>();
    if (edad == null) {
      errores.add("La edad tiene que ser un número.");
    }
    if (documento == null) {
      errores.add("El número de documento tiene que ser un número.");
    }
    if (!campo(ctx, "contrasena").equals(campo(ctx, "confirmacion"))) {
      errores.add("Las contraseñas no coinciden.");
    }
    if (!errores.isEmpty()) {
      ctx.status(400).render("registro-humana.html.hbs", modelo(ctx, errores));
      return;
    }

    try {
      RegistroResultado resultado = usuarios.registrarPersonaHumana(
          texto(ctx, "nombre"),
          texto(ctx, "apellido"),
          edad,
          documento,
          texto(ctx, "genero"),
          texto(ctx, "direccion"),
          texto(ctx, "email"),
          texto(ctx, "telefono"),
          texto(ctx, "whatsapp"),
          texto(ctx, "predeterminado"),
          campo(ctx, "contrasena"));

      if (resultado.ok()) {
        ctx.redirect("/registro/exito");
      } else {
        ctx.status(400).render("registro-humana.html.hbs", modelo(ctx, List.of(resultado.error())));
      }
    } catch (IOException e) {
      ctx.status(503).render("registro-humana.html.hbs",
          modelo(ctx, List.of("No se pudo conectar con el servicio de donaciones.")));
    }
  }

  // Lo que se le muestra a la persona: errores y lo que ya había escrito (nunca las contraseñas)
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

  // Si todavía no eligió ninguno, el predeterminado es el email
  private List<Map<String, Object>> medios(String elegido) {
    String actual = elegido.isBlank() ? "EMAIL" : elegido;
    return List.of(
        medio("EMAIL", "Email", actual),
        medio("TELEFONO", "Teléfono", actual),
        medio("WHATSAPP", "WhatsApp", actual));
  }

  private Map<String, Object> medio(String codigo, String etiqueta, String actual) {
    return Map.of("codigo", codigo, "etiqueta", etiqueta, "seleccionado", codigo.equals(actual));
  }

  // Valor tal cual llegó (para las contraseñas, que no se recortan)
  private String campo(Context ctx, String nombre) {
    String valor = ctx.formParam(nombre);
    return valor == null ? "" : valor;
  }

  // Valor sin espacios al principio y al final
  private String texto(Context ctx, String nombre) {
    return campo(ctx, nombre).trim();
  }

  // null si no es un número válido
  private Integer aEntero(String texto) {
    try {
      return Integer.parseInt(texto);
    } catch (NumberFormatException e) {
      return null;
    }
  }
}