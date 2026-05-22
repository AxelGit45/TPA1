package Modelos;

import Errors.ErrorParametrosConstructor;

public class NecesidadExtraordinaria extends Necesidad {
  private int cantidadRequeridad;
  private  int cantidadRecbidad;
  public NecesidadExtraordinaria(int cantidadRequeridad, Bien categoria, String descripcion) {
    super( categoria, descripcion);
    validarConstructor(cantidadRequeridad);
    this.cantidadRecbidad =0;
    this.cantidadRequeridad = cantidadRequeridad;
  }
  public void registrarItems(int cantidad){
    cantidadRecbidad += cantidad;
  }
  @Override
  public boolean necesidadSatisfecha() {

    return cantidadRecbidad >= this.cantidadRequeridad;
  }

  private void validarConstructor(int cantidadRequeridad){
    if(cantidadRequeridad <= 0){
      throw new ErrorParametrosConstructor("CantidadRequerida debe ser un numero positivo.");
    }
  }
}
