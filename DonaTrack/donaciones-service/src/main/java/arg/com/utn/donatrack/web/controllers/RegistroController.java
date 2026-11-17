package arg.com.utn.donatrack.web.controllers;

import arg.com.utn.donatrack.personas.PersonaJuridica;
import arg.com.utn.donatrack.personas.TipoRazonSocial;
import arg.com.utn.donatrack.personas.contactos.Mail;
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
  public void index(Context ctx) {
    ctx.render("registro-juridica.html.hbs", Map.of());
  }

  //POST -> Crea el usuario y la persona jurídica.

  public void crearUsuario(Context ctx) {
    String razonSocial = texto(ctx, "razonSocial");
    TipoRazonSocial tipo = TipoRazonSocial.valueOf(texto(ctx, "tipoRazonSocial"));
    String rubro = texto(ctx, "rubro");
    String email = texto(ctx, "email");
    String contraseña = texto(ctx, "contraseña");
    String confirmacion = texto(ctx, "confirmacion");

  // Validaciones del form

    List<String> errores = new ArrayList<>();
    if (razonSocial.isEmpty()) {errores.add("El campo 'Razón Social' es obligatorio.");}
    if (rubro.isEmpty()) {errores.add("El campo 'Rubro' es obligatorio.");}
    if (email.isEmpty()) {errores.add("El campo 'Email' es obligatorio.");}
    if (contraseña.length() < 8) {errores.add("La contraseña debe tener mínimo 8 caracteres");}
    if (!confirmacion.equals(contraseña)) {errores.add("Las contraseña no coinciden");}
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
    Usuario usuario = new Usuario(email, contraseña, Rol.DONANTE);
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
  private Map<String, Object> formulario(Context ctx, List<String> errores) {
    Map<String, Object> formulario= new HashMap<>();
    formulario.put("errores", errores);
    formulario.put("razonSocial", texto(ctx, "razonSocial"));
    formulario.put("rubro", texto(ctx, "rubro"));
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

}