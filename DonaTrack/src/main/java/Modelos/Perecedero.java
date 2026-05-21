package Modelos;

import Enums.TipoCategoria;

public class Perecedero extends TipoDeBien{
  private String fechaVencimiento;
  private int unidad;

  public Perecedero(String descripcion, String stringFoto, int cantidad, TipoCategoria categoria, String subcategoria, String fechaVencimiento, int unidad) {
    super(descripcion, stringFoto, cantidad, categoria, subcategoria);
    this.fechaVencimiento = fechaVencimiento;
    this.unidad = unidad;
  }


  public String getFechaVencimiento() {
    return fechaVencimiento;
  }

  public void setFechaVencimiento(String fechaVencimiento) {
    this.fechaVencimiento = fechaVencimiento;
  }

  public int getUnidad() {
    return unidad;
  }

  public void setUnidad(int unidad) {
    this.unidad = unidad;
  }
}
