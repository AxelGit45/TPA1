package Modelos;

import Enums.TipoCategoria;
import Enums.Unidad;
import Errors.ErrorParametrosConstructor;

public class Perecedero implements TipoDeBien{
  private String fechaVencimiento;
  private Unidad unidad;

  public Perecedero(String fechaVencimiento, Unidad unidad) {
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
