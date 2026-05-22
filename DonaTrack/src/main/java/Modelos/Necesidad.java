package Modelos;

public abstract class Necesidad {
  private Bien categoria;
  private String descripcion;

  public Necesidad(Bien categoria, String descripcion) {
    this.categoria = categoria;
    this.descripcion = descripcion;
  }

  public abstract boolean necesidadSatisfecha();

}
