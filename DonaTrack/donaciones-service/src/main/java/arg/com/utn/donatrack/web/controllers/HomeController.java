package arg.com.utn.donatrack.web.controllers;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.repositorios.RepositorioDonaciones;
import io.javalin.http.Context;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeController {

  private final RepositorioDonaciones repoDonaciones = new RepositorioDonaciones();

  public void index(Context ctx) {
    ctx.render("landing.html.hbs", Map.of());
  }

  public void mostrarMapaEntregas(Context ctx) {
    EntityManager em = Persistencia.crearEntityManager();
    try {
      List<Donacion> entregadasDb = repoDonaciones.listarEntregadas();
      List<Map<String, Object>> listaParaMapa = new ArrayList<>();
      for (Donacion d : entregadasDb) {
        Map<String, Object> pin = new HashMap<>();
        pin.put("id", d.getId());
        String desc = (d.getBienes() != null && !d.getBienes().isEmpty() && d.getBienes().get(0).getDescripcion() != null)
            ? d.getBienes().get(0).getDescripcion()
            : "Donación #" + d.getId();
        pin.put("descripcion", desc);
        String nombreEntidad = "Comedor / Entidad Beneficiaria";
        Double lat = null;
        Double lon = null;
        if (d.getEstado() instanceof arg.com.utn.donatrack.estados.Entregada entregada) {
          EntidadBeneficiaria entidad = entregada.getEntidadBeneficiaria();
          if (entidad != null) {
            nombreEntidad = entidad.getRazonSocial();
            lat = entidad.getLatitud();
            lon = entidad.getLongitud();
          }
        }
        pin.put("entidadNombre", nombreEntidad);
        pin.put("latitud", lat != null ? lat : -34.6037);
        pin.put("longitud", lon != null ? lon : -58.3816);
        String fecha = (d.getEstado() != null && d.getEstado().getFechaDeAsignacion() != null)
            ? d.getEstado().getFechaDeAsignacion().toString()
            : "-";
        pin.put("fechaEntrega", fecha);
        String foto = (d.getBienes() != null && !d.getBienes().isEmpty() && d.getBienes().get(0).getFoto() != null)
            ? d.getBienes().get(0).getFoto()
            : "https://es.wikipedia.org/wiki/Archivo:Image-not-found.png";
        pin.put("fotoUrl", foto);
        listaParaMapa.add(pin);
      }
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("entregadas", listaParaMapa);
      ctx.render("mapa-entregas.html.hbs", modelo);
    } finally {
      em.close();
    }
  }

  public void mostrarLegal(Context ctx) {
    ctx.render("legal.html.hbs", Map.of());
  }
}
