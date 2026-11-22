package arg.com.utn.donatrack.web.controllers;

import arg.com.utn.donatrack.donaciones.Bien;
import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.donaciones.EstadoUso;
import arg.com.utn.donatrack.donaciones.Formulario;
import arg.com.utn.donatrack.donaciones.ResultadoMatchmaking;
import arg.com.utn.donatrack.donaciones.Subcategoria;
import arg.com.utn.donatrack.donaciones.Unidad;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.estados.EnDeposito;
import arg.com.utn.donatrack.estados.Vencida;
import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.personas.Persona;
import arg.com.utn.donatrack.repositorios.RepositorioDonaciones;
import arg.com.utn.donatrack.repositorios.RepositorioEntidadesBeneficiarias;
import arg.com.utn.donatrack.repositorios.RepositorioPersonas;
import arg.com.utn.donatrack.repositorios.RepositorioSubcategorias;
import arg.com.utn.donatrack.tareas.ImportadorCsv;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

public class AdminController {

  private static final String DONACION_EN_CURSO = "donacionEnCurso";

  public void mostrarRecepcion(Context ctx) {
    ctx.render("admin-recepcion.html.hbs", Map.of());
  }

  //POST /admin/recepcion

  public void crearDonacion(Context ctx) {
    String emailDonante = ctx.formParam("emailDonante");
    RepositorioPersonas repositorioPersonas = new RepositorioPersonas();
    EntityManager em = Persistencia.crearEntityManager();
    Persona donante = repositorioPersonas.buscarPorEmail(em, emailDonante);

    if (donante == null) {
      ctx.status(400).result("No se encontró ningún donante registrado con el email: " + emailDonante);
      return;
    }

    Formulario donacionEnCurso = new Formulario(donante, ctx.formParam("descripcion"), new ArrayList<>());
    ctx.sessionAttribute(DONACION_EN_CURSO, donacionEnCurso);

    ctx.redirect("/admin/recepcion/bienes");


  }


 /* public void crearDonacion(Context ctx) {
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
  } */




  //GET /admin/recepcion/{id}/bienes
  public void mostrarCargaBienes(Context ctx) {
   // Long donacionId = Long.parseLong(ctx.pathParam("id"));
    // String donanteId = ctx.queryParam("donanteId");
    EntityManager em = Persistencia.crearEntityManager();
    Formulario donacionEnCurso = ctx.sessionAttribute(DONACION_EN_CURSO);

    if(donacionEnCurso == null) {
      ctx.redirect("/admin/recepcion");
      return;
    }

    //RepositorioDonaciones repoDonaciones = new RepositorioDonaciones();
    RepositorioSubcategorias repoSubcategorias = new RepositorioSubcategorias();

    //Donacion donacion = repoDonaciones.buscarPorId(donacionId);

    //donacion.setDescripcion(ctx.formParam("descripcion"));

    //SUBCATEGORIA

    Map<String, List<Subcategoria>> subcategoriasPorCategoria = new LinkedHashMap<>();
    for (Subcategoria subcategoria : repoSubcategorias.listarSubcategorias(em)) {
      subcategoriasPorCategoria
          .computeIfAbsent(subcategoria.getCategoriaPadre().getNombre(), k -> new ArrayList<>())
          .add(subcategoria);
    }

    //CATEGORIAS
    Map<String, Object> model = new HashMap<>();
    model.put("categorias", repoSubcategorias.listarCategorias(em));
    model.put("subcategorias", subcategoriasPorCategoria);

    //model.put("donacion", donacion);
    //model.put("donacionId",donacionId);
    //model.put("donanteId", donanteId);
    //model.put("donante", new RepositorioPersonas().buscarPorId(Long.parseLong(donanteId)));

    /*
    model.put("subcategorias", repoSubcategorias.listarSubcategorias());
    */

    model.put("donacion", donacionEnCurso);

    ctx.render("admin-recepcion-bienes.html.hbs", model);
  }


