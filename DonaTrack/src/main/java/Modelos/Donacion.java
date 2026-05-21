package Modelos;

import Enums.EstadoDelBien;
import Enums.EstadoDonacion;
import lombok.Getter;
import java.util.List;

@Getter
public class Donacion {

  private String nombreDonante;
  private List<Bien> bienes;
  private EstadoDonacion estado;
  private int id;
  private String descripcionGeneral;


  public Donacion(String nombreDonante, List<Bien> bienes, EstadoDonacion estado, int id) {
    this.nombreDonante = nombreDonante;
    this.bienes = bienes;
    this.estado = estado;
    this.id = id;
  }

/*
  public List<Bien> bienesNuevo(){
    bienes.stream().filter(bien -> bien.getTipoDelBien() == TipoDeBien.getEstadoDelBien().NOPRECEDERO);
    return elPepe;
  }*/

}
