package Modelos;

public abstract class Necesidad {
  private SubCategoria categoria;
  private String descripcion;

  public Necesidad(SubCategoria categoria, String descripcion) {
    this.categoria = categoria;
    this.descripcion = descripcion;
  }

  public abstract boolean necesidadSatisfecha();

}
