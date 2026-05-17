package Modelos;

import java.util.List;

public class Donacion {

  private String nombreDonante;
  private List<Bien> bienes;

  public String getNombreDonante() {
    return nombreDonante;
  }

  public void setNombreDonante(String nombreDonante) {
    this.nombreDonante = nombreDonante;
  }

  public List<Bien> getBienes() {
    return bienes;
  }

  public void setBienes(List<Bien> bienes) {
    this.bienes = bienes;
  }
}