  //POST /admin/recepcion/{id}/bienes
  public void agregarBien(Context ctx) {
    Formulario donacionEnCurso = ctx.sessionAttribute(DONACION_EN_CURSO);
    EntityManager em = Persistencia.crearEntityManager();

    if(donacionEnCurso == null) {
      ctx.redirect("/admin/recepcion");
      return;
    }

    //Long donacionId = Long.parseLong(ctx.pathParam("id"));
    //Long donanteId = Long.parseLong(ctx.formParam("donanteId"));
    Long subcategoriaId = Long.parseLong(ctx.formParam("subcategoriaId"));


   // RepositorioDonaciones repoDonaciones = new RepositorioDonaciones();
   // RepositorioPersonas repoPersonas = new RepositorioPersonas();
    RepositorioSubcategorias repoSubcategorias = new RepositorioSubcategorias();
    //Donacion donacion = repoDonaciones.buscarPorId(donacionId);
    //Persona donante = repoPersonas.buscarPorId(donanteId);
    Subcategoria sub = repoSubcategorias.buscarSubcategoriaPorId(em, subcategoriaId);

    Bien bien = new Bien();
    //bien.setDonador(donante);
    bien.setDonador(donacionEnCurso.getPersona());
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
      bien.setPerecedero(true);
    }

    //donacion.agregarBien(bien);
    //repoDonaciones.guardar(donacion);

    donacionEnCurso.agregarBien(bien);

    //ctx.redirect("/admin/recepcion/" + donacionId + "/bienes?donanteId=" + donanteId);
    ctx.redirect("/admin/recepcion/bienes");
  }

  public void finalizarDonacion(Context ctx) {
    Formulario donacionEnCurso = ctx.sessionAttribute(DONACION_EN_CURSO);
    EntityManager em = Persistencia.crearEntityManager();

    if(donacionEnCurso == null) {
      ctx.redirect("/admin/recepcion");
      return;
    }

    if (donacionEnCurso.getBienes().isEmpty()) {
      ctx.status(400).result("La donación no tiene bienes cargados");
      return;
    }

    RepositorioDonaciones repoDonaciones = new RepositorioDonaciones();
    for (Donacion donacion : donacionEnCurso.separarDonacion()) {
      repoDonaciones.guardar(em ,donacion);
    }

    ctx.sessionAttribute(DONACION_EN_CURSO, null);

    ctx.redirect("/admin");


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


  /*ESTO ES PARA PROBAR LOS ALGORITMOS*/

  public void correrAlgoritmos(Context ctx){

    EntityManager em = Persistencia.crearEntityManager();
    /*Repositorios*/
    RepositorioDonaciones repoDonacion = new RepositorioDonaciones();
    RepositorioEntidadesBeneficiarias repoEntidades = new RepositorioEntidadesBeneficiarias();
    /*Repositorios*/

    String id = "29";
    Long donacionId = Long.parseLong(id);
    Donacion donacion = repoDonacion.buscarPorId(em,donacionId);

    List<EntidadBeneficiaria> entidades = repoEntidades.listarTodas(em);
    ResultadoMatchmaking resultados = donacion.getEstado().asignacionDonaciones(entidades, donacion);
    /*hasta aca, con todo esto, no rompe*/
    //repoDonacion.

    ctx.render("admin-matchmaking.html.hbs", Map.of());
  }

  /*ESTO ES PARA PROBAR LOS ALGORITMOS*/


  public void mostrarVencidas(Context ctx) {
    EntityManager em = Persistencia.crearEntityManager();
    try {
      RepositorioDonaciones repoDonaciones = new RepositorioDonaciones();
      List<Donacion> vencidas = repoDonaciones.listarVencidasEnDeposito(em);

      Map<String, Object> model = new HashMap<>();
      model.put("donaciones", vencidas);
      model.put("actualizada", ctx.queryParam("actualizada"));
      ctx.render("admin-vencidas.html.hbs", model);
    } finally {
      em.close();
    }
  }

  public void marcarVencida(Context ctx) {
    Long donacionId = Long.parseLong(ctx.pathParam("id"));
    EntityManager em = Persistencia.crearEntityManager();
    try {
      RepositorioDonaciones repoDonaciones = new RepositorioDonaciones();
      Donacion donacion = repoDonaciones.buscarPorId(em, donacionId);

      if (donacion == null || !(donacion.getEstado() instanceof EnDeposito) || !donacion.estaVencida()) {
        ctx.status(400).result("La donación no está en depósito o no está vencida.");
        return;
      }

      donacion.cambiarEstado(new Vencida());
      repoDonaciones.actualizar(em, donacion);

      ctx.redirect("/admin/vencidas?actualizada=" + donacionId);
    } finally {
      em.close();
    }
  }

  public void mostrarHome(Context ctx) {
    ctx.render("admin-home.html.hbs", Map.of());
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
