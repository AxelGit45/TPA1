package Modelos;

import Enums.EstadoDelBien;
import Enums.TipoCategoria;
import Errors.ErrorParametrosConstructor;

public class TipoDeBien  {
  private EstadoDelBien estado;

  public TipoDeBien(EstadoDelBien estado) {
    validarConstructor(estado);
    this.estado = estado;
  }

  private void validarConstructor(EstadoDelBien estado){
    if(estado == null){
      throw new ErrorParametrosConstructor("Faltan parámetros en el constructor.");
    }
  }
}