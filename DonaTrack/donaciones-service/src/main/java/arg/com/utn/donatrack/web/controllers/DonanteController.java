package arg.com.utn.donatrack.web.controllers;

// import arg.com.utn.donatrack.clients.LogisticaClient;
import arg.com.utn.donatrack.donaciones.Bien;
import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.repositorios.RepositorioDonaciones;
import arg.com.utn.donatrack.repositorios.RepositorioEntidadesBeneficiarias;
import io.javalin.http.Context;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import arg.com.utn.donatrack.repositorios.RepositorioSubcategorias;
import arg.com.utn.donatrack.personas.Persona;
import arg.com.utn.donatrack.repositorios.RepositorioPersonas;
import arg.com.utn.donatrack.repositorios.RepositorioSubcategorias;

public class DonanteController {

  private final RepositorioDonaciones repositorioDonaciones = new RepositorioDonaciones();
  private final RepositorioEntidadesBeneficiarias repositorioEntidades = new RepositorioEntidadesBeneficiarias();
  private final RepositorioSubcategorias repositorioSubcategorias = new RepositorioSubcategorias();
  // private final LogisticaClient logisticaClient = new LogisticaClient();

  public void listarDonaciones(Context ctx) {

    String filtroEstado = ctx.queryParam("estado");
    String filtroCategoria = ctx.queryParam("categoria");

    EntityManager em = Persistencia.crearEntityManager();
    try {
      List<Donacion> donacionesDb = repositorioDonaciones.listarTodas(em);
      List<Map<String, Object>> donacionesParaVista = new ArrayList<>();
      for (Donacion d : donacionesDb) {
        String estado = d.getEstado() != null ? d.getEstado().getClass().getSimpleName().toUpperCase() : "DESCONOCIDO";

        String descripcion = "Sin bienes";
        String categoria = "General";
        if (d.getBienes() != null && !d.getBienes().isEmpty()) {
          Bien primerBien = d.getBienes().get(0);
          if (primerBien.getDescripcion() != null && !primerBien.getDescripcion().isBlank()) {
            descripcion = primerBien.getDescripcion();
          }
          if (primerBien.getSubcategoria() != null && primerBien.getSubcategoria().getCategoriaPadre() != null) {
            categoria = primerBien.getSubcategoria().getCategoriaPadre().getNombre();
          }
        }
        String fecha = d.getEstado() != null && d.getEstado().getFechaDeAsignacion() != null
            ? d.getEstado().getFechaDeAsignacion().toString()
            : "-";
        boolean coincideEstado = filtroEstado == null || filtroEstado.isBlank() || estado.equalsIgnoreCase(filtroEstado);
        boolean coincideCategoria = filtroCategoria == null || filtroCategoria.isBlank() || categoria.equalsIgnoreCase(filtroCategoria);
        if (coincideEstado && coincideCategoria) {
          Map<String, Object> fila = new HashMap<>();
          fila.put("id", d.getId());
          fila.put("fecha", fecha);
          fila.put("descripcion", descripcion);
          fila.put("categoria", categoria);
          fila.put("entidadDestino", "-");
          fila.put("estado", estado);
          donacionesParaVista.add(fila);
        }
      }

      Map<String, Object> modelo = new HashMap<>();

      modelo.put("donaciones", donacionesParaVista);
      modelo.put("categorias", repositorioSubcategorias.listarCategorias(em));
      modelo.put("subcategorias", repositorioSubcategorias.listarSubcategorias(em));

      ctx.render("donante-donaciones.html.hbs", modelo);
    } finally {
      em.close();
    }
  }

  public void listarEntidades(Context ctx) {
    EntityManager em = Persistencia.crearEntityManager();
    try {

      List<EntidadBeneficiaria> entidadesDb = repositorioEntidades.listarTodas(em);

      List<Map<String, Object>> listaParaVista = new ArrayList<>();
      for (EntidadBeneficiaria e : entidadesDb) {
        Map<String, Object> fila = new HashMap<>();
        fila.put("razonSocial", e.getRazonSocial());
        fila.put("direccion", e.getDireccion() != null ? e.getDireccion() : "-");
        fila.put("telefono", "-");

        List<Map<String, Object>> necesidadesVista = new ArrayList<>();
        if (e.getNecesidades() != null) {
          for (var n : e.getNecesidades()) {
            String desc = n.getSubcategoria() != null ? n.getSubcategoria().getNombre() : "Varios";
            necesidadesVista.add(Map.of(
                "descripcion", desc,
                "cantidad", n.getCantidadNecesitada() != null ? n.getCantidadNecesitada() : 1
            ));
          }
        }
        fila.put("necesidades", necesidadesVista);
        listaParaVista.add(fila);
      }

      Map<String, Object> modelo = new HashMap<>();
      modelo.put("entidades", listaParaVista);
      ctx.render("donante-entidades.html.hbs", modelo);
    } finally {
      em.close();
    }
  }

  public void seguirEntregas(Context ctx) {
    EntityManager em = Persistencia.crearEntityManager();
    try {

      List<Donacion> enViajeDb = repositorioDonaciones.listarEnTraslado(em);
      List<Map<String, Object>> entregasParaVista = new ArrayList<>();
      for (Donacion d : enViajeDb) {
        Map<String, Object> fila = new HashMap<>();
        fila.put("donacionId", d.getId());
        String desc = (d.getBienes() != null && !d.getBienes().isEmpty())
            ? d.getBienes().get(0).getDescripcion()
            : "Donación #" + d.getId();
        fila.put("descripcion", desc);
        fila.put("entidadDestino", "Entidad Asignada");

        Long camionId = null;
        if (d.getEstado() instanceof arg.com.utn.donatrack.estados.EnTraslado traslado) {
          camionId = traslado.getCamionId();
        }

        Double lat = null;
        Double lon = null;
        String patente = "Camión #" + (camionId != null ? camionId : "S/A");
        /*
        if (camionId != null && camionId > 0) {
          arg.com.utn.donatrack.dtos.CamionDTO camion = logisticaClient.obtenerCamion(camionId);
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
        fila.put("ultimaActualizacion", d.getEstado() != null && d.getEstado().getFechaDeAsignacion() != null
            ? d.getEstado().getFechaDeAsignacion().toString()
            : "-");
        fila.put("estado", "EN_TRASLADO");

        fila.put("latitud", lat != null ? lat : -34.6037);
        fila.put("longitud", lon != null ? lon : -58.3816);
        entregasParaVista.add(fila);
      }

      Map<String, Object> modelo = new HashMap<>();
      modelo.put("entregas", entregasParaVista);
      ctx.render("donante-seguimiento.html.hbs", modelo);
    } finally {
      em.close();
    }
  }
}
