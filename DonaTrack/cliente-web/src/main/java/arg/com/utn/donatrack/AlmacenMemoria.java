package arg.com.utn.donatrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlmacenMemoria {

  public static List<Map<String, Object>> donaciones = new ArrayList<>();
  public static List<Map<String, Object>> entidades = new ArrayList<>();
  public static List<Map<String, Object>> entregasEnViaje = new ArrayList<>();
  public static List<Map<String, Object>> notificaciones = new ArrayList<>();


  static {
    cargarDatosSemilla();
  }

  public static void cargarDatosSemilla() {

    Map<String, Object> d1 = new HashMap<>();
    d1.put("id", 101);
    d1.put("fecha", "02/11/2026");
    d1.put("fechaAsignacion", "03/11/2026");
    d1.put("descripcion", "Fideos secos y arroz blanco");
    d1.put("cantidad", 100);
    d1.put("unidad", "Kg");
    d1.put("categoria", "Alimentos");
    d1.put("entidadDestino", "Comedor Comunitario Los Bajitos");
    d1.put("estado", "EN_TRASLADO");
    donaciones.add(d1);

    Map<String, Object> d2 = new HashMap<>();
    d2.put("id", 102);
    d2.put("fecha", "28/10/2026");
    d2.put("fechaAsignacion", "29/10/2026");
    d2.put("descripcion", "Camperas y frazadas de polar");
    d2.put("cantidad", 25);
    d2.put("unidad", "Unidades");
    d2.put("categoria", "Ropa");
    d2.put("entidadDestino", "Hogar de Noche Santa Teresita");
    d2.put("estado", "ENTREGADA");
    d2.put("fotoUrl", "/logo.jpg");
    d2.put("fechaEntrega", "31/10/2026");
    d2.put("entidadNombre", "Hogar Santa Teresita (San Telmo)");
    d2.put("latitud", -34.6180);
    d2.put("longitud", -58.3730);
    donaciones.add(d2);

    Map<String, Object> d3 = new HashMap<>();
    d3.put("id", 103);
    d3.put("fecha", "04/11/2026");
    d3.put("fechaAsignacion", "05/11/2026");
    d3.put("descripcion", "Paracetamol y gasas esterilizadas");
    d3.put("cantidad", 80);
    d3.put("unidad", "Cajas");
    d3.put("categoria", "Medicamentos");
    d3.put("entidadDestino", "Cruz Roja Argentina");
    d3.put("estado", "EN_DEPOSITO");
    donaciones.add(d3);

    Map<String, Object> d4 = new HashMap<>();
    d4.put("id", 104);
    d4.put("fecha", "20/10/2026");
    d4.put("fechaAsignacion", "22/10/2026");
    d4.put("descripcion", "Leche en polvo entera");
    d4.put("cantidad", 60);
    d4.put("unidad", "Kg");
    d4.put("categoria", "Alimentos");
    d4.put("entidadDestino", "Comedor Comunitario Los Bajitos");
    d4.put("estado", "ENTREGADA");
    d4.put("fotoUrl", "/logo.jpg");
    d4.put("fechaEntrega", "25/10/2026");
    d4.put("entidadNombre", "Comedor Los Bajitos (Flores)");
    d4.put("latitud", -34.6285);
    d4.put("longitud", -58.4620);
    donaciones.add(d4);

    Map<String, Object> e1 = new HashMap<>();
    e1.put("id", 1);
    e1.put("razonSocial", "Comedor Comunitario Los Bajitos");
    e1.put("direccion", "Av. Rivadavia 7200, Flores, CABA");
    e1.put("telefono", "11-4567-8901");
    List<Map<String, Object>> nec1 = new ArrayList<>();
    nec1.add(Map.of("descripcion", "Alimentos no perecederos", "cantidad", 150));
    nec1.add(Map.of("descripcion", "Leche en polvo", "cantidad", 50));
    e1.put("necesidades", nec1);
    entidades.add(e1);

    Map<String, Object> e2 = new HashMap<>();
    e2.put("id", 2);
    e2.put("razonSocial", "Hogar de Noche Santa Teresita");
    e2.put("direccion", "Defensa 840, San Telmo, CABA");
    e2.put("telefono", "11-5678-1234");
    List<Map<String, Object>> nec2 = new ArrayList<>();
    nec2.add(Map.of("descripcion", "Frazadas y abrigos", "cantidad", 40));
    e2.put("necesidades", nec2);
    entidades.add(e2);

    Map<String, Object> c1 = new HashMap<>();
    c1.put("donacionId", 101);
    c1.put("descripcion", "Fideos y arroz (100 Kg)");
    c1.put("patenteCamion", "AF 321 CD");
    c1.put("entidadDestino", "Comedor Los Bajitos");
    c1.put("estado", "EN_TRASLADO");
    c1.put("latitud", -34.6180);
    c1.put("longitud", -58.4420);
    c1.put("ultimaActualizacion", "Hace 3 minutos");
    entregasEnViaje.add(c1);

    Map<String, Object> c2 = new HashMap<>();
    c2.put("donacionId", 105);
    c2.put("descripcion", "Calzado de invierno (30 pares)");
    c2.put("patenteCamion", "AE 987 XY");
    c2.put("entidadDestino", "Hogar Santa Teresita");
    c2.put("estado", "EN_TRASLADO");
    c2.put("latitud", -34.6037);
    c2.put("longitud", -58.3816);
    c2.put("ultimaActualizacion", "Hace 1 minuto");
    entregasEnViaje.add(c2);

    Map<String, Object> n1 = new HashMap<>();
    n1.put("fechaHora", "05/11/2026 10:15 hs");
    n1.put("tipo", "Nueva Asignación");
    n1.put("mensaje", "Se te asignó la Donación #101 (Fideos y arroz, 100 Kg). El camión está en camino.");
    n1.put("estado", "No Leída");
    notificaciones.add(n1);

    Map<String, Object> n2 = new HashMap<>();
    n2.put("fechaHora", "31/10/2026 16:30 hs");
    n2.put("tipo", "Recepción Confirmada");
    n2.put("mensaje", "Se confirmó la recepción de la Donación #102 con fotos de constancia.");
    n2.put("estado", "Leída");
    notificaciones.add(n2);
  }

  public static List<Map<String, Object>> obtenerDonacionesEntregadas() {
    return donaciones.stream()
        .filter(d -> "ENTREGADA".equals(d.get("estado")))
        .toList();
  }
}