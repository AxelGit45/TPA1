package arg.com.utn.donatrack.web.controllers;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.personas.PersonaHumana;
import arg.com.utn.donatrack.personas.PersonaJuridica;
import arg.com.utn.donatrack.personas.TipoRazonSocial;
import arg.com.utn.donatrack.personas.contactos.Contacto;
import arg.com.utn.donatrack.personas.contactos.Mail;
import arg.com.utn.donatrack.personas.contactos.Telefono;
import arg.com.utn.donatrack.personas.contactos.WhatsApp;
import arg.com.utn.donatrack.repositorios.RepositorioEntidadesBeneficiarias;
import arg.com.utn.donatrack.repositorios.RepositorioPersonas;
import arg.com.utn.donatrack.repositorios.RepositorioUsuarios;
import arg.com.utn.donatrack.usuarios.Rol;
import arg.com.utn.donatrack.usuarios.Usuario;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroController {

 /* public void registrarUsuario(Usuario usuario) {
    // Lógica para registrar un nuevo usuario
  }*/

  //GET -> Lleva al formulario vacío
  public void registroJuridica(Context ctx) {
    ctx.render("registro-juridica.html.hbs", Map.of());
  }

  public void registroEntidad(Context ctx) {
    ctx.render("registro-entidad.html.hbs", Map.of());
  }

  public void registroPersonaHumana(Context ctx) {
    ctx.render("registro-humana.html.hbs", Map.of());
  }


  //POST -> Crea el usuario y la persona jurídica.
  /*---------------------------PERSONA-JURIDICA---------------------------*/
  public void crearPersonaJuridica(Context ctx) {
    String razonSocial = texto(ctx, "razonSocial");
    TipoRazonSocial tipo = TipoRazonSocial.valueOf(texto(ctx, "tipoRazonSocial"));
    String rubro = texto(ctx, "rubro");
    String email = texto(ctx, "email");
    String contrasenia = texto(ctx, "contrasenia");
    String confirmacion = texto(ctx, "confirmacion");

  // Validaciones del form

    List<String> errores = new ArrayList<>();
    if (razonSocial.isEmpty()) {errores.add("El campo 'Razón Social' es obligatorio.");}
    if (rubro.isEmpty()) {errores.add("El campo 'Rubro' es obligatorio.");}
    if (email.isEmpty()) {errores.add("El campo 'Email' es obligatorio.");}
    if (contrasenia.length() < 8) {errores.add("La contraseña debe tener mínimo 8 caracteres");}
    if (!confirmacion.equals(contrasenia)) {errores.add("Las contraseñas no coinciden");}
    if (!errores.isEmpty()) {
      ctx.status(400).render("registro-juridica.html.hbs", formulario(ctx,errores));
      return;
    }

    /*Una vez validado*/

    RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();

    if (repositorioUsuarios.buscarUsuarioPorEmail(email) != null) {
      errores.add("Ya existe un usuario con ese email.");
      ctx.render("registro-juridica.html.hbs", Map.of("errores", errores));
      return;
    }
    /*Creo el usuario */
    Usuario usuario = new Usuario(email, contrasenia, Rol.DONANTE);
    repositorioUsuarios.guardarUsuario(usuario);

    /*Creo la PersonaJuridica */
    PersonaJuridica personaJuridica = new PersonaJuridica(razonSocial, tipo, rubro, new ArrayList<>());
    personaJuridica.agregarContacto(new Mail(email));
    /*Lo persisto pasandoselo a RepositorioUsuario*/
    personaJuridica.setUsuarioId(usuario.getId());
    /*Instancio el Repositorio*/
    RepositorioPersonas repositorioPersonas = new RepositorioPersonas();
    repositorioPersonas.guardarPersona(personaJuridica);

    ctx.redirect("/registro/exito");

  }

  /*---------------------------ENTIDAD-BENEFICIARIA---------------------------*/
  public void crearEntidadBeneficiaria(Context ctx) {
    String razonSocial = texto(ctx, "razonSocial");
    String direccion = texto(ctx, "direccion");
    String telefono = texto(ctx, "telefono");
    String email = texto(ctx, "email");
    String contrasenia = texto(ctx, "contrasenia");
    String confirmacion = texto(ctx, "confirmacion");

    // Validaciones del form

    List<String> errores = new ArrayList<>();
    if (razonSocial.isEmpty()) {errores.add("El campo 'Razón Social' es obligatorio.");}
    if (direccion.isEmpty()) {errores.add("El campo 'Dirección' es obligatorio.");}
    if (telefono.isEmpty()) {errores.add("El campo 'Teléfono' es obligatorio.");}
    if (email.isEmpty()) {errores.add("El campo 'Email' es obligatorio.");}
    if (contrasenia.length() < 8) {errores.add("La contraseña debe tener mínimo 8 caracteres");}
    if (!confirmacion.equals(contrasenia)) {errores.add("Las contraseñas no coinciden");}
    if (!errores.isEmpty()) {
      ctx.status(400).render("registro-entidad.html.hbs", formularioEntidad(ctx,errores));
      return;
    }

    /*Una vez validado*/

    RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();

    if (repositorioUsuarios.buscarUsuarioPorEmail(email) != null) {
      errores.add("Ya existe un usuario con ese email.");
      ctx.render("registro-entidad.html.hbs", Map.of("errores", errores));
      return;
    }
    /*Creo el usuario */
    Usuario usuario = new Usuario(email, contrasenia, Rol.ENTIDAD_BENEFICIARIA);
    repositorioUsuarios.guardarUsuario(usuario);

    EntidadBeneficiaria entidadBeneficiaria = new EntidadBeneficiaria(razonSocial, direccion, new ArrayList<>());
    entidadBeneficiaria.getContactos().add(new Mail(email));
    entidadBeneficiaria.getContactos().add(new Telefono(telefono));
    entidadBeneficiaria.setUsuarioId(usuario.getId());
    RepositorioEntidadesBeneficiarias repositorioEntidadesBeneficiarias = new RepositorioEntidadesBeneficiarias();
    repositorioEntidadesBeneficiarias.guardarEntidad(entidadBeneficiaria);

    ctx.redirect("/registro/exito");

  }


  /*---------------------------PERSONA-HUMANA---------------------------*/
  public void crearPersonaHumana(Context ctx) {
    String nombre = texto(ctx, "nombre");
    String apellido = texto(ctx, "apellido");
    Integer edad = pasarAEntero(texto(ctx, "edad"));
    Integer documento = pasarAEntero(texto(ctx, "documento"));
    String genero = texto(ctx, "genero");
    String direccion = texto(ctx, "direccion");
    String email = texto(ctx, "email");

    String telefono = texto(ctx, "telefono");
    String whatsapp = texto(ctx, "whatsapp");
    String predeterminado = texto(ctx, "predeterminado");

    String contrasenia = texto(ctx, "contrasenia");
    String confirmacion = texto(ctx, "confirmacion");

/*String nombre, String apellido, Integer edad, Integer numeroDeDocumento, String genero,
                       String direccion, List<Contacto> contactos, Contacto predeterminado*/
    // Validaciones del form

    List<String> errores = new ArrayList<>();
    if (nombre.isEmpty()) {errores.add("El campo 'Nombre' es obligatorio.");}
    if (apellido.isEmpty()) {errores.add("El campo 'Apellido' es obligatorio.");}
    if (edad == null || edad <= 0) {errores.add("El campo 'Edad' es obligatorio y debe ser un número positivo.");}


    if (direccion.isEmpty()) {errores.add("El campo 'Dirección' es obligatorio.");}

    if (email.isEmpty()) {errores.add("El campo 'Email' es obligatorio.");}
    if (contrasenia.length() < 8) {errores.add("La contraseña debe tener mínimo 8 caracteres");}
    if (!confirmacion.equals(contrasenia)) {errores.add("Las contraseñas no coinciden");}
    if (!errores.isEmpty()) {
      ctx.status(400).render("registro-humana.html.hbs", formularioHumana(ctx,errores));
      return;
    }

    /*Una vez validado*/

    RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();

    if (repositorioUsuarios.buscarUsuarioPorEmail(email) != null) {
      errores.add("Ya existe un usuario con ese email.");
      ctx.render("registro-humana.html.hbs", Map.of("errores", errores));
      return;
    }
    /*Creo el usuario */
    Usuario usuario = new Usuario(email, contrasenia, Rol.DONANTE);
    repositorioUsuarios.guardarUsuario(usuario);


    Mail mail = new Mail(email);
    Contacto contactoPredeterminado = mail;
    Telefono tel = null;
    if (!telefono.isEmpty()) {
      tel = new Telefono(telefono);
    }

    if (predeterminado.equals("TELEFONO") && tel != null) {
      contactoPredeterminado = tel;
    }


    WhatsApp whatsApp = null;
    if (!whatsapp.isEmpty()) {
      whatsApp = new WhatsApp(whatsapp);
    }

    if (predeterminado.equals("WHATSAPP") && whatsApp != null) {
      contactoPredeterminado = whatsApp;
    }

    RepositorioPersonas repositorioPersonas = new RepositorioPersonas();
    PersonaHumana personaHumana = new PersonaHumana(nombre, apellido, edad, documento, genero, direccion, new ArrayList<>(), contactoPredeterminado);
    repositorioPersonas.guardarPersona(personaHumana);

    ctx.redirect("/registro/exito");

  }

  private Map<String, Object> formulario(Context ctx, List<String> errores) {
    Map<String, Object> formulario= new HashMap<>();
    formulario.put("errores", errores);
    formulario.put("razonSocial", texto(ctx, "razonSocial"));
    formulario.put("rubro", texto(ctx, "rubro"));
    formulario.put("email", texto(ctx, "email"));
    return formulario;
  }

  private Map<String, Object> formularioEntidad(Context ctx, List<String> errores) {
    Map<String, Object> formulario= new HashMap<>();
    formulario.put("errores", errores);
    formulario.put("razonSocial", texto(ctx, "razonSocial"));
    formulario.put("direccion", texto(ctx, "direccion"));
    formulario.put("telefono", texto(ctx, "telefono"));
    formulario.put("email", texto(ctx, "email"));
    return formulario;
  }

  private Map<String, Object> formularioHumana(Context ctx, List<String> errores) {
    Map<String, Object> formulario= new HashMap<>();
    formulario.put("errores", errores);
    formulario.put("nombre", texto(ctx, "nombre"));
    formulario.put("apellido", texto(ctx, "apellido"));
    formulario.put("edad", texto(ctx, "edad"));
    formulario.put("documento", texto(ctx, "documento"));
    formulario.put("genero", texto(ctx, "genero"));
    formulario.put("direccion", texto(ctx, "direccion"));
    formulario.put("telefono", texto(ctx, "telefono"));
    formulario.put("email", texto(ctx, "email"));
    return formulario;
  }

  //Saca espacios de adelante y de atrás dejando el string limpio. Si es null, devuelve "".
  private String texto(Context ctx, String nombreCampo) {
    String valor = ctx.formParam(nombreCampo);
    if (valor == null) { return ""; } else {
       return valor = valor.trim();
    }
  }

  private Integer pasarAEntero(String texto) {
    try {
      return Integer.parseInt(texto);
    } catch (NumberFormatException e) {
      return null;
    }
  }

}