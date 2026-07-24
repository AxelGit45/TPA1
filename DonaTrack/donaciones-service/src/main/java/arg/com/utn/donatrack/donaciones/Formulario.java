package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.personas.Persona;
import java.util.ArrayList;
import java.util.List;

public class Formulario {

  private Persona persona;
  private String descripcion;
  private List<Bien> bienes;

  public Formulario(Persona persona, String descripcion, List<Bien> bienes){

    this.persona = persona;
    this.descripcion = descripcion;
    this.bienes = bienes;

  }

  public List<Donacion> segmentarDonaciones(){

    List<Donacion> donacionesSegmentadas = new ArrayList<>();

    for (Bien bien : this.bienes) {

      Donacion nuevaDonacion = new Donacion(bien);

      donacionesSegmentadas.add(nuevaDonacion);

    }

    return  donacionesSegmentadas;

  }

}
