package Modelos;

import Enums.TipoCategoria;
import Enums.Unidad;
import Errors.ErrorParametrosConstructor;

public class Perecedero extends TipoDeBien{
  private String fechaVencimiento;
  private Unidad unidad;

  public Perecedero(String descripcion, String stringFoto, int cantidad, TipoCategoria categoria, String subcategoria, String fechaVencimiento, Unidad unidad) {
    super(descripcion, stringFoto, cantidad, categoria, subcategoria);
    validarConstructor(fechaVencimiento, unidad);
    this.fechaVencimiento = fechaVencimiento;
    this.unidad = unidad;
  }

  public String getFechaVencimiento() {
    return fechaVencimiento;
  }

  public void setFechaVencimiento(String fechaVencimiento) {
    this.fechaVencimiento = fechaVencimiento;
  }

  public Unidad getUnidad() {
    return unidad;
  }

  public void setUnidad(Unidad unidad) {
    this.unidad = unidad;
  }

  private void validarConstructor(String fechaVencimiento, Unidad unidad) {
    if (fechaVencimiento == null || unidad == null) {
      throw new ErrorParametrosConstructor("Falta parámetros en el constructor.");
    }
  }
}
