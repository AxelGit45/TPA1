package arg.com.utn.donatrack.controllers;

import arg.com.utn.donatrack.clientes.ClienteDonaciones;
import arg.com.utn.donatrack.registro.MedioContacto;
import arg.com.utn.donatrack.registro.RegistroPersonaHumana;
import arg.com.utn.donatrack.usuarios.RepositorioUsuarios;
import arg.com.utn.donatrack.usuarios.Rol;
import arg.com.utn.donatrack.usuarios.Usuario;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroController {

  private final RepositorioUsuarios usuarios;
  private final ClienteDonaciones donaciones;

  public RegistroController(RepositorioUsuarios usuarios, ClienteDonaciones donaciones) {
    this.usuarios = usuarios;
    this.donaciones = donaciones;
  }

  public void mostrarFormularioHumana(Context ctx) {
    RegistroPersonaHumana vacio = new RegistroPersonaHumana(campo -> null);
    ctx.render("registro-humana.html.hbs", modelo(vacio, List.of()));
  }

  public void crearPersonaHumana(Context ctx) {
    RegistroPersonaHumana datos = new RegistroPersonaHumana(ctx::formParam);

    List<String> errores = datos.validar();
    if (usuarios.buscarPorEmail(datos.getEmail()).isPresent()) {
      errores.add("Ya existe una cuenta con ese correo electrónico.");
    }

    if (!errores.isEmpty()) {
      ctx.status(400).render("registro-humana.html.hbs", modelo(datos, errores));
      return;
    }

    Usuario usuario = usuarios.crear(datos.getEmail(), datos.getContrasena(), Rol.DONANTE);
    donaciones.registrarPersonaHumana(usuario.getId(), datos);
    ctx.redirect("/login");
  }

  private Map<String, Object> modelo(RegistroPersonaHumana datos, List<String> errores) {
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("errores", errores);
    modelo.put("valores", datos.valores());
    modelo.put("generos", generosConSeleccion(datos.getGenero()));
    modelo.put("medios", mediosConSeleccion(datos.getMedioPredeterminado()));
    return modelo;
  }

  private List<Map<String, Object>> generosConSeleccion(String elegido) {
    List<Map<String, Object>> opciones = new ArrayList<>();
    for (String genero : RegistroPersonaHumana.GENEROS) {
      opciones.add(Map.of("valor", genero, "seleccionado", genero.equals(elegido)));
    }
    return opciones;
  }

  private List<Map<String, Object>> mediosConSeleccion(MedioContacto elegido) {
    MedioContacto seleccionado = elegido == null ? MedioContacto.EMAIL : elegido;
    List<Map<String, Object>> opciones = new ArrayList<>();
    for (MedioContacto medio : MedioContacto.values()) {
      opciones.add(Map.of(
          "codigo", medio.name(),
          "etiqueta", medio.getEtiqueta(),
          "seleccionado", medio == seleccionado));
    }
    return opciones;
  }
}