package arg.com.utn.donatrack.web.controllers;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.estados.EnDeposito;
import arg.com.utn.donatrack.personas.Persona;
import arg.com.utn.donatrack.repositorios.RepositorioDonaciones;
import arg.com.utn.donatrack.repositorios.RepositorioPersonas;
import arg.com.utn.donatrack.repositorios.RepositorioSubcategorias;
import arg.com.utn.donatrack.tareas.ImportadorCsv;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import java.util.HashMap;
import java.util.Map;

public class AdminController {

  public void mostrarRecepcion(Context ctx) {
    ctx.render("admin-recepcion.html.hbs", Map.of());
  }

  //POST /admin/recepcion
  public void crearDonacion(Context ctx) {
    String emailDonante = ctx.formParam("emailDonante");
    RepositorioPersonas repoPersonas = new RepositorioPersonas();
    Persona donante = repoPersonas.buscarPorEmail(emailDonante);

    if (donante == null) {
      ctx.status(400).result("No se encontró ningún donante registrado con el email: " + emailDonante);
      return;
    }

    Donacion donacion = new Donacion();
    donacion.cambiarEstado(new EnDeposito());

    RepositorioDonaciones repoDonaciones = new RepositorioDonaciones();
    repoDonaciones.guardar(donacion);

    // Pasamos el donanteId en la URL para asociarlo a cada Bien en el paso 2
    ctx.redirect("/admin/recepcion/" + donacion.getId() + "/bienes?donanteId=" + donante.getId());
  }

  //GET /admin/recepcion/{id}/bienes
  public void mostrarCargaBienes(Context ctx) {
    Long donacionId = Long.parseLong(ctx.pathParam("id"));
    String donanteId = ctx.queryParam("donanteId");

    RepositorioDonaciones repoDonaciones = new RepositorioDonaciones();
    RepositorioSubcategorias repoSubcategorias = new RepositorioSubcategorias();

    //Donacion donacion = repoDonaciones.buscarPorId(donacionId);

    Map<String, Object> model = new HashMap<>();
    model.put("categorias", repoSubcategorias.listarCategorias());

    /*model.put("donacion", donacion);
    model.put("donanteId", donanteId);
    model.put("subcategorias", repoSubcategorias.listarTodas());
    */
    ctx.render("admin-recepcion-bienes.html.hbs", model);
  }


  //POST /admin/recepcion/{id}/bienes
  public void agregarBien(Context ctx) {
    Long donacionId = Long.parseLong(ctx.pathParam("id"));
    Long donanteId = Long.parseLong(ctx.formParam("donanteId"));
    Long subcategoriaId = Long.parseLong(ctx.formParam("subcategoriaId"));

    RepositorioDonaciones repoDonaciones = new RepositorioDonaciones();
    RepositorioPersonas repoPersonas = new RepositorioPersonas();
    /*RepositorioSubcategorias repoSubcategorias = new RepositorioSubcategorias();
    Donacion donacion = repoDonaciones.buscarPorId(donacionId);
    Persona donante = repoPersonas.buscarPorId(donanteId);
    Subcategoria sub = repoSubcategorias.buscarPorId(subcategoriaId);

    Bien bien = new Bien();
    bien.setDonador(donante);
    bien.setSubcategoria(sub);
    bien.setDescripcion(ctx.formParam("descripcionBien"));
    bien.setCantidad(Integer.parseInt(ctx.formParam("cantidad")));
    bien.setUnidad(Unidad.valueOf(ctx.formParam("unidad")));

    String estadoUso = ctx.formParam("estadoUso");
    if (estadoUso != null && !estadoUso.isBlank()) {
      bien.setEstadoUso(EstadoUso.valueOf(estadoUso));
    }

    String vencimiento = ctx.formParam("fechaVencimiento");
    if (vencimiento != null && !vencimiento.isBlank()) {
      bien.setFechaVencimiento(LocalDate.parse(vencimiento));
    }

    donacion.agregarBien(bien);
    repoDonaciones.guardar(donacion);
    */
    ctx.redirect("/admin/recepcion/" + donacionId + "/bienes?donanteId=" + donanteId);
  }

  public void importarCsv(Context ctx) {
    UploadedFile archivo = ctx.uploadedFile("archivoCsv");
    if (archivo == null || archivo.size() == 0) {
      ctx.render("admin-importar-csv.html.hbs", Map.of("error", "Debe seleccionar un archivo CSV válido."));
      return;
    }
    try {
      int cantidad = ImportadorCsv.importar(archivo.content());
      ctx.render("admin-importar-csv.html.hbs", Map.of(
          "exito", true,
          "mensaje", "Se importaron / actualizaron correctamente " + cantidad + " donantes."
      ));
    } catch (Exception e) {
      ctx.render("admin-importar-csv.html.hbs", Map.of(
          "error", "Ocurrió un error al procesar el archivo CSV: " + e.getMessage()
      ));
    }
  }


  public void mostrarVencidas(Context ctx) {
    ctx.render("admin-vencidas.html.hbs", Map.of());
  }

  public void mostrarMatchmaking(Context ctx) {
    ctx.render("admin-matchmaking.html.hbs", Map.of());
  }

  public void mostrarCamiones(Context ctx) {
    ctx.render("admin-camiones.html.hbs", Map.of());
  }

  public void mostrarImportarCsv(Context ctx) {
    ctx.render("admin-importar-csv.html.hbs", Map.of());
  }
}
