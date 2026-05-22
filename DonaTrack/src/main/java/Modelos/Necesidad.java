package Modelos;

import Errors.ErrorParametrosConstructor;

public abstract class Necesidad {
  private Bien categoria;
  private String descripcion;

  public Necesidad(Bien categoria, String descripcion) {
    validarConstructor(categoria, descripcion);
    this.categoria = categoria;
    this.descripcion = descripcion;
  }

  public abstract boolean necesidadSatisfecha();

  private void validarConstructor(Bien categoria, String descripcion){
    if (categoria == null || descripcion == null) {
      throw new ErrorParametrosConstructor("Faltan parámetros en el constructor.");
    }
  }
}
