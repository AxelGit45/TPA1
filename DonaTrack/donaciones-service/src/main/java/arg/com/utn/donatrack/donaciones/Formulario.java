package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.personas.Persona;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Formulario {

  private Persona persona;
  private String descripcion;
  private List<Bien> bienes;

  public Formulario(Persona persona, String descripcion, List<Bien> bienes){

    this.persona = persona;
    this.descripcion = descripcion;
    this.bienes = bienes;

  }

  public List<Donacion> separarDonacion() {

    Map<String, List<Bien>> bienesPorSubcategoria = new LinkedHashMap<>();

    for (Bien bien : this.bienes) {
      String clave = bien.getSubcategoria().getNombre();

      if (bien.esPerecedero() && bien.getFechaVencimiento() != null) {
        clave = clave + "|" + bien.getFechaVencimiento();
      }

      bienesPorSubcategoria.computeIfAbsent(clave, claveNueva -> new ArrayList<>()).add(bien);

    }

    List<Donacion> donacionesSegmentadas = new ArrayList();

    for (List<Bien> grupo : bienesPorSubcategoria.values()) {
      Donacion nuevaDonacion = new Donacion(grupo);
      nuevaDonacion.setDescripcion(this.descripcion);
      donacionesSegmentadas.add(nuevaDonacion);
    }
    return donacionesSegmentadas;
  }

  public Persona getPersona() {
    return persona;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public List<Bien> getBienes() {
    return bienes;
  }
}






/*  public List<Donacion> segmentarDonaciones(){

    List<Donacion> donacionesSegmentadas = new ArrayList<>();

    for (Bien bien : this.bienes) {

      Donacion nuevaDonacion = new Donacion(bien);

      donacionesSegmentadas.add(nuevaDonacion);

    }

    return  donacionesSegmentadas;

  }


}
*/