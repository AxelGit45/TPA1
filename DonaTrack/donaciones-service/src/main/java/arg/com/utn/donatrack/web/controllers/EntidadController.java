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
import java.util.List;
import java.util.Map;

public class EntidadController {

  private final RepositorioEntidadesBeneficiarias repoEntidades = new RepositorioEntidadesBeneficiarias();
  private final RepositorioSubcategorias repoSubcategorias = new RepositorioSubcategorias();
  private final RepositorioDonaciones repoDonaciones = new RepositorioDonaciones();
  // private final LogisticaClient logisticaClient = new LogisticaClient();

  public void mostrarFormularioNecesidad(Context ctx) {
    Map<String, Object> modelo = new HashMap<>();
    ctx.render("beneficiaria-necesidad.html.hbs", modelo);
  }

  public void guardarNecesidad(Context ctx) {
    String tipo = ctx.formParam("tipo");
    String nombreSubcategoria = ctx.formParam("subcategoria");
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
        Subcategoria subcategoria = repoSubcategorias.obtenerOCrear(
            new Subcategoria(nombreSubcategoria != null ? nombreSubcategoria : "General", null, false, false));

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
      modelo.put("exito", true);
      ctx.render("beneficiaria-necesidad.html.hbs", modelo);

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

      List<Donacion> donacionesDb = repoDonaciones.listarTodas(em);

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
    String idStr = ctx.pathParam("id");
    Long donacionId = Long.parseLong(idStr);
    EntityManager em = Persistencia.crearEntityManager();
    try {
      em.getTransaction().begin();

      Donacion donacion = repoDonaciones.buscarPorId(em, donacionId);
      if (donacion == null) {
        ctx.status(404).result("Donación no encontrada.");
        return;
      }

      Long usuarioId = ctx.sessionAttribute("usuarioId");
      EntidadBeneficiaria entidad = null;
      if (usuarioId != null) {
        entidad = repoEntidades.buscarPorUsuarioId(em, usuarioId);
      }
      if (entidad == null && !repoEntidades.listarTodas(em).isEmpty()) {
        entidad = repoEntidades.listarTodas(em).get(0);
      }

      var archivos = ctx.uploadedFiles("fotos");
      String urlFoto = "/img/sin-foto.png";
      if (!archivos.isEmpty()) {
        var fotoSubida = archivos.get(0);

        String nombreArchivo = System.currentTimeMillis() + "_" + fotoSubida.filename().replaceAll("\\s+", "_");

        java.io.File directorio = new java.io.File("src/main/resources/public/uploads");
        if (!directorio.exists()) {
          directorio.mkdirs();
        }

        java.io.File archivoDestino = new java.io.File(directorio, nombreArchivo);
        try (var input = fotoSubida.content();
             var output = new java.io.FileOutputStream(archivoDestino)) {
          input.transferTo(output);
        }

        urlFoto = "/uploads/" + nombreArchivo;
      }

      Entregada entregada = new Entregada();
      entregada.setEntidadBeneficiaria(entidad);
      donacion.cambiarEstado(entregada);

      if (donacion.getBienes() != null && !donacion.getBienes().isEmpty()) {
        donacion.getBienes().get(0).setFoto(urlFoto);
      }
      em.merge(donacion);
      em.getTransaction().commit();
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("donacionId", idStr);
      modelo.put("exito", true);
      ctx.render("beneficiaria-confirmar.html.hbs", modelo);
    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      e.printStackTrace();
      ctx.status(500).result("Error al confirmar la recepción: " + e.getMessage());
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