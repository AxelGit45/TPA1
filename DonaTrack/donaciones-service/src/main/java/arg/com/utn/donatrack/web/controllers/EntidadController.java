package arg.com.utn.donatrack.web.controllers;

// import arg.com.utn.donatrack.clients.LogisticaClient;
import arg.com.utn.donatrack.donaciones.Bien;
import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.donaciones.Subcategoria;
// import arg.com.utn.donatrack.dtos.CamionDTO;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.entidadesBeneficiarias.Necesidad;
import arg.com.utn.donatrack.entidadesBeneficiarias.NecesidadExtraordinaria;
import arg.com.utn.donatrack.entidadesBeneficiarias.NecesidadRecurrente;
import arg.com.utn.donatrack.estados.EnTraslado;
import arg.com.utn.donatrack.estados.Entregada;
import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.repositorios.RepositorioDonaciones;
import arg.com.utn.donatrack.repositorios.RepositorioEntidadesBeneficiarias;
import arg.com.utn.donatrack.repositorios.RepositorioSubcategorias;
import io.javalin.http.Context;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EntidadController {

  private final RepositorioEntidadesBeneficiarias repoEntidades = new RepositorioEntidadesBeneficiarias();
  private final RepositorioSubcategorias repoSubcategorias = new RepositorioSubcategorias();
  private final RepositorioDonaciones repoDonaciones = new RepositorioDonaciones();
  // private final LogisticaClient logisticaClient = new LogisticaClient();

  public void mostrarFormularioNecesidad(Context ctx) {
    EntityManager em = Persistencia.crearEntityManager();
    RepositorioSubcategorias repoSubcategorias = new RepositorioSubcategorias();

    //Donacion donacion = repoDonaciones.buscarPorId(donacionId);

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
    model.put("exito", ctx.queryParam("exito") != null);

    ctx.render("beneficiaria-necesidad.html.hbs", model);
  }

  public void guardarNecesidad(Context ctx) {
    String tipo = ctx.formParam("tipo");
    String subcategoriaId = ctx.formParam( "subcategoriaId");
    String cantidadStr = ctx.formParam("cantidad");
    String descripcion = ctx.formParam("descripcion");
    String fechaLimiteStr = ctx.formParam("fechaLimite");

    int cantidad = (cantidadStr != null && !cantidadStr.isEmpty()) ? Integer.parseInt(cantidadStr) : 1;

    EntityManager em = Persistencia.crearEntityManager();
    try {
      em.getTransaction().begin();

      Long usuarioId = ctx.sessionAttribute("usuarioId");
      EntidadBeneficiaria entidad = null;
      if (usuarioId != null) {
        entidad = repoEntidades.buscarPorUsuarioId(em, usuarioId);
      }
      if (entidad == null) {
        List<EntidadBeneficiaria> todas = repoEntidades.listarTodas(em);
        if (!todas.isEmpty()) {
          entidad = todas.get(0);
        }
      }

      if (entidad != null) {
       // Subcategoria subcategoria = repoSubcategorias.obtenerOCrear(em,
            //new Subcategoria(nombreSubcategoria != null ? nombreSubcategoria : "General", null, false, false));
        Subcategoria subcategoria = repoSubcategorias.buscarSubcategoriaPorId(em, Long.parseLong(subcategoriaId));
        Necesidad necesidad;
        if ("EXTRAORDINARIA".equalsIgnoreCase(tipo)) {
          necesidad = new NecesidadExtraordinaria(null, subcategoria, cantidad, descripcion, false, 0);
        } else {
          LocalDate fechaLimite = (fechaLimiteStr != null && !fechaLimiteStr.isBlank())
              ? LocalDate.parse(fechaLimiteStr)
              : LocalDate.now().plusMonths(1);
          necesidad = new NecesidadRecurrente(null, subcategoria, cantidad, descripcion, false, 0, fechaLimite);
        }

        entidad.getNecesidades().add(necesidad);
        em.merge(entidad);
        em.getTransaction().commit();
      }

      Map<String, Object> modelo = new HashMap<>();

      ctx.redirect("/entidad/necesidades/nueva?exito=true");

    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      e.printStackTrace();
      ctx.status(500).result("Error al guardar la necesidad: " + e.getMessage());
    } finally {
      em.close();
    }
  }

  public void listarDonacionesAsignadas(Context ctx) {
    EntityManager em = Persistencia.crearEntityManager();
    try {

      Long usuarioId = ctx.sessionAttribute("usuarioId");
      EntidadBeneficiaria entidad = repoEntidades.buscarPorUsuarioId(em, usuarioId);
      List<Donacion> donacionesDb = (entidad != null)
          ? repoDonaciones.listarAsignadasAEntidad(em, entidad.getId())
          : new ArrayList<>();

      List<Map<String, Object>> donacionesParaVista = new ArrayList<>();
      for (Donacion d : donacionesDb) {
        String estado = d.getEstado() != null ? d.getEstado().getClass().getSimpleName().toUpperCase() : "DESCONOCIDO";

        if (!"ENTREGADA".equalsIgnoreCase(estado) && !"VENCIDA".equalsIgnoreCase(estado)) {
          String desc = "Bienes varios";
          int cant = 1;
          String unidad = "unidades";
          if (d.getBienes() != null && !d.getBienes().isEmpty()) {
            Bien primerBien = d.getBienes().get(0);
            if (primerBien.getDescripcion() != null && !primerBien.getDescripcion().isBlank()) {
              desc = primerBien.getDescripcion();
            }
            if (primerBien.getCantidad() != null) {
              cant = primerBien.getCantidad();
            }
            if (primerBien.getUnidad() != null) {
              unidad = primerBien.getUnidad().name().toLowerCase();
            }
          }
          String fecha = (d.getEstado() != null && d.getEstado().getFechaDeAsignacion() != null)
              ? d.getEstado().getFechaDeAsignacion().toString()
              : "-";
          Map<String, Object> fila = new HashMap<>();
          fila.put("id", d.getId());
          fila.put("fechaAsignacion", fecha);
          fila.put("descripcion", desc);
          fila.put("cantidad", cant);
          fila.put("unidad", unidad);
          fila.put("estado", estado);
          donacionesParaVista.add(fila);
        }
      }

      Map<String, Object> modelo = new HashMap<>();
      modelo.put("donaciones", donacionesParaVista);
      ctx.render("beneficiaria-donaciones.html.hbs", modelo);
    } finally {
      em.close();
    }
  }

  public void mostrarFormularioConfirmacion(Context ctx) {
    String id = ctx.pathParam("id");
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("donacionId", id);

    ctx.render("beneficiaria-confirmar.html.hbs", modelo);
  }

  public void confirmarRecepcion(Context ctx) {
    Long donacionId = Long.parseLong(ctx.pathParam("id"));
    EntityManager em = Persistencia.crearEntityManager();
    try {
      Long usuarioId = ctx.sessionAttribute("usuarioId");
      EntidadBeneficiaria entidad = repoEntidades.buscarPorUsuarioId(em, usuarioId);
      Donacion donacion = repoDonaciones.buscarPorId(em, donacionId);

      // Solo puede confirmar una donación que le fue asignada a ella
      if (entidad == null || donacion == null
              || repoDonaciones.listarAsignadasAEntidad(em, entidad.getId()).stream()
              .noneMatch(asignada -> asignada.getId().equals(donacionId))) {
        ctx.status(404).result("Donación no encontrada.");
        return;
      }

      var archivos = ctx.uploadedFiles("fotos");
      if (archivos.isEmpty()) {
        ctx.status(400).result("Tenés que subir al menos una foto de la donación recibida.");
        return;
      }

      // Se guarda la primera foto en la carpeta externa "uploads"
      var fotoSubida = archivos.get(0);
      String nombreArchivo = System.currentTimeMillis() + "_" + fotoSubida.filename().replaceAll("\\s+", "_");
      java.io.File archivoDestino = new java.io.File("uploads", nombreArchivo);
      try (var input = fotoSubida.content();
           var output = new java.io.FileOutputStream(archivoDestino)) {
        input.transferTo(output);
      }
      String urlFoto = "/uploads/" + nombreArchivo;

      Entregada entregada = new Entregada();
      entregada.setEntidadBeneficiaria(entidad);
      donacion.cambiarEstado(entregada);
      donacion.getBienes().forEach(bien -> bien.setFoto(urlFoto));

      repoDonaciones.actualizar(em, donacion);

      Map<String, Object> modelo = new HashMap<>();
      modelo.put("donacionId", donacionId);
      modelo.put("exito", true);
      modelo.put("urlFoto", urlFoto);
      ctx.render("beneficiaria-confirmar.html.hbs", modelo);
    } catch (java.io.IOException e) {
      e.printStackTrace();
      ctx.status(500).result("No se pudo guardar la foto: " + e.getMessage());
    } finally {
      em.close();
    }
  }

  public void listarNotificaciones(Context ctx) {
    EntityManager em = Persistencia.crearEntityManager();
    try {

      List<Donacion> donacionesDb = repoDonaciones.listarTodas(em);
      List<Map<String, Object>> notificacionesParaVista = new ArrayList<>();

      for (Donacion d : donacionesDb) {
        if (d.getHistorialDeCambiosDeEstado() != null) {
          for (var estado : d.getHistorialDeCambiosDeEstado()) {
            Map<String, Object> notif = new HashMap<>();
            String nombreEstado = estado.getClass().getSimpleName();
            notif.put("fechaHora", estado.getFechaDeAsignacion() != null ? estado.getFechaDeAsignacion().toString() : "-");
            notif.put("tipo", "Actualización de Donación #" + d.getId());
            notif.put("mensaje", "La donación #" + d.getId() + " pasó al estado: " + nombreEstado);
            notif.put("estado", "REGISTRADO");
            notificacionesParaVista.add(notif);
          }
        }
      }

      Map<String, Object> modelo = new HashMap<>();
      modelo.put("notificaciones", notificacionesParaVista);
      ctx.render("beneficiaria-notificaciones.html.hbs", modelo);
    } finally {
      em.close();
    }
  }

  public void seguirEntregas(Context ctx) {
    EntityManager em = Persistencia.crearEntityManager();
    try {
      List<Donacion> enViajeDb = repoDonaciones.listarEnTraslado(em);
      List<Map<String, Object>> entregasParaVista = new ArrayList<>();
      for (Donacion d : enViajeDb) {
        Map<String, Object> fila = new HashMap<>();
        fila.put("donacionId", d.getId());
        String desc = (d.getBienes() != null && !d.getBienes().isEmpty() && d.getBienes().get(0).getDescripcion() != null)
            ? d.getBienes().get(0).getDescripcion()
            : "Donación #" + d.getId();
        fila.put("descripcion", desc);
        Long camionId = null;
        if (d.getEstado() instanceof EnTraslado traslado) {
          camionId = traslado.getCamionId();
        }

        Double lat = null;
        Double lon = null;
        String patente = "Camión #" + (camionId != null ? camionId : "S/A");
        /*
        if (camionId != null && camionId > 0) {
          CamionDTO camion = logisticaClient.obtenerCamion(camionId);
          if (camion != null) {
            lat = camion.getLatitud();
            lon = camion.getLongitud();
            if (camion.getPatente() != null && !camion.getPatente().isBlank()) {
              patente = camion.getPatente();
            }
          }
        }
         */
        fila.put("patenteCamion", patente);
        fila.put("ultimaActualizacion", d.getEstado().getFechaDeAsignacion() != null
            ? d.getEstado().getFechaDeAsignacion().toString()
            : "-");
        fila.put("estado", "EN_TRASLADO");

        fila.put("latitud", lat != null ? lat : -34.6037);
        fila.put("longitud", lon != null ? lon : -58.3816);
        entregasParaVista.add(fila);
      }
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("entregas", entregasParaVista);
      ctx.render("beneficiaria-seguimiento.html.hbs", modelo);
    } finally {
      em.close();
    }
  }
}