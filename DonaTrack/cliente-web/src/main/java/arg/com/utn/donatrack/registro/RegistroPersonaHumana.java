package arg.com.utn.donatrack.registro;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class RegistroPersonaHumana {

  public static final int EDAD_MINIMA = 18;
  public static final List<String> GENEROS =
      List.of("Femenino", "Masculino", "No binario", "Otro", "Prefiero no decir");

  private static final String FORMATO_EMAIL = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
  private static final String FORMATO_TELEFONO = "^[+0-9 ()-]{6,20}$";

  private final String nombre;
  private final String apellido;
  private final String edad;
  private final String numeroDeDocumento;
  private final String genero;
  private final String direccion;
  private final String email;
  private final String telefono;
  private final String whatsapp;
  private final String predeterminado;
  private final String contrasena;
  private final String confirmacion;

  public RegistroPersonaHumana(Function<String, String> campo) {
    this.nombre = limpiar(campo.apply("nombre"));
    this.apellido = limpiar(campo.apply("apellido"));
    this.edad = limpiar(campo.apply("edad"));
    this.numeroDeDocumento = limpiar(campo.apply("numeroDeDocumento"));
    this.genero = limpiar(campo.apply("genero"));
    this.direccion = limpiar(campo.apply("direccion"));
    this.email = limpiar(campo.apply("email")).toLowerCase();
    this.telefono = limpiar(campo.apply("telefono"));
    this.whatsapp = limpiar(campo.apply("whatsapp"));
    this.predeterminado = limpiar(campo.apply("predeterminado"));
    this.contrasena = campo.apply("contrasena") == null ? "" : campo.apply("contrasena");
    this.confirmacion = campo.apply("confirmacion") == null ? "" : campo.apply("confirmacion");
  }

  public List<String> validar() {
    List<String> errores = new ArrayList<>();

    if (nombre.isBlank()) errores.add("El nombre es obligatorio.");
    if (apellido.isBlank()) errores.add("El apellido es obligatorio.");

    Integer edadNumerica = parsearEntero(edad);
    if (edadNumerica == null) {
      errores.add("La edad debe ser un número.");
    } else if (edadNumerica < EDAD_MINIMA) {
      errores.add("Tenés que tener al menos " + EDAD_MINIMA + " años para registrarte.");
    }

    if (!numeroDeDocumento.matches("\\d{7,8}")) {
      errores.add("El número de documento debe tener 7 u 8 dígitos.");
    }
    if (!GENEROS.contains(genero)) errores.add("Elegí un género de la lista.");
    if (direccion.isBlank()) errores.add("La dirección completa es obligatoria.");

    if (email.isBlank()) {
      errores.add("El correo electrónico es obligatorio.");
    } else if (!email.matches(FORMATO_EMAIL)) {
      errores.add("El correo electrónico no tiene un formato válido.");
    }
    if (!telefono.isBlank() && !telefono.matches(FORMATO_TELEFONO)) {
      errores.add("El teléfono no tiene un formato válido.");
    }
    if (!whatsapp.isBlank() && !whatsapp.matches(FORMATO_TELEFONO)) {
      errores.add("El WhatsApp no tiene un formato válido.");
    }

    MedioContacto medio = getMedioPredeterminado();
    if (medio == null) {
      errores.add("Elegí un medio de contacto predeterminado.");
    } else if (!estaCargado(medio)) {
      errores.add("El medio de contacto predeterminado tiene que estar cargado.");
    }

    if (contrasena.length() < 8) errores.add("La contraseña debe tener al menos 8 caracteres.");
    if (!contrasena.equals(confirmacion)) errores.add("Las contraseñas no coinciden.");

    return errores;
  }

  public MedioContacto getMedioPredeterminado() {
    try {
      return MedioContacto.valueOf(predeterminado);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  private boolean estaCargado(MedioContacto medio) {
    switch (medio) {
      case EMAIL: return !email.isBlank();
      case TELEFONO: return !telefono.isBlank();
      case WHATSAPP: return !whatsapp.isBlank();
      default: return false;
    }
  }

  // Para volver a mostrar lo que escribió la persona si hay errores (sin contraseñas)
  public Map<String, String> valores() {
    Map<String, String> valores = new LinkedHashMap<>();
    valores.put("nombre", nombre);
    valores.put("apellido", apellido);
    valores.put("edad", edad);
    valores.put("numeroDeDocumento", numeroDeDocumento);
    valores.put("direccion", direccion);
    valores.put("email", email);
    valores.put("telefono", telefono);
    valores.put("whatsapp", whatsapp);
    return valores;
  }

  private static String limpiar(String texto) { return texto == null ? "" : texto.trim(); }

  private static Integer parsearEntero(String texto) {
    try {
      return Integer.parseInt(texto);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  public String getNombre() { return nombre; }
  public String getApellido() { return apellido; }
  public String getGenero() { return genero; }
  public String getEmail() { return email; }
  public String getContrasena() { return contrasena; }
}